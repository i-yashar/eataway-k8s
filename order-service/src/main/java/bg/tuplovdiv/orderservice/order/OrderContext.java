package bg.tuplovdiv.orderservice.order;

import bg.tuplovdiv.orderservice.dto.BasketDTO;
import bg.tuplovdiv.orderservice.model.OrderStatus;

import java.util.UUID;

public class OrderContext {
    private UUID orderId;
    private UUID clientId;
    private String clientPhoneNumber;
    private String address;
    private BasketDTO basket;
    private Double totalCost;
    private OrderStatus status;

    public UUID getOrderId() {
        return orderId;
    }

    public OrderContext setOrderId(UUID orderId) {
        this.orderId = orderId;
        return this;
    }

    public UUID getClientId() {
        return clientId;
    }

    public OrderContext setClientId(UUID clientId) {
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

    public BasketDTO getBasket() {
        return basket;
    }

    public OrderContext setBasket(BasketDTO basket) {
        this.basket = basket;
        return this;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public OrderContext setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
        return this;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public OrderContext setStatus(OrderStatus status) {
        this.status = status;
        return this;
    }
}
