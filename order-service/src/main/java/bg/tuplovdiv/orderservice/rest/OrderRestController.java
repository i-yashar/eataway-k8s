package bg.tuplovdiv.orderservice.rest;

import bg.tuplovdiv.orderservice.dto.OrderDTO;
import bg.tuplovdiv.orderservice.dto.OrderStatusInfoDTO;
import bg.tuplovdiv.orderservice.dto.page.PageDTO;
import bg.tuplovdiv.orderservice.service.OrderService;
import bg.tuplovdiv.orderservice.service.OrderStatusInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/orders/api/v1")
public class OrderRestController {
    private static final String ORDERS_PATH = "/orders";
    private static final String ORDER_PATH = ORDERS_PATH + "/{orderId}";
    private static final String CLIENT_ORDERS_PATH = "/users/{clientId}" + ORDERS_PATH;
    private static final String ACTIVE_DELIVERY_DRIVER_ORDERS_PATH = "/delivery/drivers/{driverId}" + ORDERS_PATH;
    private static final String ORDER_STATUS_INFO_PATH = ORDER_PATH + "/info";

    private final OrderService orderService;
    private final OrderStatusInfoService orderStatusInfoService;

    public OrderRestController(OrderService orderService, OrderStatusInfoService orderStatusInfoService) {
        this.orderService = orderService;
        this.orderStatusInfoService = orderStatusInfoService;
    }

    @GetMapping(ORDER_PATH)
    public ResponseEntity<OrderDTO> getOrder(@PathVariable("orderId") UUID orderId) {
        return ResponseEntity.ok(orderService.findOrderByOrderId(orderId));
    }

    @GetMapping(CLIENT_ORDERS_PATH)
    public ResponseEntity<PageDTO<OrderDTO>> getActiveUserOrders(@PathVariable String clientId,
                                                                 @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                                 @RequestParam(name = "size", required = false, defaultValue = "15") int size) {
        return ResponseEntity.ok(orderService.findActiveUserOrders(clientId, page, size));
    }

    @GetMapping(ACTIVE_DELIVERY_DRIVER_ORDERS_PATH)
    public ResponseEntity<Collection<OrderDTO>> getActiveDeliveryDriverOrders(@PathVariable String driverId) {
        return ResponseEntity.ok(orderService.getActiveDeliveryDriverOrders(driverId));
    }

    @GetMapping(ORDER_STATUS_INFO_PATH)
    public ResponseEntity<Collection<OrderStatusInfoDTO>> getOrderStatusInfoMessages(@PathVariable UUID orderId) {
        return ResponseEntity.ok(orderStatusInfoService.getOrderStatusInfoMessages(orderId));
    }
}
