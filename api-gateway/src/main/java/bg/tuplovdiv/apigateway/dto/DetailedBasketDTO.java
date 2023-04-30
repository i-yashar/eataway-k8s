package bg.tuplovdiv.apigateway.dto;

import java.util.Set;

public class DetailedBasketDTO {

    private Set<DetailedBasketItemDTO> items;
    private Double totalCost;

    public Set<DetailedBasketItemDTO> getItems() {
        return items;
    }

    public DetailedBasketDTO setItems(Set<DetailedBasketItemDTO> items) {
        this.items = items;
        return this;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public DetailedBasketDTO setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
        return this;
    }
}
