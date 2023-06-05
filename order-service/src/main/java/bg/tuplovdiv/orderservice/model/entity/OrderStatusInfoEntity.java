package bg.tuplovdiv.orderservice.model.entity;

import bg.tuplovdiv.orderservice.model.enums.OrderStatusInfo;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "orders_status_infos")
public class OrderStatusInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private UUID orderId;

    private String time;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private OrderStatusInfo infoMessage;

    public Long getId() {
        return id;
    }

    public OrderStatusInfoEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public OrderStatusInfoEntity setOrderId(UUID orderId) {
        this.orderId = orderId;
        return this;
    }

    public String getTime() {
        return time;
    }

    public OrderStatusInfoEntity setTime(String time) {
        this.time = time;
        return this;
    }

    public OrderStatusInfo getInfoMessage() {
        return infoMessage;
    }

    public OrderStatusInfoEntity setInfoMessage(OrderStatusInfo infoMessage) {
        this.infoMessage = infoMessage;
        return this;
    }
}
