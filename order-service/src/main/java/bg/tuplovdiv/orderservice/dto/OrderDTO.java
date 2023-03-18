package bg.tuplovdiv.orderservice.dto;

import bg.tuplovdiv.orderservice.model.OrderStatus;
import bg.tuplovdiv.orderservice.model.entity.BasketItemEntity;

import java.util.Set;
import java.util.UUID;

public class OrderDTO {
    private UUID orderId;
    private UUID clientId;
    private String clientPhoneNumber;
    private UUID deliverDriverId;
    private String address;
    private Set<BasketItemEntity> items;
    private Double totalCost;
    private OrderStatus status;

    public UUID getOrderId() {
        return orderId;
    }

    public OrderDTO setOrderId(UUID orderId) {
        this.orderId = orderId;
        return this;
    }

    public UUID getClientId() {
        return clientId;
    }

    public OrderDTO setClientId(UUID clientId) {
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

    public UUID getDeliverDriverId() {
        return deliverDriverId;
    }

    public OrderDTO setDeliverDriverId(UUID deliverDriverId) {
        this.deliverDriverId = deliverDriverId;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public OrderDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public Set<BasketItemEntity> getItems() {
        return items;
    }

    public OrderDTO setItems(Set<BasketItemEntity> items) {
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

    public OrderStatus getStatus() {
        return status;
    }

    public OrderDTO setStatus(OrderStatus status) {
        this.status = status;
        return this;
    }
}
