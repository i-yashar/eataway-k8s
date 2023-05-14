package bg.tuplovdiv.apigateway.order;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.Map;

public class OrderStatusEmitters {

    private static final Map<String, SseEmitter> EMITTERS = new HashMap<>();

    public static SseEmitter get(String clientId) {
        EMITTERS.putIfAbsent(clientId, new SseEmitter(-1L));
        return EMITTERS.get(clientId);
    }

    public static void remove(String clientId) {
        EMITTERS.remove(clientId);
    }
}
