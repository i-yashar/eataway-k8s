package bg.tuplovdiv.apigateway.messaging;

import bg.tuplovdiv.apigateway.dto.ItemDTO;

import java.util.Set;
import java.util.UUID;

public class OrderContext extends Message {
    private UUID orderId;
    private String clientId;
    private String clientPhoneNumber;
    private String address;
    private Set<ItemDTO> items;
    private Double totalCost;

    public UUID getOrderId() {
        return orderId;
    }

    public OrderContext setOrderId(UUID orderId) {
        this.orderId = orderId;
        return this;
    }

    public String getClientId() {
        return clientId;
    }

    public OrderContext setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public OrderContext setClientPhoneNumber(String clientPhoneNumber) {
        this.clientPhoneNumber = clientPhoneNumber;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public OrderContext setAddress(String address) {
        this.address = address;
        return this;
    }

    public Set<ItemDTO> getItems() {
        return items;
    }

    public OrderContext setItems(Set<ItemDTO> items) {
        this.items = items;
        return this;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public OrderContext setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
        return this;
    }
}
