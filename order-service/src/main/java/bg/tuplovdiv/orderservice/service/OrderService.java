package bg.tuplovdiv.orderservice.service;

import bg.tuplovdiv.orderservice.dto.CreateOrderDTO;
import bg.tuplovdiv.orderservice.dto.OrderDTO;

import java.util.UUID;

public interface OrderService {
    OrderDTO findOrderByOrderId(UUID orderId);
    OrderDTO createOrder(CreateOrderDTO orderDTO);
}
