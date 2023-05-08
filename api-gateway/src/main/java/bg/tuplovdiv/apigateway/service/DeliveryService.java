package bg.tuplovdiv.apigateway.service;

import bg.tuplovdiv.apigateway.dto.OrderDTO;

import java.util.UUID;

public interface DeliveryService {
    OrderDTO takeOrder(UUID orderId);
    OrderDTO updateOrder(UUID orderId);
}
