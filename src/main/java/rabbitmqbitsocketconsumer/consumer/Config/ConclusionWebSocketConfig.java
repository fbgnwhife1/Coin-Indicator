package rabbitmqbitsocketconsumer.consumer.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import rabbitmqbitsocketconsumer.consumer.WebSocket.ConclusionWebSocketHandler;

@RequiredArgsConstructor
@Configuration
@EnableWebSocket
public class ConclusionWebSocketConfig implements WebSocketConfigurer {
    private final ConclusionWebSocketHandler conclusionWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(conclusionWebSocketHandler, "socket-service/ws/v1/conclusion").setAllowedOrigins("*");
    }
}
