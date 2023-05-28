package bg.tuplovdiv.orderservice.service.impl;

import bg.tuplovdiv.orderservice.dto.CreateOrderRequest;
import bg.tuplovdiv.orderservice.dto.OrderDTO;
import bg.tuplovdiv.orderservice.dto.page.PageDTO;
import bg.tuplovdiv.orderservice.exception.BasketNotFoundException;
import bg.tuplovdiv.orderservice.mapper.OrderMapper;
import bg.tuplovdiv.orderservice.messaging.delivery.OrderStatusChange;
import bg.tuplovdiv.orderservice.messaging.process.CreateOrderProcess;
import bg.tuplovdiv.orderservice.messaging.process.UpdateOrderProcess;
import bg.tuplovdiv.orderservice.model.entity.BasketEntity;
import bg.tuplovdiv.orderservice.model.entity.ItemEntity;
import bg.tuplovdiv.orderservice.model.entity.OrderEntity;
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
    private final BasketRepository basketRepository;
    private final OrderMapper mapper;

    public OrderServiceImpl(OrderStatusInfoService orderStatusInfoService, CreateOrderProcess createOrderProcess, UpdateOrderProcess updateOrderProcess, OrderRepository orderRepository, BasketRepository basketRepository, OrderMapper mapper) {
        this.orderStatusInfoService = orderStatusInfoService;
        this.createOrderProcess = createOrderProcess;
        this.updateOrderProcess = updateOrderProcess;
        this.orderRepository = orderRepository;
        this.basketRepository = basketRepository;
        this.mapper = mapper;
    }

    @Override
    public OrderDTO findOrderByOrderId(UUID orderId) {
        OrderEntity order = getOrderByOrderId(orderId);

        return mapper.toDTO(order);
    }

    @Override
    public PageDTO<OrderDTO> findActiveUserOrders(String clientId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return getActiveUserOrdersPage(clientId, pageable);
    }

    private PageDTO<OrderDTO> getActiveUserOrdersPage(String clientId, Pageable pageable) {
        Page<OrderEntity> orders = orderRepository.findAllByClientIdAndStatusInOrderByUpdatedAtDesc(clientId, Set.of(REGISTERED, ACTIVE, ABOUT_TO_BE_DELIVERED), pageable);

        return new PageDTO<OrderDTO>()
                .setContent(mapToOrderDTOs(orders))
                .setPageInfo(orders.getSize(), orders.hasNext());
    }

    private Collection<OrderDTO> mapToOrderDTOs(Page<OrderEntity> orders) {
        return orders.map(mapper::toDTO)
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public UUID createOrder(CreateOrderRequest createOrderRequest) {
        OrderEntity order = persistOrder(createOrderRequest);
        resetBasketItems(createOrderRequest.getClientId());

        createOrderProcess.start(order);

        return order.getExternalId();
    }

    private OrderEntity persistOrder(CreateOrderRequest createOrderRequest) {
        String clientId = createOrderRequest.getClientId();
        Set<ItemEntity> orderItems = getOrderItems(clientId);

        OrderEntity orderEntity = new OrderEntity()
                .setExternalId(UUID.randomUUID())
                .setClientId(clientId)
                .setClientPhoneNumber(createOrderRequest.getClientPhoneNumber())
                .setAddress(createOrderRequest.getAddress())
                .setItems(orderItems)
                .setTotalCost(calculateTotalCost(orderItems))
                .setStatus(REGISTERED)
                .setUpdatedAt(Instant.now());

        return orderRepository.save(orderEntity);
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
        OrderEntity orderEntity = getOrderByOrderId(order.getOrderId());
        orderEntity.setDeliveryDriverId(order.getDeliveryDriverId());
        orderEntity.setUpdatedAt(Instant.now());

        orderEntity = orderRepository.save(orderEntity);
        orderStatusInfoService.saveOrderStatusInfo(order);

        updateOrderProcess.start(createOrderStatusChange(orderEntity));

        return mapper.toDTO(orderEntity);
    }

    private OrderEntity getOrderByOrderId(UUID orderId) {
        return orderRepository.findOrderEntityByExternalId(orderId)
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public Collection<OrderDTO> getActiveDeliveryDriverOrders(String driverId) {
        return orderRepository
                .findAllByDeliveryDriverIdAndStatusIn(driverId, Set.of(ACTIVE, ABOUT_TO_BE_DELIVERED))
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    private OrderStatusChange createOrderStatusChange(OrderEntity order) {
        return new OrderStatusChange()
                .setOrderId(order.getExternalId())
                .setClientId(order.getClientId())
                .setDeliveryDriverId(order.getDeliveryDriverId())
                .setStatus(order.getStatus().name());
    }
}

