package bg.tuplovdiv.orderservice.service;

import bg.tuplovdiv.orderservice.dto.CreateOrderDTO;
import bg.tuplovdiv.orderservice.dto.OrderDTO;
import bg.tuplovdiv.orderservice.dto.page.PageDTO;

import java.util.UUID;

public interface OrderService {
    OrderDTO findOrderByOrderId(UUID orderId);
    PageDTO<OrderDTO> findAllUserOrders(UUID userId, int page, int size);
    OrderDTO createOrder(CreateOrderDTO orderDTO);
}
