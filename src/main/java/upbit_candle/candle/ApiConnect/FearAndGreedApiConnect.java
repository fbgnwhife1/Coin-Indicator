package upbit_candle.candle.ApiConnect;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import upbit_candle.candle.Analyze.FearAndGreed.FearAndGreed;
import upbit_candle.candle.Entity.Result.OrderBookResult;

public class FearAndGreedApiConnect {

    public FearAndGreed getFGIndex(){
        try{
            OkHttpClient client = new OkHttpClient();
            Gson gson = new Gson();

            Request request = new Request.Builder()
                    .url("https://api.alternative.me/fng/")
                    .get()
                    .addHeader("Accept", "application/json")
                    .build();

            Response response = client.newCall(request).execute();

            if(response.isSuccessful()){
                String result = response.body().string();
                JSONParser jsonParser = new JSONParser();
                JSONObject temp = (JSONObject) jsonParser.parse(result);

                FearAndGreed fearAndGreed = gson.fromJson(temp.toJSONString(), FearAndGreed.class);
                return fearAndGreed;
            }

            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
