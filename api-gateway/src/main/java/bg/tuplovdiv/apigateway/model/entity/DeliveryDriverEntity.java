package bg.tuplovdiv.apigateway.model.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "delivery_drivers")
public class DeliveryDriverEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String deliveryDriverId;

    @Column(nullable = false)
    private Boolean isFree;

    private UUID currentOrderId;

    @Column(nullable = false)
    private UUID restaurantId;

    public Long getId() {
        return id;
    }

    public DeliveryDriverEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getDeliveryDriverId() {
        return deliveryDriverId;
    }

    public DeliveryDriverEntity setDeliveryDriverId(String deliveryDriverId) {
        this.deliveryDriverId = deliveryDriverId;
        return this;
    }

    public Boolean getFree() {
        return isFree;
    }

    public Boolean isFree() {
        return isFree;
    }

    public DeliveryDriverEntity setFree(Boolean free) {
        isFree = free;
        return this;
    }

    public UUID getCurrentOrderId() {
        return currentOrderId;
    }

    public DeliveryDriverEntity setCurrentOrderId(UUID currentOrderId) {
        this.currentOrderId = currentOrderId;
        return this;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public DeliveryDriverEntity setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
        return this;
    }
}
