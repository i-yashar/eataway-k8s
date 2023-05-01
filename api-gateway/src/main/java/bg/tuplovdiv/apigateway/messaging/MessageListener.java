package bg.tuplovdiv.apigateway.messaging;

import org.springframework.stereotype.Component;

@Component
public interface MessageListener<T extends Message> {
    T accept(T message);
}
