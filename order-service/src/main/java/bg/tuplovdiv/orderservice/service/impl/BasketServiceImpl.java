package bg.tuplovdiv.orderservice.service.impl;

import bg.tuplovdiv.orderservice.dto.BasketDTO;
import bg.tuplovdiv.orderservice.dto.BasketItemDTO;
import bg.tuplovdiv.orderservice.exception.MenuNotFoundException;
import bg.tuplovdiv.orderservice.mapper.BasketMapper;
import bg.tuplovdiv.orderservice.model.entity.BasketEntity;
import bg.tuplovdiv.orderservice.model.entity.BasketItemEntity;
import bg.tuplovdiv.orderservice.model.entity.UserEntity;
import bg.tuplovdiv.orderservice.repository.BasketRepository;
import bg.tuplovdiv.orderservice.repository.UserRepository;
import bg.tuplovdiv.orderservice.service.BasketService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;
    private final UserRepository userRepository;
    private final BasketMapper mapper;

    public BasketServiceImpl(BasketRepository basketRepository, UserRepository userRepository, BasketMapper mapper) {
        this.basketRepository = basketRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public BasketDTO addBasketItem(String ownerId, BasketItemDTO item) {
        BasketEntity basket = getBasketEntityByOwnerId(ownerId);
        Set<BasketItemEntity> basketItems = basket.getItems() == null ? new HashSet<>() : basket.getItems();
        Optional<BasketItemEntity> optItem = getBasketItem(basketItems, item.getMenuId());

        if (optItem.isEmpty()) {
            addNewBasketItem(basketItems, item);
        } else {
            incrementBasketItemCount(optItem.get());
        }

        return mapper.toBasketDTO(basketRepository.save(basket));
    }

    private void addNewBasketItem(Set<BasketItemEntity> basketItems, BasketItemDTO item) {
        BasketItemEntity itemEntity = mapper.toBasketItemEntity(item);
        basketItems.add(itemEntity);
    }

    private void incrementBasketItemCount(BasketItemEntity item) {
        item.setCount(item.getCount() + 1);
    }

    @Override
    public BasketDTO getBasketByOwnerId(String ownerId) {
        BasketEntity basket = getBasketEntityByOwnerId(ownerId);

        return mapper.toBasketDTO(basket);
    }

    @Override
    public void deleteBasketItem(String ownerId, UUID menuId) {
        BasketEntity basket = getBasketEntityByOwnerId(ownerId);
        removeBasketItemFromBasket(basket, menuId);
    }

    private BasketEntity getBasketEntityByOwnerId(String userId) {
        return basketRepository.findBasketEntityByOwnerUserId(userId)
                .orElseGet(() -> createUserBasket(userId));
    }

    private BasketEntity createUserBasket(String userId) {
        UserEntity user = getUser(userId);
        BasketEntity basket = new BasketEntity()
                .setExternalId(UUID.randomUUID())
                .setOwner(user)
                .setItems(new HashSet<>());

        return basketRepository.save(basket);
    }

    private UserEntity getUser(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User with userId " + userId + " not found"));
    }

    private void removeBasketItemFromBasket(BasketEntity basket, UUID menuId) {
        BasketItemEntity basketItem = getBasketItem(basket.getItems(), menuId)
                .orElseThrow(() -> new MenuNotFoundException("Menu with menuId " + menuId + " not found"));

        basket.getItems().remove(basketItem);
    }

    private Optional<BasketItemEntity> getBasketItem(Set<BasketItemEntity> items, UUID menuId) {
        return items.stream()
                .filter(item -> item.getMenuId().equals(menuId))
                .findFirst();
    }
}
