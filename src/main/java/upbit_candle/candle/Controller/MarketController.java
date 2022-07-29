package upbit_candle.candle.Controller;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import upbit_candle.candle.Entity.MarketEntity;
import upbit_candle.candle.Repository.MarketRepository;
import upbit_candle.candle.Response.Message;
import upbit_candle.candle.Response.StatusEnum;
import upbit_candle.candle.trade_search.MarketSearchSearvice;

import java.nio.charset.Charset;
import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
public class MarketController {

    private final MarketRepository marketRepository;

    @PostMapping("v1/market")
    public void loadMarketV1(){

        MarketSearchSearvice marketSearchSearvice = new MarketSearchSearvice();

        try {
            Gson gson = new Gson();
            String result = "";
            result = marketSearchSearvice.search();

            if(result == null) return;

            JSONParser jsonParser = new JSONParser();
            JSONArray results = (JSONArray) jsonParser.parse(result.toString());
//            JSONArray results = (JSONArray) jsonObject.get("market");

            for(int i = 0; i < results.size(); i++){
                JSONObject temp = (JSONObject) results.get(i);
                MarketEntity market = gson.fromJson(temp.toJSONString(), MarketEntity.class);
                marketRepository.save(market);
            }

        }catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    @PostMapping("/v2/market")
    public ResponseEntity<Message> loadMarketV2(){
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        Message message = new Message();
        MarketSearchSearvice marketSearchSearvice = new MarketSearchSearvice();

        try {
            Gson gson = new Gson();
            String result = "";
            result = marketSearchSearvice.search();

            if(result == null) throw new RuntimeException();

            JSONParser jsonParser = new JSONParser();
            JSONArray results = (JSONArray) jsonParser.parse(result.toString());

            ArrayList<MarketEntity> list = new ArrayList<>();

            for(int i = 0; i < results.size(); i++){
                JSONObject temp = (JSONObject) results.get(i);
                MarketEntity market = gson.fromJson(temp.toJSONString(), MarketEntity.class);
                list.add(market);
            }

            marketRepository.saveAll(list);
            message.setData(list);
            message.setMessage("market 생성");
            message.setStatus(StatusEnum.OK);
            return new ResponseEntity<>(message, headers, HttpStatus.OK);

        }catch (Exception e) {

            message.setData(null);
            message.setMessage("Error");
            message.setStatus(StatusEnum.BAD_REQUEST);

            return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
        }
    }
}
