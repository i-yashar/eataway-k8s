package bg.tuplovdiv.orderservice.service;

import bg.tuplovdiv.orderservice.dto.BasketDTO;
import bg.tuplovdiv.orderservice.dto.BasketItemDTO;

import java.util.UUID;

public interface BasketService {
    BasketDTO addBasketItem(UUID ownerId, BasketItemDTO basketItem);
    BasketDTO getBasketByOwnerId(UUID ownerId);
    void deleteBasketItem(UUID ownerId, UUID menuId);
}
