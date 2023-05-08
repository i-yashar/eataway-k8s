package bg.tuplovdiv.apigateway.service;

import bg.tuplovdiv.apigateway.dto.OrderDTO;
import bg.tuplovdiv.apigateway.messaging.OrderContext;

import java.util.Collection;
import java.util.UUID;

public interface DeliveryService {
    Collection<OrderDTO> getActiveOrders();
    OrderContext getOrderInfo(UUID orderId);
    OrderDTO takeOrder(UUID orderId, String deliveryDriverId);
    OrderDTO updateOrder(UUID orderId, String status);
}
