package bg.tuplovdiv.orderservice.service;

import bg.tuplovdiv.orderservice.dto.BasketDTO;
import bg.tuplovdiv.orderservice.dto.BasketItemDTO;

import java.util.UUID;

public interface BasketService {
    BasketDTO addBasketItem(String ownerId, BasketItemDTO basketItem);
    BasketDTO getBasketByOwnerId(String ownerId);
    void deleteBasketItem(String ownerId, UUID menuId);
}
