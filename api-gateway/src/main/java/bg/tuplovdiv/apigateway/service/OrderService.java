package bg.tuplovdiv.apigateway.service;

import bg.tuplovdiv.apigateway.dto.CreateOrderRequest;
import bg.tuplovdiv.apigateway.dto.OrderDTO;
import bg.tuplovdiv.apigateway.dto.OrderStatusInfoDTO;

import java.util.Collection;
import java.util.UUID;

public interface OrderService {
    UUID createOrder(CreateOrderRequest createOrderRequest);
    OrderDTO getOrderInfo(UUID orderId);
    Collection<OrderDTO> getUserActiveOrders(String userId);
    Collection<OrderStatusInfoDTO> getOrderStatusInfoMessages(UUID orderId);
}
