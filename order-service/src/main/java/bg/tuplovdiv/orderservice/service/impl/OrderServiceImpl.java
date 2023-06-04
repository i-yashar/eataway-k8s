package bg.tuplovdiv.orderservice.service.impl;

import bg.tuplovdiv.orderservice.dto.CreateOrderRequestDTO;
import bg.tuplovdiv.orderservice.dto.OrderDTO;
import bg.tuplovdiv.orderservice.dto.OrderStatusChangeDTO;
import bg.tuplovdiv.orderservice.dto.page.PageDTO;
import bg.tuplovdiv.orderservice.exception.BasketNotFoundException;
import bg.tuplovdiv.orderservice.mapper.OrderMapper;
import bg.tuplovdiv.orderservice.messaging.process.CreateOrderProcess;
import bg.tuplovdiv.orderservice.messaging.process.UpdateOrderProcess;
import bg.tuplovdiv.orderservice.model.entity.ActiveOrderEntity;
import bg.tuplovdiv.orderservice.model.entity.BasketEntity;
import bg.tuplovdiv.orderservice.model.entity.ItemEntity;
import bg.tuplovdiv.orderservice.model.entity.OrderEntity;
import bg.tuplovdiv.orderservice.model.enums.OrderStatus;
import bg.tuplovdiv.orderservice.repository.ActiveOrderRepository;
import bg.tuplovdiv.orderservice.repository.BasketRepository;
import bg.tuplovdiv.orderservice.repository.OrderRepository;
import bg.tuplovdiv.orderservice.service.OrderService;
import bg.tuplovdiv.orderservice.service.OrderStatusInfoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static bg.tuplovdiv.orderservice.model.enums.OrderStatus.*;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderStatusInfoService orderStatusInfoService;
    private final CreateOrderProcess createOrderProcess;
    private final UpdateOrderProcess updateOrderProcess;
    private final OrderRepository orderRepository;
    private final ActiveOrderRepository activeOrderRepository;
    private final BasketRepository basketRepository;
    private final OrderMapper mapper;

    public OrderServiceImpl(OrderStatusInfoService orderStatusInfoService, CreateOrderProcess createOrderProcess, UpdateOrderProcess updateOrderProcess, OrderRepository orderRepository, ActiveOrderRepository activeOrderRepository, BasketRepository basketRepository, OrderMapper mapper) {
        this.orderStatusInfoService = orderStatusInfoService;
        this.createOrderProcess = createOrderProcess;
        this.updateOrderProcess = updateOrderProcess;
        this.orderRepository = orderRepository;
        this.activeOrderRepository = activeOrderRepository;
        this.basketRepository = basketRepository;
        this.mapper = mapper;
    }

    @Override
    public OrderDTO findOrderByOrderId(UUID orderId) {
        Optional<ActiveOrderEntity> order = activeOrderRepository
                .findActiveOrderEntityByExternalId(orderId);

        if (order.isEmpty()) {
            return mapper.toDTO(getOrderByOrderId(orderId));
        }

        return mapper.toDTO(order.get().toOrder());
    }

    private OrderEntity getOrderByOrderId(UUID orderId) {
        return orderRepository.findOrderEntityByExternalId(orderId)
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public PageDTO<OrderDTO> findActiveUserOrders(String clientId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return getActiveUserOrdersPage(clientId, pageable);
    }

    private PageDTO<OrderDTO> getActiveUserOrdersPage(String clientId, Pageable pageable) {
        Page<ActiveOrderEntity> orders = activeOrderRepository.findAllByClientIdAndStatusInOrderByUpdatedAtDesc(clientId, Set.of(REGISTERED, ACTIVE, ABOUT_TO_BE_DELIVERED), pageable);

        return new PageDTO<OrderDTO>()
                .setContent(mapToOrderDTOs(orders))
                .setPageInfo(orders.getSize(), orders.hasNext());
    }

    private Collection<OrderDTO> mapToOrderDTOs(Page<ActiveOrderEntity> orders) {
        return orders.map(o -> mapper.toDTO(o.toOrder()))
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public UUID createOrder(CreateOrderRequestDTO createOrderRequest) {
        List<OrderEntity> order = persistOrder(createOrderRequest);
        resetBasketItems(createOrderRequest.getClientId());

        order.forEach(o -> createOrderProcess.start(mapper.toDTO(o)));

        return order.get(0).getExternalId();
    }

    private List<OrderEntity> persistOrder(CreateOrderRequestDTO createOrderRequest) {
        String clientId = createOrderRequest.getClientId();
        Set<ItemEntity> orderItems = getOrderItems(clientId);

        Map<UUID, Set<ItemEntity>> orders = new HashMap<>();

        for (ItemEntity item : orderItems) {
            UUID restaurantId = item.getMenu().getRestaurant().getExternalId();
            orders.putIfAbsent(restaurantId, new HashSet<>());
            orders.get(restaurantId).add(item);
        }

        return orders.values()
                .stream()
                .map(o -> {
                    ActiveOrderEntity orderEntity = new ActiveOrderEntity()
                            .setExternalId(UUID.randomUUID())
                            .setClientId(clientId)
                            .setClientPhoneNumber(createOrderRequest.getClientPhoneNumber())
                            .setAddress(createOrderRequest.getAddress())
                            .setItems(o)
                            .setTotalCost(calculateTotalCost(o))
                            .setStatus(REGISTERED)
                            .setUpdatedAt(Instant.now());

                    return activeOrderRepository.save(orderEntity).toOrder();
                })
                .toList();
    }

    private Set<ItemEntity> getOrderItems(String clientId) {
        BasketEntity basketEntity = getBasketEntity(clientId);
        return Collections.unmodifiableSet(basketEntity.getItems());
    }

    private Double calculateTotalCost(Set<ItemEntity> items) {
        return items.stream()
                .map(item -> item.getMenu().getPrice() * item.getCount())
                .reduce((double) 0, Double::sum);
    }

    private void resetBasketItems(String clientId) {
        BasketEntity basket = getBasketEntity(clientId);
        basket.setItems(new HashSet<>());
        basketRepository.save(basket);
    }

    private BasketEntity getBasketEntity(String clientId) {
        return basketRepository.findBasketEntityByOwnerUserId(clientId)
                .orElseThrow(() -> new BasketNotFoundException("Basket not found"));
    }

    @Override
    public OrderDTO updateOrder(OrderDTO order) {
        ActiveOrderEntity activeOrderEntity = getActiveOrderByOrderId(order.getOrderId());
        activeOrderEntity.setStatus(OrderStatus.valueOf(order.getStatus()));
        activeOrderEntity.setDeliveryDriverId(order.getDeliveryDriverId());
        activeOrderEntity.setUpdatedAt(Instant.now());

        activeOrderEntity = activeOrderRepository.save(activeOrderEntity);
        orderStatusInfoService.saveOrderStatusInfo(order);

        OrderEntity orderEntity = activeOrderEntity.toOrder();
        updateOrderProcess.start(createOrderStatusChange(orderEntity));

        return mapper.toDTO(orderEntity);
    }

    private ActiveOrderEntity getActiveOrderByOrderId(UUID orderId) {
        return activeOrderRepository.findActiveOrderEntityByExternalId(orderId)
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public Collection<OrderDTO> getActiveDeliveryDriverOrders(String driverId) {
        return activeOrderRepository
                .findAllByDeliveryDriverIdAndStatusIn(driverId, Set.of(ACTIVE, ABOUT_TO_BE_DELIVERED))
                .stream()
                .map(o -> mapper.toDTO(o.toOrder()))
                .collect(Collectors.toList());
    }

    private OrderStatusChangeDTO createOrderStatusChange(OrderEntity order) {
        return new OrderStatusChangeDTO()
                .setOrderId(order.getExternalId())
                .setClientId(order.getClientId())
                .setDeliveryDriverId(order.getDeliveryDriverId())
                .setStatus(order.getStatus().name());
    }
}

