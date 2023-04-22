package bg.tuplovdiv.orderservice.service.impl;

import bg.tuplovdiv.orderservice.dto.BasketDTO;
import bg.tuplovdiv.orderservice.dto.BasketItemDTO;
import bg.tuplovdiv.orderservice.exception.BasketEmptyException;
import bg.tuplovdiv.orderservice.exception.BasketNotFoundException;
import bg.tuplovdiv.orderservice.exception.MenuNotFoundException;
import bg.tuplovdiv.orderservice.mapper.BasketMapper;
import bg.tuplovdiv.orderservice.model.entity.BasketEntity;
import bg.tuplovdiv.orderservice.model.entity.BasketItemEntity;
import bg.tuplovdiv.orderservice.repository.BasketRepository;
import bg.tuplovdiv.orderservice.service.BasketService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;
    private final BasketMapper mapper;

    public BasketServiceImpl(BasketRepository basketRepository, BasketMapper mapper) {
        this.basketRepository = basketRepository;
        this.mapper = mapper;
    }

    @Override
    public BasketDTO addBasketItem(UUID ownerId, BasketItemDTO basketItem) {
        BasketEntity basket = getBasketEntityByOwnerId(ownerId);
        Set<BasketItemEntity> items = getBasketItems(basket);
        Optional<BasketItemEntity> optItem = getBasketItem(items, basketItem.getMenu().getMenuId());

        if(optItem.isEmpty()) {
            addNewBasketItem(items, basketItem);
        } else {
            incrementBasketItemCount(optItem.get());
        }

        return mapper.toBasketDTO(basketRepository.save(basket));
    }

    private void addNewBasketItem(Set<BasketItemEntity> items, BasketItemDTO basketItem) {
        BasketItemEntity item = mapper.toBasketItemEntity(basketItem);
        items.add(item);
    }

    private void incrementBasketItemCount(BasketItemEntity item) {
        item.setCount(item.getCount() + 1);
    }

    private Set<BasketItemEntity> getBasketItems(BasketEntity basket) {
        Set<BasketItemEntity> items = basket.getItems();

        if(items == null) {
            items = new HashSet<>();
        }

        return items;
    }

    @Override
    public BasketDTO getBasketByOwnerId(UUID ownerId) {
        BasketEntity basket = getBasketEntityByOwnerId(ownerId);

        return mapper.toBasketDTO(basket);
    }

    private BasketEntity getBasketEntity(UUID basketId) {
        return basketRepository.findBasketEntityByExternalId(basketId)
                .orElseThrow(() -> new BasketNotFoundException("Basket with basketId " + basketId + " not found"));
    }

    @Override
    public void deleteBasketItem(UUID ownerId, UUID menuId) {
        BasketEntity basket = getBasketEntityByOwnerId(ownerId);
        removeBasketItemFromBasket(basket, menuId);
    }

    private BasketEntity getBasketEntityByOwnerId(UUID ownerId) {
        return basketRepository.findBasketEntityByOwnerExternalId(ownerId)
                .orElseThrow(() -> new BasketNotFoundException("Basket with ownerId " + ownerId + " not found"));
    }

    private void removeBasketItemFromBasket(BasketEntity basket, UUID menuId) {
        BasketItemEntity basketItem = getBasketItem(basket.getItems(), menuId)
                .orElseThrow(() -> new MenuNotFoundException("Menu with menuId " + menuId + " not found"));

        basket.getItems().remove(basketItem);
    }

    private Optional<BasketItemEntity> getBasketItem(Set<BasketItemEntity> items, UUID menuId) {
        if (items == null) {
            throw new BasketEmptyException("Cannot remove item from empty basket");
        }

        return items.stream()
                .filter(item -> item.getMenu().getExternalId().equals(menuId))
                .findFirst();
    }
}
