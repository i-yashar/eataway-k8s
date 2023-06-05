package bg.tuplovdiv.orderservice.dto;

import java.util.UUID;

public class OrderStatusInfoDTO {

    private UUID orderId;
    private String time;
    private String infoMessage;

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
