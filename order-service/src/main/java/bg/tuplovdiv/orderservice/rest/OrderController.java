package bg.tuplovdiv.orderservice.rest;

import bg.tuplovdiv.orderservice.dto.OrderDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders/api/v1")
public class OrderController {
    private static final String ORDER_ID = "/{orderId}";
    private static final String ORDERS_PATH = "/orders";
    private static final String USER_ID = "/{userId}";
    private static final String USER_ORDERS_PATH = "/users" + USER_ID + ORDERS_PATH;

    @GetMapping(ORDERS_PATH + ORDER_ID)
    public ResponseEntity<OrderDTO> getOrder(@PathVariable("orderId") UUID orderId) {
        return ResponseEntity.ok(new OrderDTO());
    }

    @GetMapping(USER_ORDERS_PATH)
    public ResponseEntity<List<OrderDTO>> getUserOrders(@PathVariable("userId") UUID userId) {
        return ResponseEntity.ok(new ArrayList<>());
    }

    @PostMapping(ORDERS_PATH)
    public ResponseEntity<Void> createNewOrder(@RequestBody OrderDTO orderDTO) {
        UUID orderId = UUID.randomUUID();

        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .buildAndExpand("/{orderId}", orderId)
                .toUri();

        return ResponseEntity.created(uri).build();
    }
}
