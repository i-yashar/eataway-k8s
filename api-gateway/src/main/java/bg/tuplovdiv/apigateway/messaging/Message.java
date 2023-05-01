package bg.tuplovdiv.apigateway.messaging;

import java.util.UUID;

public abstract class Message {

    private UUID messageId;
    private Long createdAt;

    public UUID getMessageId() {
        return messageId;
    }

    public Message setMessageId(UUID messageId) {
        this.messageId = messageId;
        return this;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public Message setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
        return this;
    }
}
