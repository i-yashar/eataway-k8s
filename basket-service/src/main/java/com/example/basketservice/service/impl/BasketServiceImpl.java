package com.example.basketservice.service.impl;

import com.example.basketservice.dto.BasketDTO;
import com.example.basketservice.dto.ItemDTO;
import com.example.basketservice.exception.MenuNotFoundException;
import com.example.basketservice.mapper.BasketMapper;
import com.example.basketservice.mapper.ItemMapper;
import com.example.basketservice.model.entity.BasketEntity;
import com.example.basketservice.model.entity.ItemEntity;
import com.example.basketservice.repository.BasketRepository;
import com.example.basketservice.service.BasketService;
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
    private final ItemMapper itemMapper;

    public BasketServiceImpl(BasketRepository basketRepository, BasketMapper mapper, ItemMapper itemMapper) {
        this.basketRepository = basketRepository;
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
        return basketRepository.findBasketEntityByOwner(userId)
                .orElseGet(() -> createUserBasket(userId));
    }

    private BasketEntity createUserBasket(String userId) {
        BasketEntity basket = new BasketEntity()
                .setExternalId(UUID.randomUUID())
                .setOwner(userId)
                .setItems(new HashSet<>());

        return basketRepository.save(basket);
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
