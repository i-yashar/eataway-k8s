package bg.tuplovdiv.orderservice.dto;

import bg.tuplovdiv.orderservice.model.OrderStatus;
import bg.tuplovdiv.orderservice.validation.order.ValidOrder;

import java.util.UUID;

@ValidOrder
public class OrderRequest {
    public static final String ORDER_ID_JSON_PROPERTY = "orderId";
    public static final String CLIENT_ID_JSON_PROPERTY = "clientId";
    public static final String CLIENT_PHONE_NUMBER_JSON_PROPERTY = "clientPhoneNumber";
    public static final String DELIVERY_DRIVER_ID_JSON_PROPERTY = "deliveryDriverId";
    public static final String ADDRESS_JSON_PROPERTY = "address";
    public static final String BASKET_ID_JSON_PROPERTY = "basketId";
    public static final String TOTAL_COST_JSON_PROPERTY = "totalCost";
    public static final String STATUS_JSON_PROPERTY = "status";

    private UUID orderId;
    private UUID clientId;
    private String clientPhoneNumber;
    private UUID deliveryDriverId;
    private String address;
    private UUID basketId;
    private Double totalCost;
    private OrderStatus status;

    public UUID getOrderId() {
        return orderId;
    }

    public OrderRequest setOrderId(UUID orderId) {
        this.orderId = orderId;
        return this;
    }

    public UUID getClientId() {
        return clientId;
    }

    public OrderRequest setClientId(UUID clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public OrderRequest setClientPhoneNumber(String clientPhoneNumber) {
        this.clientPhoneNumber = clientPhoneNumber;
        return this;
    }

    public UUID getDeliveryDriverId() {
        return deliveryDriverId;
    }

    public OrderRequest setDeliveryDriverId(UUID deliveryDriverId) {
        this.deliveryDriverId = deliveryDriverId;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public OrderRequest setAddress(String address) {
        this.address = address;
        return this;
    }

    public UUID getBasketId() {
        return basketId;
    }

    public OrderRequest setBasketId(UUID basketId) {
        this.basketId = basketId;
        return this;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public OrderRequest setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
        return this;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public OrderRequest setStatus(OrderStatus status) {
        this.status = status;
        return this;
    }
}
