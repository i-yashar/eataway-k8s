package bg.tuplovdiv.orderservice.service.impl;

import bg.tuplovdiv.orderservice.dto.BasketDTO;
import bg.tuplovdiv.orderservice.dto.OrderRequest;
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
    public OrderRequest findOrderByOrderId(UUID orderId) {
        OrderEntity order = getOrderByOrderId(orderId);

        return mapper.toDTO(order);
    }

    private OrderEntity getOrderByOrderId(UUID orderId) {
        return orderRepository.findOrderEntityByExternalId(orderId)
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public PageDTO<OrderRequest> findAllUserOrders(UUID userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return getAllUserOrdersPage(userId, pageable);
    }

    private PageDTO<OrderRequest> getAllUserOrdersPage(UUID userId, Pageable pageable) {
        Page<OrderEntity> orders = orderRepository.findAllByClientId(userId, pageable);

        return new PageDTO<OrderRequest>()
                .setContent(mapToOrderDTOs(orders))
                .setPageInfo(orders.getSize(), orders.hasNext());
    }

    private Collection<OrderRequest> mapToOrderDTOs(Page<OrderEntity> orders) {
        return orders.map(mapper::toDTO)
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public UUID createOrder(OrderRequest orderDTO) {
        OrderContext context = buildOrderContext(orderDTO);
        persistOrder(orderDTO, context.getOrderId());

        createOrderProcess.start(context);

        return context.getOrderId();
    }

    private void persistOrder(OrderRequest orderDTO, UUID orderId) {
        orderDTO.setOrderId(orderId);
        orderDTO.setStatus(REGISTERED);
        OrderEntity orderEntity = mapper.toEntity(orderDTO);

        orderRepository.save(orderEntity);
    }

    private OrderContext buildOrderContext(OrderRequest order) {
        BasketDTO basket = basketService.getByBasketId(order.getBasketId());

        return OrderContext.getBuilder()
                .orderId(UUID.randomUUID())
                .clientId(order.getClientId())
                .clientPhone(order.getClientPhoneNumber())
                .address(order.getAddress())
                .basket(basket)
                .totalCost(calculateTotalCost(order))
                .build();
    }

    private Double calculateTotalCost(OrderRequest orderRequest) {
        BasketDTO basket = basketService.getByBasketId(orderRequest.getBasketId());

        return basket.getItems()
                .stream()
                .map(item -> item.getMenu().getPrice() * item.getCount())
                .reduce((double) 0, Double::sum);
    }
}
