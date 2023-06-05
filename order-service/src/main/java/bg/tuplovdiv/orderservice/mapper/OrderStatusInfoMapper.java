package bg.tuplovdiv.orderservice.mapper;

import bg.tuplovdiv.orderservice.dto.OrderStatusInfoDTO;
import bg.tuplovdiv.orderservice.model.entity.OrderStatusInfoEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderStatusInfoMapper {

    public OrderStatusInfoDTO toDTO(OrderStatusInfoEntity entity) {
        return new OrderStatusInfoDTO()
                .setOrderId(entity.getOrderId())
                .setTime(entity.getTime())
                .setInfoMessage(entity.getInfoMessage().name());
    }
}
