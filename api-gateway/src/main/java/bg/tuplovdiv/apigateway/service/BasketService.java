package bg.tuplovdiv.apigateway.service;

import bg.tuplovdiv.apigateway.dto.BasketDTO;

import java.util.UUID;

public interface BasketService {
    BasketDTO getUserBasket(String ownerId);
    BasketDTO addBasketItem(String ownerId, UUID menuId);
    void deleteBasketItem(String ownerId, UUID menuId);
}
