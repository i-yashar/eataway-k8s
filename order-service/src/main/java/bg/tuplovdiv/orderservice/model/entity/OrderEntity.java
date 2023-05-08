package bg.tuplovdiv.orderservice.model.entity;

import bg.tuplovdiv.orderservice.model.OrderStatus;
import jakarta.persistence.*;

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

    @Column(nullable = false)
    private String clientPhoneNumber;

    private String deliveryDriverId;

    @Column(nullable = false)
    private String address;

    @ManyToMany
    private Set<MenuEntity> menus;

    @Column(nullable = false)
    private Double totalCost;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

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

    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public OrderEntity setClientPhoneNumber(String clientPhone) {
        this.clientPhoneNumber = clientPhone;
        return this;
    }

    public String getDeliveryDriverId() {
        return deliveryDriverId;
    }

    public OrderEntity setDeliveryDriverId(String deliverDriverId) {
        this.deliveryDriverId = deliverDriverId;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public OrderEntity setAddress(String address) {
        this.address = address;
        return this;
    }

    public Set<MenuEntity> getMenus() {
        return menus;
    }

    public OrderEntity setMenus(Set<MenuEntity> menus) {
        this.menus = menus;
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
}
