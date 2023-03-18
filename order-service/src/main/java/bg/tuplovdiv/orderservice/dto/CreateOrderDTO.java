package bg.tuplovdiv.orderservice.dto;

import java.util.UUID;

public class CreateOrderDTO {
    private UUID clientId;
    private String clientPhoneNumber;
    private String address;
    private UUID basketId;
    private Double totalCost;

    public UUID getClientId() {
        return clientId;
    }

    public CreateOrderDTO setClientId(UUID clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public CreateOrderDTO setClientPhoneNumber(String clientPhoneNumber) {
        this.clientPhoneNumber = clientPhoneNumber;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public CreateOrderDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public UUID getBasketId() {
        return basketId;
    }

    public CreateOrderDTO setBasketId(UUID basketId) {
        this.basketId = basketId;
        return this;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public CreateOrderDTO setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
        return this;
    }
}
