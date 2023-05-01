package bg.tuplovdiv.apigateway.service.impl;

import bg.tuplovdiv.apigateway.connectivity.client.OrdersRestClient;
import bg.tuplovdiv.apigateway.dto.CreateOrderRequest;
import bg.tuplovdiv.apigateway.dto.OrderDTO;
import bg.tuplovdiv.apigateway.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrdersRestClient client;

    public OrderServiceImpl(OrdersRestClient client) {
        this.client = client;
    }

    @Override
    public UUID createOrder(CreateOrderRequest createOrderRequest) {
        String location = client.createOrder(createOrderRequest);
        return extractOrderId(location);
    }

    private UUID extractOrderId(String location) {
        int startIndex = location.lastIndexOf('/') + 1;
        return UUID.fromString(location.substring(startIndex));
    }

    @Override
    public OrderDTO getOrderInfo(UUID orderId) {
        return null;
    }
}