package name.erzin.learn.hl.configuration;

import name.erzin.learn.hl.api.PostWebsocketImplementation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebsocketConfig implements WebSocketConfigurer {

    @Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(postWebsocketHandler(), "/post/feed/posted");
	}

	@Bean
	public WebSocketHandler postWebsocketHandler() {
		return new PostWebsocketImplementation();
	}
}
