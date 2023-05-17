package bg.tuplovdiv.apigateway.service;

import bg.tuplovdiv.apigateway.dto.BasketDTO;
import bg.tuplovdiv.apigateway.dto.OrderDTO;

import java.util.Collection;
import java.util.UUID;

public interface DeliveryService {
    Collection<OrderDTO> getRegisteredOrders();
    OrderDTO getOrderInfo(UUID orderId);
    BasketDTO getOrderBasketInfo(UUID orderId);
    OrderDTO takeOrder(UUID orderId, String deliveryDriverId);
    OrderDTO updateOrder(UUID orderId, String status);
}
