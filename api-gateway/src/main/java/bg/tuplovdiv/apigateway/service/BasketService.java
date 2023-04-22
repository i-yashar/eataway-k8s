package bg.tuplovdiv.apigateway.service;

import bg.tuplovdiv.apigateway.dto.BasketDTO;
import bg.tuplovdiv.apigateway.dto.BasketItemDTO;

import java.util.UUID;

public interface BasketService {
    BasketDTO getUserBasket(UUID ownerId);
    BasketDTO addBasketItem(UUID ownerId, BasketItemDTO basketItem);
    void deleteBasketItem(UUID ownerId, UUID menuId);
}
