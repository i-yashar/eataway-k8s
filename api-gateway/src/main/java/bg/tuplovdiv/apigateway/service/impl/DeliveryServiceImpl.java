package bg.tuplovdiv.apigateway.service.impl;

import bg.tuplovdiv.apigateway.connectivity.client.OrdersRestClient;
import bg.tuplovdiv.apigateway.dto.OrderDTO;
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
public class DeliveryServiceImpl implements DeliveryService {

    private final OrderQueue orderQueue;
    private final DeliveryDriverRepository deliveryDriverRepository;
    private final OrdersRestClient client;

    public DeliveryServiceImpl(OrderQueue orderQueue, DeliveryDriverRepository deliveryDriverRepository, OrdersRestClient client) {
        this.orderQueue = orderQueue;
        this.deliveryDriverRepository = deliveryDriverRepository;
        this.client = client;
    }

    @Override
    public Collection<OrderDTO> getRegisteredOrders() {
        return orderQueue.getRegisteredOrders();
    }

    @Override
    public Collection<OrderDTO> getDeliveryDriverActiveOrders(String deliveryDriverId) {
        return client.getDeliveryDriverActiveOrders(deliveryDriverId);
    }

    @Override
    public OrderDTO getOrderInfo(UUID orderId) {
        return client.getUserOrder(orderId);
    }

    @Override
    @Transactional
    public OrderDTO takeOrder(UUID orderId, String deliveryDriverId) {
        OrderDTO order = orderQueue.takeOrder(orderId);
        order.setDeliveryDriverId(deliveryDriverId);
        order.setStatus("ACTIVE");

        order = client.updateOrder(order);
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
        OrderDTO order = client.getUserOrder(orderId);
        order.setStatus(status);

        order = client.updateOrder(order);
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
