package bg.tuplovdiv.apigateway.service.impl;

import bg.tuplovdiv.apigateway.cache.BasketCache;
import bg.tuplovdiv.apigateway.connectivity.client.OrdersRestClient;
import bg.tuplovdiv.apigateway.dto.BasketDTO;
import bg.tuplovdiv.apigateway.dto.ItemDTO;
import bg.tuplovdiv.apigateway.service.BasketService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
class BasketServiceImpl implements BasketService {

    private final OrdersRestClient ordersClient;
    private final BasketCache basketCache;

    BasketServiceImpl(OrdersRestClient ordersClient, BasketCache basketCache) {
        this.ordersClient = ordersClient;
        this.basketCache = basketCache;
    }

    @Override
    public BasketDTO getUserBasket(String ownerId) {
        BasketDTO basket = ordersClient.getUserBasket(ownerId);
        calculateTotalCost(basket);

        basketCache.updateUserBasket(basket);

        return basket;
    }

    private void calculateTotalCost(BasketDTO basket) {
        Double totalCost = basket.getItems().stream()
                .map(item -> item.getMenu().getPrice() * item.getCount())
                .reduce((double) 0, Double::sum);
        basket.setTotalCost(totalCost);
    }

    @Override
    public BasketDTO addBasketItem(String ownerId, ItemDTO item) {
        return ordersClient.addBasketItem(ownerId, item);
    }

    @Override
    public void deleteBasketItem(String ownerId, UUID menuId) {
        ordersClient.deleteBasketItem(ownerId, menuId);
    }
}
