package bg.tuplovdiv.apigateway.service.impl;

import bg.tuplovdiv.apigateway.connectivity.client.OrdersRestClient;
import bg.tuplovdiv.apigateway.connectivity.client.RestaurantsRestClient;
import bg.tuplovdiv.apigateway.dto.BasketDTO;
import bg.tuplovdiv.apigateway.dto.BasketItemDTO;
import bg.tuplovdiv.apigateway.dto.DetailedBasketDTO;
import bg.tuplovdiv.apigateway.dto.DetailedBasketItemDTO;
import bg.tuplovdiv.apigateway.service.BasketService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.UUID;

@Service
public class BasketServiceImpl implements BasketService {
    private final OrdersRestClient ordersClient;
    private final RestaurantsRestClient restaurantsClient;

    public BasketServiceImpl(OrdersRestClient ordersClient, RestaurantsRestClient restaurantsClient) {
        this.ordersClient = ordersClient;
        this.restaurantsClient = restaurantsClient;
    }

    @Override
    public DetailedBasketDTO getUserBasket(String ownerId) {
        BasketDTO basket = ordersClient.getUserBasket(ownerId);
        return getDetailedBasket(basket);
    }

    private DetailedBasketDTO getDetailedBasket(BasketDTO basket) {
        DetailedBasketDTO detailedBasket = new DetailedBasketDTO()
                .setItems(new HashSet<>())
                .setTotalCost(0.0);

        basket.getItems().forEach(item -> {
            DetailedBasketItemDTO detailedItem = new DetailedBasketItemDTO()
                    .setMenu(restaurantsClient.getMenuByMenuId(item.getMenuId()))
                    .setCount(item.getCount());
            detailedBasket.getItems().add(detailedItem);
            detailedBasket.setTotalCost(detailedBasket.getTotalCost() + detailedItem.getMenu().getPrice());
        });

        return detailedBasket;
    }

    @Override
    public BasketDTO addBasketItem(String ownerId, BasketItemDTO basketItem) {
        return ordersClient.addBasketItem(ownerId, basketItem);
    }

    @Override
    public void deleteBasketItem(String ownerId, UUID menuId) {
        ordersClient.deleteBasketItem(ownerId, menuId);
    }
}
