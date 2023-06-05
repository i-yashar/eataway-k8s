package bg.tuplovdiv.apigateway.cache;

import bg.tuplovdiv.apigateway.dto.BasketDTO;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class BasketCache {

    @Cacheable(value = "basket", key = "#userId")
    public BasketDTO getUserBasket(String userId) {
        return new BasketDTO();
    }

    @CachePut(value = "basket", key = "#basket.ownerId")
    public BasketDTO updateUserBasket(BasketDTO basket) {
        return basket;
    }
}
