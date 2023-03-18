package bg.tuplovdiv.orderservice.model.entity;

import jakarta.persistence.*;

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
    private UUID clientId;

    @Column(nullable = false)
    private String clientPhoneNumber;

    @Column(nullable = false)
    private UUID deliverDriverId;

    @Column(nullable = false)
    private String address;

    @OneToOne
    private BasketEntity basket;

    @Column(nullable = false)
    private Double totalCost;

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

    public UUID getClientId() {
        return clientId;
    }

    public OrderEntity setClientId(UUID clientId) {
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

    public UUID getDeliverDriverId() {
        return deliverDriverId;
    }

    public OrderEntity setDeliverDriverId(UUID deliverDriverId) {
        this.deliverDriverId = deliverDriverId;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public OrderEntity setAddress(String address) {
        this.address = address;
        return this;
    }

    public BasketEntity getBasket() {
        return basket;
    }

    public OrderEntity setBasket(BasketEntity basket) {
        this.basket = basket;
        return this;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public OrderEntity setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
        return this;
    }
}
