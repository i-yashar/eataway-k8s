package bg.tuplovdiv.orderservice.messaging.delivery;

import bg.tuplovdiv.orderservice.messaging.Message;

import java.util.UUID;

public class OrderStatusChange extends Message {

    private UUID orderId;
    private String clientId;
    private String deliveryDriverId;
    private String status;

    public UUID getOrderId() {
        return orderId;
    }

    public OrderStatusChange setOrderId(UUID orderId) {
        this.orderId = orderId;
        return this;
    }

    public String getClientId() {
        return clientId;
    }

    public OrderStatusChange setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getDeliveryDriverId() {
        return deliveryDriverId;
    }

    public OrderStatusChange setDeliveryDriverId(String deliveryDriverId) {
        this.deliveryDriverId = deliveryDriverId;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public OrderStatusChange setStatus(String status) {
        this.status = status;
        return this;
    }
}
