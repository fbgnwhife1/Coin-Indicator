package upbit_candle.candle.trade_search;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class TickService {
    OkHttpClient client = new OkHttpClient();

    Request request = new Request.Builder()
            .url("https://api.upbit.com/v1/trades/ticks?count=1")
            .get()
            .addHeader("Accept", "application/json")
            .build();

    Response response = client.newCall(request).execute();

    public TickService() throws IOException {
    }
}
