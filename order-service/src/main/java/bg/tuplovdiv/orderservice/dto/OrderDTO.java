package bg.tuplovdiv.orderservice.dto;

import java.util.UUID;

public class OrderDTO {
    private UUID orderId;
    private String clientId;
    private String clientPhoneNumber;
    private String deliveryDriverId;
    private String address;
    private Double totalCost;
    private String status;

    public UUID getOrderId() {
        return orderId;
    }

    public OrderDTO setOrderId(UUID orderId) {
        this.orderId = orderId;
        return this;
    }

    public String getClientId() {
        return clientId;
    }

    public OrderDTO setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public OrderDTO setClientPhoneNumber(String clientPhoneNumber) {
        this.clientPhoneNumber = clientPhoneNumber;
        return this;
    }

    public String getDeliveryDriverId() {
        return deliveryDriverId;
    }

    public OrderDTO setDeliveryDriverId(String deliveryDriverId) {
        this.deliveryDriverId = deliveryDriverId;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public OrderDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public OrderDTO setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public OrderDTO setStatus(String status) {
        this.status = status;
        return this;
    }
}
