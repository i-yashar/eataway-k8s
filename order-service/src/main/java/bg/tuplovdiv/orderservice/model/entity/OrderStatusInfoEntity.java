package bg.tuplovdiv.orderservice.model.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "orders_status_infos")
public class OrderStatusInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private UUID externalId;

    @ManyToOne
    private OrderEntity order;

    private String time;

    private String infoMessage;

    public Long getId() {
        return id;
    }

    public OrderStatusInfoEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public UUID getExternalId() {
        return externalId;
    }

    public OrderStatusInfoEntity setExternalId(UUID externalId) {
        this.externalId = externalId;
        return this;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public OrderStatusInfoEntity setOrder(OrderEntity order) {
        this.order = order;
        return this;
    }

    public String getTime() {
        return time;
    }

    public OrderStatusInfoEntity setTime(String time) {
        this.time = time;
        return this;
    }

    public String getInfoMessage() {
        return infoMessage;
    }

    public OrderStatusInfoEntity setInfoMessage(String infoMessage) {
        this.infoMessage = infoMessage;
        return this;
    }
}
