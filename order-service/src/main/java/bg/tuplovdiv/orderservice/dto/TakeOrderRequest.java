package bg.tuplovdiv.orderservice.dto;

import bg.tuplovdiv.orderservice.validation.order.ValidTakeOrderRequest;

import java.util.UUID;

@ValidTakeOrderRequest
public class TakeOrderRequest {

    public static final String ORDER_ID_JSON_PROPERTY = "orderId";
    public static final String DELIVERY_DRIVER_ID_JSON_PROPERTY = "deliveryDriverId";

    private UUID orderId;
    private String deliveryDriverId;

    public UUID getOrderId() {
        return orderId;
    }

    public TakeOrderRequest setOrderId(UUID orderId) {
        this.orderId = orderId;
        return this;
    }

    public String getDeliveryDriverId() {
        return deliveryDriverId;
    }

    public TakeOrderRequest setDeliveryDriverId(String deliveryDriverId) {
        this.deliveryDriverId = deliveryDriverId;
        return this;
    }

}
