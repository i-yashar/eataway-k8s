package bg.tuplovdiv.apigateway.service.impl;

import bg.tuplovdiv.apigateway.connectivity.client.OrdersRestClient;
import bg.tuplovdiv.apigateway.dto.OrderDTO;
import bg.tuplovdiv.apigateway.order.OrderQueue;
import bg.tuplovdiv.apigateway.order.delivery.DriverManager;
import bg.tuplovdiv.apigateway.service.DeliveryService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private final OrderQueue orderQueue;
    private final DriverManager driverManager;
    private final OrdersRestClient client;

    public DeliveryServiceImpl(OrderQueue orderQueue, DriverManager driverManager, OrdersRestClient client) {
        this.orderQueue = orderQueue;
        this.driverManager = driverManager;
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
    public OrderDTO takeOrder(UUID orderId, String deliveryDriverId) {
        OrderDTO order = orderQueue.takeOrder(orderId);

        order.setDeliveryDriverId(deliveryDriverId);
        order.setStatus("ACTIVE");
        driverManager.registerDriver(deliveryDriverId, orderId);

        OrderDTO updatedOrder = null;

        try {
            updatedOrder = client.updateOrder(order);
        } catch (Exception e) {
            resetOrder(order);
        }

        return updatedOrder;
    }

    private void resetOrder(OrderDTO order) {
        driverManager.deregisterDriver(order.getDeliveryDriverId());
        order.setDeliveryDriverId(null);
        order.setStatus("REGISTERED");
        orderQueue.registerOrder(order);
    }

    @Override
    public OrderDTO updateOrder(UUID orderId, String status) {
        OrderDTO order = client.getUserOrder(orderId);
        order.setStatus(status);

        OrderDTO updatedOrder = client.updateOrder(order);

        deregisterDriverIfOrderDelivered(updatedOrder);

        return updatedOrder;
    }

    private void deregisterDriverIfOrderDelivered(OrderDTO order) {
        if (order.getStatus().equals("DELIVERED")) {
            driverManager.deregisterDriver(order.getDeliveryDriverId());
        }
    }
}
