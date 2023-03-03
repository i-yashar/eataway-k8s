package bg.tuplovdiv.restaurants.model.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "items")
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private UUID externalId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer quantity;

    public Long getId() {
        return id;
    }

    public UUID getExternalId() {
        return externalId;
    }

    public ItemEntity setExternalId(UUID externalId) {
        this.externalId = externalId;
        return this;
    }

    public ItemEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ItemEntity setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public ItemEntity setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }
}
