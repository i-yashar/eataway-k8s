package bg.tuplovdiv.apigateway.service.impl;

import bg.tuplovdiv.apigateway.cache.OrderCache;
import bg.tuplovdiv.apigateway.connectivity.client.OrdersRestClient;
import bg.tuplovdiv.apigateway.dto.OrderDTO;
import bg.tuplovdiv.apigateway.messaging.OrderDispatcher;
import bg.tuplovdiv.apigateway.model.entity.DeliveryDriverEntity;
import bg.tuplovdiv.apigateway.order.OrderQueue;
import bg.tuplovdiv.apigateway.repository.DeliveryDriverRepository;
import bg.tuplovdiv.apigateway.service.DeliveryService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
class DeliveryServiceImpl implements DeliveryService {

    private final OrderDispatcher orderDispatcher;
    private final OrderQueue orderQueue;
    private final OrderCache orderCache;
    private final DeliveryDriverRepository deliveryDriverRepository;
    private final OrdersRestClient client;

    DeliveryServiceImpl(OrderDispatcher orderDispatcher, OrderQueue orderQueue, OrderCache orderCache, DeliveryDriverRepository deliveryDriverRepository, OrdersRestClient client) {
        this.orderDispatcher = orderDispatcher;
        this.orderQueue = orderQueue;
        this.orderCache = orderCache;
        this.deliveryDriverRepository = deliveryDriverRepository;
        this.client = client;
    }

    @Override
    public Collection<OrderDTO> getRegisteredOrders(String driverId) {
        UUID driverRestaurantId = getDeliveryDriver(driverId).getRestaurantId();
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
        registerDriver(deliveryDriverId, orderId);

        return order;
    }

    private void registerDriver(String deliveryDriverId, UUID orderId) {
        DeliveryDriverEntity deliveryDriver = getDeliveryDriver(deliveryDriverId);

        deliveryDriver.setFree(false);
        deliveryDriver.setCurrentOrderId(orderId);
        deliveryDriverRepository.save(deliveryDriver);
    }

    @Override
    @Transactional
    public OrderDTO updateOrder(UUID orderId, String status) {
        OrderDTO order = orderCache.getOrder(orderId);
        order.setStatus(status);

        orderDispatcher.sendOrderUpdate(order);
        setDriverFreeIfOrderIsDelivered(order);

        return order;
    }


    private void setDriverFreeIfOrderIsDelivered(OrderDTO order) {
        if (order.getStatus().equals("DELIVERED")) {
            DeliveryDriverEntity deliveryDriver = getDeliveryDriver(order.getDeliveryDriverId());

            deliveryDriver.setFree(true);
            deliveryDriver.setCurrentOrderId(null);
            deliveryDriverRepository.save(deliveryDriver);
        }
    }

    private DeliveryDriverEntity getDeliveryDriver(String deliveryDriverId) {
        return deliveryDriverRepository.findByDeliveryDriverId(deliveryDriverId)
                .orElseThrow(() -> new UsernameNotFoundException("Delivery driver not found."));
    }
}
