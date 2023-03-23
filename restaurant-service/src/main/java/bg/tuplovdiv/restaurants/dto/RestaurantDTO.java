package bg.tuplovdiv.restaurants.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public class RestaurantDTO {

    private UUID restaurantId;

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    private String description;

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public RestaurantDTO setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
        return this;
    }

    public String getName() {
        return name;
    }

    public RestaurantDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public RestaurantDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public RestaurantDTO setDescription(String description) {
        this.description = description;
        return this;
    }
}
