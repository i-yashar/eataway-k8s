package bg.tuplovdiv.apigateway.cache;

import bg.tuplovdiv.apigateway.connectivity.client.OrdersRestClient;
import bg.tuplovdiv.apigateway.dto.OrderDTO;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OrderCache {

    private final OrdersRestClient client;

    public OrderCache(OrdersRestClient client) {
        this.client = client;
    }

    @Cacheable(value = "order", key = "#orderId")
    public OrderDTO getOrder(UUID orderId) {
        return client.getUserOrder(orderId);
    }

    @CachePut(value = "order", key = "#order.orderId")
    public OrderDTO updateOrder(OrderDTO order) {
        return order;
    }
}
