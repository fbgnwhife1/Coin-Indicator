package upbit_candle.candle.Service;

import okhttp3.*;

import java.io.IOException;

public class MarketSearchService {

    public String search(){
        try{
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://api.upbit.com/v1/market/all?isDetails=false")
                    .get()
                    .addHeader("Accept", "application/json")
                    .build();

            Response response = client.newCall(request).execute();

            if(response.isSuccessful()){
                return response.body().string();
            }

            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
