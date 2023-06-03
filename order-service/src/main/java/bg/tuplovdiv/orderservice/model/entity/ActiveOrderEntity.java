package bg.tuplovdiv.orderservice.model.entity;

import bg.tuplovdiv.orderservice.model.enums.OrderStatus;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "active_orders")
public class ActiveOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private UUID externalId;

    @Column(nullable = false)
    private String clientId;

    private String deliveryDriverId;

    @Column(nullable = false)
    private String clientPhoneNumber;

    @Column(nullable = false)
    private String address;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<ItemEntity> items;

    @Column(nullable = false)
    private Double totalCost;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    public Long getId() {
        return id;
    }

    public ActiveOrderEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public UUID getExternalId() {
        return externalId;
    }

    public ActiveOrderEntity setExternalId(UUID externalId) {
        this.externalId = externalId;
        return this;
    }

    public String getClientId() {
        return clientId;
    }

    public ActiveOrderEntity setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getDeliveryDriverId() {
        return deliveryDriverId;
    }

    public ActiveOrderEntity setDeliveryDriverId(String deliveryDriverId) {
        this.deliveryDriverId = deliveryDriverId;
        return this;
    }

    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public ActiveOrderEntity setClientPhoneNumber(String clientPhoneNumber) {
        this.clientPhoneNumber = clientPhoneNumber;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public ActiveOrderEntity setAddress(String address) {
        this.address = address;
        return this;
    }

    public Set<ItemEntity> getItems() {
        return items;
    }

    public ActiveOrderEntity setItems(Set<ItemEntity> items) {
        this.items = items;
        return this;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public ActiveOrderEntity setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
        return this;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public ActiveOrderEntity setStatus(OrderStatus status) {
        this.status = status;
        return this;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public ActiveOrderEntity setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    private Instant updatedAt;

    public OrderEntity toOrder() {
        return new OrderEntity()
                .setId(id)
                .setExternalId(externalId)
                .setClientId(clientId)
                .setDeliveryDriverId(deliveryDriverId)
                .setClientPhoneNumber(clientPhoneNumber)
                .setAddress(address)
                .setItems(items)
                .setTotalCost(totalCost)
                .setStatus(status)
                .setUpdatedAt(updatedAt);
    }
}
