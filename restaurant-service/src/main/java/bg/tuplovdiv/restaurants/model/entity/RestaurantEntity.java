package bg.tuplovdiv.restaurants.model.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "restaurants")
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private UUID externalId;

    @Column(nullable = false)
    private String name;

    private String address;

    private String description;

    public Long getId() {
        return id;
    }

    public RestaurantEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public UUID getExternalId() {
        return externalId;
    }

    public RestaurantEntity setExternalId(UUID externalId) {
        this.externalId = externalId;
        return this;
    }

    public String getName() {
        return name;
    }

    public RestaurantEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public RestaurantEntity setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public RestaurantEntity setDescription(String description) {
        this.description = description;
        return this;
    }
}
