package bg.tuplovdiv.orderservice.service.impl;

import bg.tuplovdiv.orderservice.dto.CreateOrderDTO;
import bg.tuplovdiv.orderservice.dto.OrderDTO;
import bg.tuplovdiv.orderservice.dto.page.PageDTO;
import bg.tuplovdiv.orderservice.mapper.OrderMapper;
import bg.tuplovdiv.orderservice.model.OrderStatus;
import bg.tuplovdiv.orderservice.model.entity.OrderEntity;
import bg.tuplovdiv.orderservice.repository.OrderRepository;
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

    private final OrderRepository orderRepository;
    private final OrderMapper mapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper mapper) {
        this.orderRepository = orderRepository;
        this.mapper = mapper;
    }

    @Override
    public OrderDTO findOrderByOrderId(UUID orderId) {
        OrderEntity order = getOrderByOrderId(orderId);

        return mapper.toDTO(order);
    }

    @Override
    public PageDTO<OrderDTO> findAllUserOrders(UUID userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return getAllUserOrdersPage(userId, pageable);
    }

    @Override
    public OrderDTO createOrder(CreateOrderDTO orderDTO) {
        UUID orderId = registerOrder(orderDTO);
        //todo: implement delivery logic
        return new OrderDTO();
    }

    private UUID registerOrder(CreateOrderDTO orderDTO) {
        OrderEntity orderEntity = mapper.toEntity(orderDTO);
        orderEntity.setStatus(OrderStatus.REGISTERED);

        return orderRepository.save(orderEntity)
                .getExternalId();
    }

    private OrderEntity getOrderByOrderId(UUID orderId) {
        return orderRepository.findOrderEntityByExternalId(orderId)
                .orElseThrow(IllegalArgumentException::new);
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
}
