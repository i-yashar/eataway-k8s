package bg.tuplovdiv.apigateway.service;

import java.util.Collection;
import java.util.UUID;

public interface DeliveryService {
    Collection<OrderDTO> getRegisteredOrders();
    Collection<OrderDTO> getDeliveryDriverActiveOrders(String deliveryDriverId);
    OrderDTO getOrderInfo(UUID orderId);
    BasketDTO getOrderBasketInfo(UUID orderId);
    OrderDTO takeOrder(UUID orderId, String deliveryDriverId);
    OrderDTO updateOrder(UUID orderId, String status);
}
