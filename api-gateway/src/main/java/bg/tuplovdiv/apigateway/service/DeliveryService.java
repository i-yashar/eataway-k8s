package bg.tuplovdiv.apigateway.service;

import bg.tuplovdiv.apigateway.dto.OrderDTO;

import java.util.Collection;
import java.util.UUID;

public interface DeliveryService {
    Collection<OrderDTO> getRegisteredOrders();
    Collection<OrderDTO> getDeliveryDriverActiveOrders(String deliveryDriverId);
    OrderDTO getOrderInfo(UUID orderId);
    OrderDTO takeOrder(UUID orderId, String deliveryDriverId);
    OrderDTO updateOrder(UUID orderId, String status);
}
