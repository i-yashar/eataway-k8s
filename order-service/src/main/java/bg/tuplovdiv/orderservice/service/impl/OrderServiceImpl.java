package bg.tuplovdiv.orderservice.service.impl;

import bg.tuplovdiv.orderservice.dto.*;
import bg.tuplovdiv.orderservice.dto.page.PageDTO;
import bg.tuplovdiv.orderservice.exception.MenuNotFoundException;
import bg.tuplovdiv.orderservice.mapper.OrderMapper;
import bg.tuplovdiv.orderservice.messaging.OrderContext;
import bg.tuplovdiv.orderservice.messaging.process.CreateOrderProcess;
import bg.tuplovdiv.orderservice.model.entity.MenuEntity;
import bg.tuplovdiv.orderservice.model.entity.OrderEntity;
import bg.tuplovdiv.orderservice.repository.MenuRepository;
import bg.tuplovdiv.orderservice.repository.OrderRepository;
import bg.tuplovdiv.orderservice.service.BasketService;
import bg.tuplovdiv.orderservice.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import static bg.tuplovdiv.orderservice.model.OrderStatus.ACTIVE;
import static bg.tuplovdiv.orderservice.model.OrderStatus.REGISTERED;

@Service
public class OrderServiceImpl implements OrderService {

    private final BasketService basketService;
    private final CreateOrderProcess createOrderProcess;
    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final OrderMapper mapper;

    public OrderServiceImpl(BasketService basketService, CreateOrderProcess createOrderProcess, OrderRepository orderRepository, MenuRepository menuRepository, OrderMapper mapper) {
        this.basketService = basketService;
        this.createOrderProcess = createOrderProcess;
        this.orderRepository = orderRepository;
        this.menuRepository = menuRepository;
        this.mapper = mapper;
    }

    @Override
    public OrderDTO findOrderByOrderId(UUID orderId) {
        OrderEntity order = getOrderByOrderId(orderId);

        return mapper.toOrderDTO(order);
    }

    @Override
    public PageDTO<OrderDTO> findAllUserOrders(UUID clientId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return getAllUserOrdersPage(clientId, pageable);
    }

    private PageDTO<OrderDTO> getAllUserOrdersPage(UUID clientId, Pageable pageable) {
        Page<OrderEntity> orders = orderRepository.findAllByClientId(clientId, pageable);

        return new PageDTO<OrderDTO>()
                .setContent(mapToOrderDTOs(orders))
                .setPageInfo(orders.getSize(), orders.hasNext());
    }

    private Collection<OrderDTO> mapToOrderDTOs(Page<OrderEntity> orders) {
        return orders.map(mapper::toOrderDTO)
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public UUID createOrder(CreateOrderRequest createOrderRequest) {
        OrderContext context = buildOrderContext(createOrderRequest);
        persistOrder(context);

        createOrderProcess.start(context);

        return context.getOrderId();
    }

    private void persistOrder(OrderContext context) {
        OrderEntity orderEntity = mapper.toOrderEntity(context);
        orderEntity.setStatus(REGISTERED);

        orderRepository.save(orderEntity);
    }

    private OrderContext buildOrderContext(CreateOrderRequest createOrderRequest) {
        String clientId = createOrderRequest.getClientId();
        BasketDTO basket = basketService.getBasketByOwnerId(clientId);

        return OrderContext.getBuilder()
                .orderId(UUID.randomUUID())
                .clientId(clientId)
                .clientPhone(createOrderRequest.getClientPhoneNumber())
                .address(createOrderRequest.getAddress())
                .basket(basket)
                .totalCost(calculateTotalCost(basket))
                .build();
    }

    private Double calculateTotalCost(BasketDTO basket) {
        return basket.getItems()
                .stream()
                .map(item -> getMenuByMenuId(item.getMenuId()).getPrice() * item.getCount())
                .reduce((double) 0, Double::sum);
    }

    private MenuEntity getMenuByMenuId(UUID menuId) {
        return menuRepository.findMenuEntityByExternalId(menuId)
                .orElseThrow(() -> new MenuNotFoundException("Menu with menuId " + menuId + " not found"));
    }

    @Override
    public OrderDTO updateOrderStatus(TakeOrderRequest orderRequest) {
        OrderEntity orderEntity = updateOrderEntity(orderRequest);

        return mapper.toOrderDTO(orderRepository.save(orderEntity));
    }

    private OrderEntity updateOrderEntity(TakeOrderRequest orderRequest) {
        OrderEntity orderEntity = getOrderByOrderId(orderRequest.getOrderId());
        orderEntity.setDeliverDriverId(orderRequest.getDeliveryDriverId());
        orderEntity.setStatus(ACTIVE);
        return orderEntity;
    }

    private OrderEntity getOrderByOrderId(UUID orderId) {
        return orderRepository.findOrderEntityByExternalId(orderId)
                .orElseThrow(IllegalArgumentException::new);
    }
}

