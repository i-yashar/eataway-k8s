package bg.tuplovdiv.apigateway.dto;

import java.util.UUID;

public class OrderStatusInfoDTO {

    private UUID orderStatusInfoId;
    private UUID orderId;
    private String time;
    private String infoMessage;

    public UUID getOrderStatusInfoId() {
        return orderStatusInfoId;
    }

    public OrderStatusInfoDTO setOrderStatusInfoId(UUID orderStatusInfoId) {
        this.orderStatusInfoId = orderStatusInfoId;
        return this;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public OrderStatusInfoDTO setOrderId(UUID orderId) {
        this.orderId = orderId;
        return this;
    }

    public String getTime() {
        return time;
    }

    public OrderStatusInfoDTO setTime(String time) {
        this.time = time;
        return this;
    }

    public String getInfoMessage() {
        return infoMessage;
    }

    public OrderStatusInfoDTO setInfoMessage(String infoMessage) {
        this.infoMessage = infoMessage;
        return this;
    }
}
