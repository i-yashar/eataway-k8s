package bg.tuplovdiv.apigateway.service.impl;

import bg.tuplovdiv.apigateway.connectivity.client.BasketRestClient;
import bg.tuplovdiv.apigateway.dto.BasketDTO;
import bg.tuplovdiv.apigateway.dto.ItemDTO;
import bg.tuplovdiv.apigateway.service.BasketService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
class BasketServiceImpl implements BasketService {

    private final BasketRestClient client;

    BasketServiceImpl(BasketRestClient client) {
        this.client = client;
    }

    @Override
    public BasketDTO getUserBasket(String ownerId) {
        BasketDTO basket = client.getUserBasket(ownerId);
        calculateTotalCost(basket);

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
        return client.addBasketItem(ownerId, item);
    }

    @Override
    public void deleteBasketItem(String ownerId, UUID menuId) {
        client.deleteBasketItem(ownerId, menuId);
    }
}
