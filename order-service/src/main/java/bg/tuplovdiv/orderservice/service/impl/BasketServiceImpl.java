package bg.tuplovdiv.orderservice.service.impl;

import bg.tuplovdiv.orderservice.dto.BasketDTO;
import bg.tuplovdiv.orderservice.dto.BasketItemDTO;
import bg.tuplovdiv.orderservice.exception.BasketNotFoundException;
import bg.tuplovdiv.orderservice.mapper.BasketMapper;
import bg.tuplovdiv.orderservice.model.entity.BasketEntity;
import bg.tuplovdiv.orderservice.model.entity.BasketItemEntity;
import bg.tuplovdiv.orderservice.repository.BasketRepository;
import bg.tuplovdiv.orderservice.service.BasketService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
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
        Set<BasketItemEntity> items = getBasketItems(basket.getItems());
        BasketItemEntity item = mapper.toBasketItemEntity(basketItem);

        items.add(item);
        basket = basketRepository.save(basket);

        return mapper.toBasketDTO(basket);
    }

    private BasketEntity getBasketEntityByOwnerId(UUID ownerId) {
        return basketRepository.findBasketEntityByOwnerExternalId(ownerId)
                .orElseThrow(() -> new BasketNotFoundException("Basket with ownerId " + ownerId + " not found"));
    }

    private Set<BasketItemEntity> getBasketItems(Set<BasketItemEntity> items) {
        if (items == null) {
            items = new HashSet<>();
        }

        return items;
    }

    @Override
    public BasketDTO getByBasketId(UUID basketId) {
        BasketEntity basketEntity = getBasketEntity(basketId);

        return mapper.toBasketDTO(basketEntity);
    }

    private BasketEntity getBasketEntity(UUID basketId) {
        return basketRepository.findBasketEntityByExternalId(basketId)
                .orElseThrow(() -> new BasketNotFoundException("Basket with basketId " + basketId + " not found"));
    }
}
