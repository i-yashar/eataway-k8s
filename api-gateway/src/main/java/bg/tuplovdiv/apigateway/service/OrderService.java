package bg.tuplovdiv.apigateway.service;

import bg.tuplovdiv.apigateway.dto.CreateOrderRequest;
import bg.tuplovdiv.apigateway.dto.OrderDTO;

import java.util.Collection;
import java.util.UUID;

public interface OrderService {
    UUID createOrder(CreateOrderRequest createOrderRequest);
    OrderDTO getOrderInfo(UUID orderId);
    Collection<OrderDTO> getUserOrders(String userId);
}
