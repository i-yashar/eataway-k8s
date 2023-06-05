package bg.tuplovdiv.apigateway.cache;

import bg.tuplovdiv.apigateway.connectivity.client.OrdersRestClient;
import bg.tuplovdiv.apigateway.dto.OrderDTO;
import bg.tuplovdiv.apigateway.dto.OrderStatusInfoDTO;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Comparator;
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

    @Cacheable(value = "orderStatusInfos", key = "#orderId")
    public Collection<OrderStatusInfoDTO> getOrderStatusInfoMessages(UUID orderId) {
        return client.getOrderStatusInfoMessages(orderId);
    }

    @CachePut(value = "orderStatusInfos", key = "#orderStatusInfo.orderId")
    public Collection<OrderStatusInfoDTO> updateOrderStatusInfoMessages(OrderStatusInfoDTO orderStatusInfo) {
        Collection<OrderStatusInfoDTO> messages = getOrderStatusInfoMessages(orderStatusInfo.getOrderId());
        messages.removeIf(i -> i.getInfoMessage().equals(orderStatusInfo.getInfoMessage()));
        messages.add(orderStatusInfo);

        return messages.stream()
                .sorted(Comparator.comparing(OrderStatusInfoDTO::getTime))
                .toList();
    }
}
