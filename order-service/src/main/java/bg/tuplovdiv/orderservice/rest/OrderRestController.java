package bg.tuplovdiv.orderservice.rest;

import bg.tuplovdiv.orderservice.dto.CreateOrderRequest;
import bg.tuplovdiv.orderservice.dto.OrderDTO;
import bg.tuplovdiv.orderservice.dto.page.PageDTO;
import bg.tuplovdiv.orderservice.service.OrderService;
import bg.tuplovdiv.orderservice.validation.order.DeliveryValidator;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/orders/api/v1")
public class OrderRestController {
    private static final String ORDER_ID = "/{orderId}";
    private static final String ORDERS_PATH = "/orders";
    private static final String CLIENT_ORDERS_PATH = "/users/{clientId}" +  ORDERS_PATH;

    private static final String AUTH_USER_HEADER = "AUTH_USER";

    private final OrderService orderService;
    private final DeliveryValidator deliveryValidator;

    public OrderRestController(OrderService orderService, DeliveryValidator deliveryValidator) {
        this.orderService = orderService;
        this.deliveryValidator = deliveryValidator;
    }

    @GetMapping(ORDERS_PATH + ORDER_ID)
    public ResponseEntity<OrderDTO> getOrder(@PathVariable("orderId") UUID orderId) {
        return ResponseEntity.ok(orderService.findOrderByOrderId(orderId));
    }

    @GetMapping(CLIENT_ORDERS_PATH)
    public ResponseEntity<PageDTO<OrderDTO>> getUserOrders(@PathVariable String clientId,
                                                               @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                                               @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok(orderService.findAllUserOrders(clientId, page, size));
    }

    @PostMapping(ORDERS_PATH)
    public ResponseEntity<Void> createOrder(@RequestBody @Valid CreateOrderRequest orderRequest) {
        UUID orderId = orderService.createOrder(orderRequest);

        URI uri = buildOrderCreatedPath(orderId);

        return ResponseEntity.created(uri).build();
    }

    private URI buildOrderCreatedPath(UUID orderId) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{orderId}")
                .buildAndExpand(orderId)
                .toUri();
    }

    @PutMapping(ORDERS_PATH + ORDER_ID)
    @PreAuthorize("@deliveryValidator.isValid(#order)")
    public ResponseEntity<OrderDTO> updateOrder(@RequestBody @Valid OrderDTO order) {
        return ResponseEntity.ok(orderService.updateOrder(order));
    }
}
