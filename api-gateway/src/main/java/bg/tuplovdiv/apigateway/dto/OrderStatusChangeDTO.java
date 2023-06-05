package bg.tuplovdiv.apigateway.dto;

import java.util.UUID;

public class OrderStatusChangeDTO {

    private UUID orderId;
    private String clientId;
    private String deliveryDriverId;
    private String status;

    public UUID getOrderId() {
        return orderId;
    }

    public OrderStatusChangeDTO setOrderId(UUID orderId) {
        this.orderId = orderId;
        return this;
    }

    public String getClientId() {
        return clientId;
    }

    public OrderStatusChangeDTO setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getDeliveryDriverId() {
        return deliveryDriverId;
    }

    public OrderStatusChangeDTO setDeliveryDriverId(String deliveryDriverId) {
        this.deliveryDriverId = deliveryDriverId;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public OrderStatusChangeDTO setStatus(String status) {
        this.status = status;
        return this;
    }
}
