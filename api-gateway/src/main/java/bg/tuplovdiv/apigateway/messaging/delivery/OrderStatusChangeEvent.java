package bg.tuplovdiv.apigateway.messaging.delivery;

import bg.tuplovdiv.apigateway.messaging.Message;

import java.util.UUID;

public class OrderStatusChangeEvent extends Message {

    private UUID orderId;
    private String clientId;
    private String deliveryDriverId;
    private String status;

    public UUID getOrderId() {
        return orderId;
    }

    public OrderStatusChangeEvent setOrderId(UUID orderId) {
        this.orderId = orderId;
        return this;
    }

    public String getClientId() {
        return clientId;
    }

    public OrderStatusChangeEvent setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getDeliveryDriverId() {
        return deliveryDriverId;
    }

    public OrderStatusChangeEvent setDeliveryDriverId(String deliveryDriverId) {
        this.deliveryDriverId = deliveryDriverId;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public OrderStatusChangeEvent setStatus(String status) {
        this.status = status;
        return this;
    }
}
