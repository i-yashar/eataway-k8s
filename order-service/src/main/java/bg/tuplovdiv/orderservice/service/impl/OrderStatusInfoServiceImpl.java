package bg.tuplovdiv.orderservice.service.impl;

import bg.tuplovdiv.orderservice.dto.OrderDTO;
import bg.tuplovdiv.orderservice.dto.OrderStatusInfoDTO;
import bg.tuplovdiv.orderservice.mapper.OrderStatusInfoMapper;
import bg.tuplovdiv.orderservice.model.entity.OrderStatusInfoEntity;
import bg.tuplovdiv.orderservice.model.enums.OrderStatusInfo;
import bg.tuplovdiv.orderservice.repository.OrderStatusInfoRepository;
import bg.tuplovdiv.orderservice.service.OrderStatusInfoService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.UUID;

@Service
class OrderStatusInfoServiceImpl implements OrderStatusInfoService {

    private final OrderStatusInfoRepository orderStatusInfoRepository;
    private final OrderStatusInfoMapper mapper;

    OrderStatusInfoServiceImpl(OrderStatusInfoRepository orderStatusInfoRepository, OrderStatusInfoMapper mapper) {
        this.orderStatusInfoRepository = orderStatusInfoRepository;
        this.mapper = mapper;
    }

    @Override
    public void saveOrderStatusInfo(OrderDTO order) {
        OrderStatusInfoEntity orderStatusInfoEntity = new OrderStatusInfoEntity()
                .setOrderId(order.getOrderId())
                .setTime(String.valueOf(Instant.now().getEpochSecond()))
                .setInfoMessage(OrderStatusInfo.valueOf(order.getStatus()));

        orderStatusInfoRepository.save(orderStatusInfoEntity);
    }

    @Override
    public Collection<OrderStatusInfoDTO> getOrderStatusInfoMessages(UUID orderId) {
        return orderStatusInfoRepository.findAllByOrderIdOrderByTimeAsc(orderId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }
}
