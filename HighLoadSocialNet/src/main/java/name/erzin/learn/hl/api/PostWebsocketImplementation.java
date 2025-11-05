package name.erzin.learn.hl.api;

import lombok.extern.java.Log;
import name.erzin.learn.hl.service.OnlineSessionStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Log
public class PostWebsocketImplementation extends TextWebSocketHandler {
    @Autowired
    OnlineSessionStore sessions;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String user = session.getPrincipal().getName();
        log.info("User connected: " + user);
        sessions.add(user, session);
        super.afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String user = session.getPrincipal().getName();
        log.info("User disconnected: " + user);
        sessions.remove(user);
        super.afterConnectionClosed(session, status);
    }

    @Override
    /*
     * Just experiment, really we not need handle incoming messages, we will only write messages to feed
     */
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String user = session.getPrincipal().getName();
        log.info("Message from user: " + user + ": " + message);
        TextMessage response = new TextMessage("TEST RESPONSE");
        session.sendMessage(response);
        super.handleTextMessage(session, message);
    }
}
