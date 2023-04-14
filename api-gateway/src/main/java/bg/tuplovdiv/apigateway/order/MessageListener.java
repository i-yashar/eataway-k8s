package bg.tuplovdiv.apigateway.order;

import org.springframework.stereotype.Component;

@Component
public interface MessageListener<T extends Message> {
    T accept(T message);
}
