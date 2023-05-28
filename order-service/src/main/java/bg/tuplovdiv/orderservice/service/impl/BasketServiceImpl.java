package bg.tuplovdiv.orderservice.service.impl;

import bg.tuplovdiv.orderservice.dto.BasketDTO;
import bg.tuplovdiv.orderservice.dto.ItemDTO;
import bg.tuplovdiv.orderservice.exception.MenuNotFoundException;
import bg.tuplovdiv.orderservice.mapper.BasketMapper;
import bg.tuplovdiv.orderservice.mapper.ItemMapper;
import bg.tuplovdiv.orderservice.model.entity.BasketEntity;
import bg.tuplovdiv.orderservice.model.entity.ItemEntity;
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
    private final ItemMapper itemMapper;

    public BasketServiceImpl(BasketRepository basketRepository, UserRepository userRepository, BasketMapper mapper, ItemMapper itemMapper) {
        this.basketRepository = basketRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.itemMapper = itemMapper;
    }

    @Override
    public BasketDTO addBasketItem(String ownerId, ItemDTO item) {
        BasketEntity basket = getBasketEntityByOwnerId(ownerId);
        Set<ItemEntity> basketItems = basket.getItems() == null ? new HashSet<>() : basket.getItems();
        Optional<ItemEntity> optItem = getBasketItem(basketItems, item.getMenu().getMenuId());

        if (optItem.isEmpty()) {
            addNewBasketItem(basketItems, item);
        } else {
            incrementBasketItemCount(optItem.get());
        }

        return mapper.toDTO(basketRepository.save(basket));
    }

    private void addNewBasketItem(Set<ItemEntity> basketItems, ItemDTO item) {
        ItemEntity itemEntity = itemMapper.fromDTO(item);
        basketItems.add(itemEntity);
    }

    private void incrementBasketItemCount(ItemEntity item) {
        item.setCount(item.getCount() + 1);
    }

    @Override
    public BasketDTO getBasketByOwnerId(String ownerId) {
        BasketEntity basket = getBasketEntityByOwnerId(ownerId);

        return mapper.toDTO(basket);
    }

    @Override
    public void deleteBasketItem(String ownerId, UUID menuId) {
        BasketEntity basket = getBasketEntityByOwnerId(ownerId);

        removeBasketItemFromBasket(basket, menuId);

        basketRepository.save(basket);
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
        ItemEntity basketItem = getBasketItem(basket.getItems(), menuId)
                .orElseThrow(() -> new MenuNotFoundException("Menu with menuId " + menuId + " not found"));

        basket.getItems().remove(basketItem);
    }

    private Optional<ItemEntity> getBasketItem(Set<ItemEntity> items, UUID menuId) {
        return items.stream()
                .filter(item -> item.getMenu().getExternalId().equals(menuId))
                .findFirst();
    }
}
