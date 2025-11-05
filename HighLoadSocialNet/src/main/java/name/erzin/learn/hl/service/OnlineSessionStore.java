package name.erzin.learn.hl.service;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Class contains all online users connected by WebSocket
 */
@Service
public class OnlineSessionStore {

    public static final String EXCHANGE_NAME = "feedExchange";

    public static class UserSession {
        WebSocketSession webSocketSession;
        Channel rabbitChannel;
        AMQP.Queue.DeclareOk rabbitQueue;
        AMQP.Exchange.DeclareOk rabbitExchange;
    }

    @Autowired
    RabbitTemplate rabbitTemplate;

    private final Map<String, UserSession> sessions = Collections.synchronizedMap(new HashMap<>());

    public void add (String user, WebSocketSession session) {
        UserSession userSession = new UserSession();
        userSession.webSocketSession = session;

        configureRabbitQueue (user, userSession);

        sessions.put(user, userSession);
    }

    public void remove (String user) {
        UserSession userSession = sessions.remove(user);
        if (userSession == null) {
            return;
        }
        cleanRabbitQueue(user, userSession);
    }

    @SneakyThrows
    private void configureRabbitQueue (String user, UserSession userSession) {
        ConnectionFactory connectionFactory = rabbitTemplate.getConnectionFactory();
        Connection connection = connectionFactory.createConnection();

        userSession.rabbitChannel = connection.createChannel(false);
        userSession.rabbitQueue = userSession.rabbitChannel.queueDeclare (user,false, false, true, null);
        userSession.rabbitExchange = userSession.rabbitChannel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        userSession.rabbitChannel.queueBind (user, EXCHANGE_NAME, user);
        userSession.rabbitChannel.basicConsume (user, new RabbitQueueConsumer(userSession));
    }

    @SneakyThrows
    private void cleanRabbitQueue (String user, UserSession userSession) {
        userSession.rabbitChannel.queueDelete(user);
        userSession.rabbitChannel.close();
        userSession.webSocketSession.close();
    }

    // TODO по идее надо реализовать сборку мусора, если вдруг при отключении сокеты мы не удалили сессию по какой-либо причине
    @SneakyThrows
    public void cleanup() {
        for (Map.Entry<String, UserSession> userSessionEntry : sessions.entrySet()) {
            if (! userSessionEntry.getValue().webSocketSession.isOpen()) {
                remove(userSessionEntry.getKey());
            }
        }
    }
}
