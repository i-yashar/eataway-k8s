package bg.tuplovdiv.apigateway.service;

import bg.tuplovdiv.apigateway.dto.OrderDTO;

import java.util.UUID;

public interface DeliveryDriverService {
    UUID getDeliveryDriverRestaurant(String driverId);
    void registerDriver(String driverId, UUID orderId);
    void setDriverFreeIfOrderIsDelivered(OrderDTO order);
}
