package name.erzin.learn.hl.service;

import com.rabbitmq.client.*;
import lombok.extern.java.Log;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;

@Log
public class RabbitQueueConsumer extends DefaultConsumer {
    private final OnlineSessionStore.UserSession userSession;

    public RabbitQueueConsumer(OnlineSessionStore.UserSession userSession) {
        super(userSession.rabbitChannel);
        this.userSession = userSession;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        String postJson = new String(body);
        log.info("Send message to websocket: " + postJson);
        TextMessage response = new TextMessage(postJson);
        userSession.webSocketSession.sendMessage(response);
    }
}
