package bg.tuplovdiv.restaurants.mapper;

import bg.tuplovdiv.restaurants.dto.RestaurantDTO;
import bg.tuplovdiv.restaurants.model.entity.RestaurantEntity;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMapper {

    public RestaurantDTO toDTO(RestaurantEntity restaurant) {
        return new RestaurantDTO()
                .setRestaurantId(restaurant.getExternalId())
                .setName(restaurant.getName())
                .setAddress(restaurant.getAddress())
                .setDescription(restaurant.getDescription());
    }
}
