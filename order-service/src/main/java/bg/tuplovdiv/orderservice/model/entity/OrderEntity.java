package bg.tuplovdiv.orderservice.model.entity;

import bg.tuplovdiv.orderservice.model.enums.OrderStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class OrderEntity {

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
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Set<ItemEntity> items;

    @Column(nullable = false)
    private Double totalCost;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    private Instant updatedAt;

    public Long getId() {
        return id;
    }

    public OrderEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public UUID getExternalId() {
        return externalId;
    }

    public OrderEntity setExternalId(UUID externalId) {
        this.externalId = externalId;
        return this;
    }

    public String getClientId() {
        return clientId;
    }

    public OrderEntity setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getDeliveryDriverId() {
        return deliveryDriverId;
    }

    public OrderEntity setDeliveryDriverId(String deliveryDriverId) {
        this.deliveryDriverId = deliveryDriverId;
        return this;
    }

    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public OrderEntity setClientPhoneNumber(String clientPhoneNumber) {
        this.clientPhoneNumber = clientPhoneNumber;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public OrderEntity setAddress(String address) {
        this.address = address;
        return this;
    }

    public Set<ItemEntity> getItems() {
        return items;
    }

    public OrderEntity setItems(Set<ItemEntity> items) {
        this.items = items;
        return this;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public OrderEntity setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
        return this;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public OrderEntity setStatus(OrderStatus status) {
        this.status = status;
        return this;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public OrderEntity setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
