package bg.tuplovdiv.apigateway.dto;

import java.util.Set;
import java.util.UUID;

public class OrderDTO {
    private UUID orderId;
    private String clientId;
    private String deliveryDriverId;
    private String clientPhoneNumber;
    private String address;
    private Set<ItemDTO> items;
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

    public String getDeliveryDriverId() {
        return deliveryDriverId;
    }

    public OrderDTO setDeliveryDriverId(String deliveryDriverId) {
        this.deliveryDriverId = deliveryDriverId;
        return this;
    }

    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public OrderDTO setClientPhoneNumber(String clientPhoneNumber) {
        this.clientPhoneNumber = clientPhoneNumber;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public OrderDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public Set<ItemDTO> getItems() {
        return items;
    }

    public OrderDTO setItems(Set<ItemDTO> items) {
        this.items = items;
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
