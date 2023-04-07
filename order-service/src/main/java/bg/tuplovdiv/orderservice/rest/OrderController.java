package bg.tuplovdiv.orderservice.rest;

import bg.tuplovdiv.orderservice.dto.OrderDTO;
import bg.tuplovdiv.orderservice.dto.page.PageDTO;
import bg.tuplovdiv.orderservice.service.OrderService;
import bg.tuplovdiv.orderservice.validation.OrderValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/orders/api/v1")
public class OrderController {
    private static final String ORDER_ID = "/{orderId}";
    private static final String ORDERS_PATH = "/orders";
    private static final String CLIENT_ID = "/{userId}";
    private static final String CLIENT_ORDERS_PATH = "/users" + CLIENT_ID + ORDERS_PATH;

    private final OrderService orderService;
    private final OrderValidator orderValidator;

    public OrderController(OrderService orderService, OrderValidator orderValidator) {
        this.orderService = orderService;
        this.orderValidator = orderValidator;
    }

    @GetMapping(ORDERS_PATH + ORDER_ID)
    public ResponseEntity<OrderDTO> getOrder(@PathVariable("orderId") UUID orderId) {
        return ResponseEntity.ok(orderService.findOrderByOrderId(orderId));
    }

    @GetMapping(CLIENT_ORDERS_PATH)
    public ResponseEntity<PageDTO<OrderDTO>> getUserOrders(@PathVariable("userId") UUID userId,
                                                           @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                           @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok(orderService.findAllUserOrders(userId, page, size));
    }

    @PostMapping(ORDERS_PATH)
    //@PreAuthorize("@orderValidator.isValid(#orderDTO)")
    public ResponseEntity<Void> createOrder(@RequestBody OrderDTO orderDTO) {
        UUID orderId = orderService.createOrder(orderDTO);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .buildAndExpand("/{orderId}", orderId)
                .toUri();

        return ResponseEntity.created(uri).build();
    }
}
