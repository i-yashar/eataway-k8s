package bg.tuplovdiv.orderservice.validation;

import bg.tuplovdiv.orderservice.dto.OrderDTO;
import bg.tuplovdiv.orderservice.model.entity.BasketEntity;
import bg.tuplovdiv.orderservice.repository.BasketRepository;
import org.springframework.stereotype.Component;

@Component
public record OrderValidator(BasketRepository basketRepository) {

    public boolean isValid(OrderDTO orderDTO) {
//        validateBasketCost(orderDTO);

        return true;
    }

    private void validateBasketCost(OrderDTO orderDTO) {
        BasketEntity basket = getBasketByBasketId(orderDTO);

        boolean totalCostIsValid = basket.getItems()
                .stream()
                .map(item -> item.getMenu().getPrice() * item.getCount())
                .reduce((double) 0, Double::sum)
                .equals(orderDTO.getTotalCost());

        if (!totalCostIsValid) {
            //todo: add custom validation
            throw new IllegalArgumentException();
        }
    }

    private BasketEntity getBasketByBasketId(OrderDTO orderDTO) {
        //todo: add custom validation
        return basketRepository.findBasketEntityByExternalId(orderDTO.getBasketId())
                .orElseThrow(IllegalArgumentException::new);
    }
}
