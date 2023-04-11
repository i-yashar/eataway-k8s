package bg.tuplovdiv.orderservice.service.impl;

import bg.tuplovdiv.orderservice.dto.BasketDTO;
import bg.tuplovdiv.orderservice.exception.BasketNotFoundException;
import bg.tuplovdiv.orderservice.mapper.BasketMapper;
import bg.tuplovdiv.orderservice.model.entity.BasketEntity;
import bg.tuplovdiv.orderservice.repository.BasketRepository;
import bg.tuplovdiv.orderservice.service.BasketService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;
    private final BasketMapper mapper;

    public BasketServiceImpl(BasketRepository basketRepository, BasketMapper mapper) {
        this.basketRepository = basketRepository;
        this.mapper = mapper;
    }

    @Override
    public BasketDTO getByBasketId(UUID basketId) {
        BasketEntity basketEntity = getBasketEntity(basketId);

        return mapper.toBasketDTO(basketEntity);
    }

    private BasketEntity getBasketEntity(UUID basketId) {
        return basketRepository.findBasketEntityByExternalId(basketId)
                .orElseThrow(() -> new BasketNotFoundException("Basket with basketId " + basketId + " not found"));
    }
}
