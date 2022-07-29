package upbit_candle.candle.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import upbit_candle.candle.Entity.MarketEntity;
import upbit_candle.candle.Repository.MarketRepository;
import upbit_candle.candle.Response.Message;
import upbit_candle.candle.Response.StatusEnum;
import upbit_candle.candle.Service.ConclusionService;
import upbit_candle.candle.Service.RunSocketService;
import upbit_candle.candle.Service.forTest.ForTest;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TradeController {


    private final RunSocketService runSocket;
    private final MarketRepository marketRepository;

    @GetMapping("v1/recent-trade-stock/{marketId}")
    public ResponseEntity<Message> loadTradeV1(@PathVariable String marketId) {
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        Message message = new Message();

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    @GetMapping("v2/recent-trade-stock")
    public ResponseEntity<Message> loadTradeV2() {
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        Message message = new Message();

        try {
            runSocket.runSocket(ForTest.initTestData());
        }catch (Exception e){
            message.setMessage("잘못된 요청");
            message.setStatus(StatusEnum.BAD_REQUEST);
            return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
        }

        message.setMessage("OK");
        message.setStatus(StatusEnum.OK);
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    @GetMapping("v3/recent-trade-stock")
    public ResponseEntity<Message> loadTradeV3(@RequestParam List<String> list) {
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        Message message = new Message();

        try {
            if(list.size() > 15) throw new IllegalArgumentException();
            runSocket.runSocket(list);
        }catch (Exception e){
            message.setMessage("잘못된 요청");
            message.setStatus(StatusEnum.BAD_REQUEST);
            return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
        }

        message.setMessage("OK");
        message.setStatus(StatusEnum.OK);
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }
}
