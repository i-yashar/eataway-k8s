package bg.tuplovdiv.orderservice.model.entity;

import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "menus")
public class MenuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private UUID externalId;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private Double price;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<ItemEntity> items;

    @ManyToOne
    private RestaurantEntity restaurant;

    public Long getId() {
        return id;
    }

    public MenuEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public UUID getExternalId() {
        return externalId;
    }

    public MenuEntity setExternalId(UUID externalId) {
        this.externalId = externalId;
        return this;
    }

    public String getName() {
        return name;
    }

    public MenuEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public MenuEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public MenuEntity setPrice(Double price) {
        this.price = price;
        return this;
    }

    public Set<ItemEntity> getItems() {
        return items;
    }

    public MenuEntity setItems(Set<ItemEntity> items) {
        this.items = items;
        return this;
    }

    public RestaurantEntity getRestaurant() {
        return restaurant;
    }

    public MenuEntity setRestaurant(RestaurantEntity restaurant) {
        this.restaurant = restaurant;
        return this;
    }
}
