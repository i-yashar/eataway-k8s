package bg.tuplovdiv.apigateway.dto;

import java.util.UUID;

public class MenuDTO {

    private UUID menuId;
    private String name;
    private String description;
    private Double price;
    private UUID restaurantId;
    private String restaurantName;
    private String restaurantAddress;

    public UUID getMenuId() {
        return menuId;
    }

    public MenuDTO setMenuId(UUID menuId) {
        this.menuId = menuId;
        return this;
    }

    public String getName() {
        return name;
    }

    public MenuDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public MenuDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public MenuDTO setPrice(Double price) {
        this.price = price;
        return this;
    }

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public MenuDTO setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
        return this;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public MenuDTO setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
        return this;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public MenuDTO setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
        return this;
    }
}
