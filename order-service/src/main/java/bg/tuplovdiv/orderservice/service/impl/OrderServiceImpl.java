package bg.tuplovdiv.orderservice.service.impl;

import bg.tuplovdiv.orderservice.dto.BasketDTO;
import bg.tuplovdiv.orderservice.dto.OrderDTO;
import bg.tuplovdiv.orderservice.dto.CreateOrderRequest;
import bg.tuplovdiv.orderservice.dto.TakeOrderRequest;
import bg.tuplovdiv.orderservice.dto.page.PageDTO;
import bg.tuplovdiv.orderservice.mapper.OrderMapper;
import bg.tuplovdiv.orderservice.messaging.OrderContext;
import bg.tuplovdiv.orderservice.messaging.process.CreateOrderProcess;
import bg.tuplovdiv.orderservice.model.entity.OrderEntity;
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
    private final OrderMapper mapper;

    public OrderServiceImpl(BasketService basketService, CreateOrderProcess createOrderProcess, OrderRepository orderRepository, OrderMapper mapper) {
        this.basketService = basketService;
        this.createOrderProcess = createOrderProcess;
        this.orderRepository = orderRepository;
        this.mapper = mapper;
    }

    @Override
    public OrderDTO findOrderByOrderId(UUID orderId) {
        OrderEntity order = getOrderByOrderId(orderId);

        return mapper.toOrderDTO(order);
    }

    @Override
    public PageDTO<OrderDTO> findAllUserOrders(UUID userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return getAllUserOrdersPage(userId, pageable);
    }

    private PageDTO<OrderDTO> getAllUserOrdersPage(UUID userId, Pageable pageable) {
        Page<OrderEntity> orders = orderRepository.findAllByClientId(userId, pageable);

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
    public UUID createOrder(CreateOrderRequest orderDTO) {
        OrderContext context = buildOrderContext(orderDTO);
        persistOrder(orderDTO, context);

        createOrderProcess.start(context);

        return context.getOrderId();
    }

    private void persistOrder(CreateOrderRequest orderRequest, OrderContext context) {
        OrderEntity orderEntity = mapper.toOrderEntity(orderRequest);
        orderEntity.setExternalId(context.getOrderId());
        orderEntity.setTotalCost(context.getTotalCost());
        orderEntity.setStatus(REGISTERED);

        orderRepository.save(orderEntity);
    }

    private OrderContext buildOrderContext(CreateOrderRequest orderRequest) {
        BasketDTO basket = basketService.getByBasketId(orderRequest.getBasketId());

        return OrderContext.getBuilder()
                .orderId(UUID.randomUUID())
                .clientId(orderRequest.getClientId())
                .clientPhone(orderRequest.getClientPhoneNumber())
                .address(orderRequest.getAddress())
                .basket(basket)
                .totalCost(calculateTotalCost(orderRequest))
                .build();
    }

    private Double calculateTotalCost(CreateOrderRequest orderRequest) {
        BasketDTO basket = basketService.getByBasketId(orderRequest.getBasketId());

        return basket.getItems()
                .stream()
                .map(item -> item.getMenu().getPrice() * item.getCount())
                .reduce((double) 0, Double::sum);
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

