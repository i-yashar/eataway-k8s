package bg.tuplovdiv.orderservice.service;

import bg.tuplovdiv.orderservice.dto.BasketDTO;
import bg.tuplovdiv.orderservice.dto.ItemDTO;

import java.util.UUID;

public interface BasketService {
    BasketDTO addBasketItem(String ownerId, ItemDTO basketItem);
    BasketDTO getBasketByOwnerId(String ownerId);
    void deleteBasketItem(String ownerId, UUID menuId);
}
