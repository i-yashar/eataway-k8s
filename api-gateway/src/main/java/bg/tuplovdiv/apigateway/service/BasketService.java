package bg.tuplovdiv.apigateway.service;

import bg.tuplovdiv.apigateway.dto.BasketDTO;
import bg.tuplovdiv.apigateway.dto.BasketItemDTO;

import java.util.UUID;

public interface BasketService {
    BasketDTO getUserBasket(String ownerId);
    BasketDTO addBasketItem(String ownerId, BasketItemDTO basketItem);
    void deleteBasketItem(String ownerId, UUID menuId);
}
