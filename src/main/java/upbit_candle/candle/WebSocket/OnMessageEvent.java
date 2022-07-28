package upbit_candle.candle.WebSocket;

import lombok.Data;

@Data
public class OnMessageEvent {
    private String message;

    public OnMessageEvent(String message) {
        super();
        this.message = message;
    }
}
