package rabbitmqbitsocketconsumer.consumer.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import rabbitmqbitsocketconsumer.consumer.WebSocket.ConclusionWebSocketHandler;
import rabbitmqbitsocketconsumer.consumer.WebSocket.OrderBookWebSocketHandler;

@RequiredArgsConstructor
@Configuration
@EnableWebSocket
public class BSIWebSocketConfig implements WebSocketConfigurer {
    private final OrderBookWebSocketHandler orderBookWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(orderBookWebSocketHandler, "socket-service/ws/v1/bsi").setAllowedOrigins("*");
    }
}
