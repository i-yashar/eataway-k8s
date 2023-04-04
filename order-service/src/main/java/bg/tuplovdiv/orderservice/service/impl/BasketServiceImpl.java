package bg.tuplovdiv.orderservice.service.impl;

import bg.tuplovdiv.orderservice.dto.BasketDTO;
import bg.tuplovdiv.orderservice.repository.BasketRepository;
import bg.tuplovdiv.orderservice.service.BasketService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BasketServiceImpl implements BasketService {
    private final BasketRepository basketRepository;

    public BasketServiceImpl(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    @Override
    public BasketDTO getByBasketId(UUID basketId) {
        return null;
        //add mapper
//        return basketRepository.findBasketEntityByExternalId(basketId)
//                .orElseThrow(() -> new BasketNotFoundException("Basket with basketId " + basketId + " not found"));
    }
}
