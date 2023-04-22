package bg.tuplovdiv.apigateway.service.impl;

import bg.tuplovdiv.apigateway.connectivity.client.OrdersRestClient;
import bg.tuplovdiv.apigateway.dto.BasketDTO;
import bg.tuplovdiv.apigateway.dto.BasketItemDTO;
import bg.tuplovdiv.apigateway.service.BasketService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BasketServiceImpl implements BasketService {
    private final OrdersRestClient client;

    public BasketServiceImpl(OrdersRestClient client) {
        this.client = client;
    }

    @Override
    public BasketDTO getUserBasket(String ownerId) {
        return client.getUserBasket(ownerId);
    }

    @Override
    public BasketDTO addBasketItem(String ownerId, BasketItemDTO basketItem) {
        return client.addBasketItem(ownerId, basketItem);
    }

    @Override
    public void deleteBasketItem(String ownerId, UUID menuId) {
        client.deleteBasketItem(ownerId, menuId);
    }
}
