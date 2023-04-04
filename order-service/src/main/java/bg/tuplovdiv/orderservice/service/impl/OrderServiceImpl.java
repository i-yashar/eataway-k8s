package bg.tuplovdiv.orderservice.service.impl;

import bg.tuplovdiv.orderservice.dto.BasketDTO;
import bg.tuplovdiv.orderservice.dto.CreateOrderDTO;
import bg.tuplovdiv.orderservice.dto.OrderDTO;
import bg.tuplovdiv.orderservice.dto.page.PageDTO;
import bg.tuplovdiv.orderservice.mapper.OrderMapper;
import bg.tuplovdiv.orderservice.model.OrderStatus;
import bg.tuplovdiv.orderservice.model.entity.OrderEntity;
import bg.tuplovdiv.orderservice.order.OrderContext;
import bg.tuplovdiv.orderservice.order.process.CreateOrderProcess;
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

        return mapper.toDTO(order);
    }

    private OrderEntity getOrderByOrderId(UUID orderId) {
        return orderRepository.findOrderEntityByExternalId(orderId)
                .orElseThrow(IllegalArgumentException::new);
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
        return orders.map(mapper::toDTO)
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public UUID createOrder(CreateOrderDTO orderDTO) {
        persistOrder(orderDTO);
        OrderContext context = buildOrderContext(orderDTO);

        return createOrderProcess.start(context);
    }

    private void persistOrder(CreateOrderDTO orderDTO) {
        OrderEntity orderEntity = mapper.toEntity(orderDTO);
        orderEntity.setStatus(OrderStatus.REGISTERED);

        orderRepository.save(orderEntity);
    }

    private OrderContext buildOrderContext(CreateOrderDTO orderDTO) {
        UUID clientId = orderDTO.getClientId();
        String clientPhoneNumber = orderDTO.getClientPhoneNumber();
        String address = orderDTO.getAddress();
        BasketDTO basket = basketService.getByBasketId(orderDTO.getBasketId());
        Double totalCost = orderDTO.getTotalCost();

        return new OrderContext()
                .setClientId(clientId)
                .setClientPhoneNumber(clientPhoneNumber)
                .setAddress(address)
                .setBasket(basket)
                .setTotalCost(totalCost);
    }
}
