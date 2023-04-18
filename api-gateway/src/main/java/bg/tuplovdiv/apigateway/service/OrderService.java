package bg.tuplovdiv.apigateway.service;

import bg.tuplovdiv.apigateway.dto.CreateOrderRequest;

import java.util.UUID;

public interface OrderService {
    UUID createOrder(CreateOrderRequest createOrderRequest);
}
