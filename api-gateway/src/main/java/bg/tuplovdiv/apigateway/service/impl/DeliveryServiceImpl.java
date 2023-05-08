package bg.tuplovdiv.apigateway.service.impl;

import bg.tuplovdiv.apigateway.connectivity.client.OrdersRestClient;
import bg.tuplovdiv.apigateway.dto.OrderDTO;
import bg.tuplovdiv.apigateway.order.OrderQueue;
import bg.tuplovdiv.apigateway.service.DeliveryService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private final OrderQueue orderQueue;
    private final OrdersRestClient client;

    public DeliveryServiceImpl(OrderQueue orderQueue, OrdersRestClient client) {
        this.orderQueue = orderQueue;
        this.client = client;
    }

    @Override
    public OrderDTO takeOrder(UUID orderId) {
        orderQueue.takeOrder(orderId);
        return client.takeOrder(orderId);
        //todo: 1. change orders service controller - one handler for taking orders, another for updating status; 2. add new methods to the orders client; 3. finish delivery service and controller
    }

    @Override
    public OrderDTO updateOrder(UUID orderId) {
        return null;
    }
}
