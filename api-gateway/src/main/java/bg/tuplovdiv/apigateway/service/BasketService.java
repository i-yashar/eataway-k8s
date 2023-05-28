package bg.tuplovdiv.apigateway.service;

import bg.tuplovdiv.apigateway.dto.BasketDTO;
import bg.tuplovdiv.apigateway.dto.ItemDTO;

import java.util.UUID;

public interface BasketService {
    BasketDTO getUserBasket(String ownerId);
    BasketDTO addBasketItem(String ownerId, ItemDTO item);
    void deleteBasketItem(String ownerId, UUID menuId);
}
