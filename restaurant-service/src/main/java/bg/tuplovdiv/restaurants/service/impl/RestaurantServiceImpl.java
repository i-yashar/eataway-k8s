package bg.tuplovdiv.restaurants.service.impl;

import bg.tuplovdiv.restaurants.dto.RestaurantDTO;
import bg.tuplovdiv.restaurants.dto.page.PageDTO;
import bg.tuplovdiv.restaurants.exception.RestaurantNotFoundException;
import bg.tuplovdiv.restaurants.mapper.RestaurantMapper;
import bg.tuplovdiv.restaurants.model.entity.RestaurantEntity;
import bg.tuplovdiv.restaurants.repository.RestaurantRepository;
import bg.tuplovdiv.restaurants.service.RestaurantService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper mapper;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, RestaurantMapper mapper) {
        this.restaurantRepository = restaurantRepository;
        this.mapper = mapper;
    }

    @Override
    public RestaurantDTO findRestaurantByRestaurantId(UUID restaurantId) {
        RestaurantEntity restaurant = getRestaurantByRestaurantId(restaurantId);

        return mapper.toDTO(restaurant);
    }

    private RestaurantEntity getRestaurantByRestaurantId(UUID restaurantId) {
        return restaurantRepository.findByExternalId(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found"));
    }

    @Override
    public PageDTO<RestaurantDTO> findAllRestaurants(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return getRestaurantsPage(pageable);
    }

    private PageDTO<RestaurantDTO> getRestaurantsPage(Pageable pageable) {
        Page<RestaurantEntity> restaurants = restaurantRepository.findAll(pageable);
        return new PageDTO<RestaurantDTO>()
                .setContent(mapToListOfDTOs(restaurants.getContent()))
                .setPageInfo(restaurants.getContent().size(), restaurants.hasNext());
    }

    private List<RestaurantDTO> mapToListOfDTOs(List<RestaurantEntity> restaurants) {
        return restaurants.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RestaurantDTO saveRestaurant(RestaurantDTO restaurantDTO) {
        RestaurantEntity restaurantEntity = mapper.toEntity(restaurantDTO);

        return mapper.toDTO(restaurantRepository.save(restaurantEntity));
    }
}
