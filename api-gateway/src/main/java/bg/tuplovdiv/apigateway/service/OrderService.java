package bg.tuplovdiv.apigateway.service;

import bg.tuplovdiv.apigateway.dto.CreateOrderRequestDTO;
import bg.tuplovdiv.apigateway.dto.OrderDTO;

import java.util.Collection;
import java.util.UUID;

public interface OrderService {
    UUID createOrder(CreateOrderRequestDTO createOrderRequest);
    OrderDTO getOrderInfo(UUID orderId);
    Collection<OrderDTO> getUserActiveOrders(String userId);
}
