package bg.tuplovdiv.apigateway.service;

import bg.tuplovdiv.apigateway.dto.OrderDTO;

import java.util.Collection;
import java.util.UUID;

public interface DeliveryService {
    Collection<OrderDTO> getRegisteredOrders(String driverId);
    Collection<OrderDTO> getDeliveryDriverActiveOrders(String deliveryDriverId);
    OrderDTO getOrderInfo(UUID orderId);
    OrderDTO updateOrder(UUID orderId, String status, String deliveryDriverId);
}
