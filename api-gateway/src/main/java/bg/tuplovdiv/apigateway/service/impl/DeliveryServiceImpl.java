package bg.tuplovdiv.apigateway.service.impl;

import bg.tuplovdiv.apigateway.cache.OrderCache;
import bg.tuplovdiv.apigateway.connectivity.client.OrdersRestClient;
import bg.tuplovdiv.apigateway.dto.OrderDTO;
import bg.tuplovdiv.apigateway.messaging.OrderDispatcher;
import bg.tuplovdiv.apigateway.order.OrderQueue;
import bg.tuplovdiv.apigateway.service.DeliveryDriverService;
import bg.tuplovdiv.apigateway.service.DeliveryService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
class DeliveryServiceImpl implements DeliveryService {

    private final OrderDispatcher orderDispatcher;
    private final OrderQueue orderQueue;
    private final OrderCache orderCache;
    private final DeliveryDriverService deliveryDriverService;
    private final OrdersRestClient client;

    DeliveryServiceImpl(OrderDispatcher orderDispatcher, OrderQueue orderQueue, OrderCache orderCache, DeliveryDriverService deliveryDriverService, OrdersRestClient client) {
        this.orderDispatcher = orderDispatcher;
        this.orderQueue = orderQueue;
        this.orderCache = orderCache;
        this.deliveryDriverService = deliveryDriverService;
        this.client = client;
    }

    @Override
    public Collection<OrderDTO> getRegisteredOrders(String driverId) {
        UUID driverRestaurantId = deliveryDriverService.getDeliveryDriverRestaurant(driverId);
        return orderQueue.getRegisteredOrders()
                .stream()
                .filter(o -> o.getItems()
                        .stream()
                        .anyMatch(i -> i.getMenu().getRestaurantId().equals(driverRestaurantId)))
                .toList();
    }

    @Override
    public Collection<OrderDTO> getDeliveryDriverActiveOrders(String deliveryDriverId) {
        return client.getDeliveryDriverActiveOrders(deliveryDriverId);
    }

    @Override
    public OrderDTO getOrderInfo(UUID orderId) {
        return orderCache.getOrder(orderId);
    }

    @Override
    @Transactional
    public OrderDTO takeOrder(UUID orderId, String deliveryDriverId) {
        OrderDTO order = orderQueue.takeOrder(orderId);
        order.setDeliveryDriverId(deliveryDriverId);
        order.setStatus("ACTIVE");

        orderDispatcher.sendOrderUpdate(order);
        deliveryDriverService.registerDriver(deliveryDriverId, orderId);

        return order;
    }

    @Override
    @Transactional
    public OrderDTO updateOrder(UUID orderId, String status) {
        OrderDTO order = orderCache.getOrder(orderId);
        order.setStatus(status);

        orderDispatcher.sendOrderUpdate(order);
        deliveryDriverService.setDriverFreeIfOrderIsDelivered(order);

        return order;
    }
}
