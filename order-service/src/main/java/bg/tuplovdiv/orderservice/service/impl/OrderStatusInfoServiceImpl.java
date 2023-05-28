package bg.tuplovdiv.orderservice.service.impl;

import bg.tuplovdiv.orderservice.dto.OrderDTO;
import bg.tuplovdiv.orderservice.dto.OrderStatusInfoDTO;
import bg.tuplovdiv.orderservice.mapper.OrderStatusInfoMapper;
import bg.tuplovdiv.orderservice.model.entity.OrderStatusInfoEntity;
import bg.tuplovdiv.orderservice.repository.OrderStatusInfoRepository;
import bg.tuplovdiv.orderservice.service.OrderStatusInfoService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.UUID;

@Service
class OrderStatusInfoServiceImpl implements OrderStatusInfoService {

    private static final String ACTIVE_STATUS_MESSAGE = "Your order was taken by one of our employees";
    private static final String ABOUT_TO_BE_DELIVERED_STATUS_MESSAGE = "Your order will be delivered in a few minutes";
    private static final String DELIVERED_STATUS_MESSAGE = "Your order was delivered. Enjoy your food!";

    private final OrderStatusInfoRepository orderStatusInfoRepository;
    private final OrderStatusInfoMapper mapper;

    OrderStatusInfoServiceImpl(OrderStatusInfoRepository orderStatusInfoRepository, OrderStatusInfoMapper mapper) {
        this.orderStatusInfoRepository = orderStatusInfoRepository;
        this.mapper = mapper;
    }

    @Override
    public void saveOrderStatusInfo(OrderDTO order) {
        OrderStatusInfoEntity orderStatusInfoEntity = new OrderStatusInfoEntity()
                .setExternalId(UUID.randomUUID())
                .setOrderId(order.getOrderId())
                .setTime(Instant.now().toString())
                .setInfoMessage(getInfoMessageByStatus(order.getStatus()));

        orderStatusInfoRepository.save(orderStatusInfoEntity);
    }

    private String getInfoMessageByStatus(String status) {
        return switch (status) {
            case "ACTIVE" -> ACTIVE_STATUS_MESSAGE;
            case "ABOUT_TO_BE_DELIVERED" -> ABOUT_TO_BE_DELIVERED_STATUS_MESSAGE;
            case "DELIVERED" -> DELIVERED_STATUS_MESSAGE;
            default -> "";
        };
    }

    @Override
    public Collection<OrderStatusInfoDTO> getOrderStatusInfoMessages(UUID orderId) {
        return orderStatusInfoRepository.findAllByOrderIdOrderByTimeAsc(orderId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }
}
