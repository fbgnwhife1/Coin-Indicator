package upbit_candle.candle.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import upbit_candle.candle.Response.Message;
import upbit_candle.candle.Response.StatusEnum;
import upbit_candle.candle.WebSocket.RunSocketService;
import upbit_candle.candle.Service.forTest.ForTest;
import upbit_candle.candle.WebSocket.OnExecuteCoin;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class GetWsTradeController {

    private final RunSocketService runSocket;

    @GetMapping("v1/recent-trade-stock/{marketId}")
    public ResponseEntity<Message> loadTradeV1(@PathVariable String marketId) {
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        Message message = new Message();

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    @GetMapping("v2/recent-trade-stock")
    public ResponseEntity<Message> loadTradeV2(@RequestParam(defaultValue = "1_000_000") String pivot) {
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        Message message = new Message();

        try {
            runSocket.runSocket(ForTest.initTestData(), Long.parseLong(pivot));
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
    public ResponseEntity<Message> loadTradeV3(@RequestParam List<String> list,
                                               @RequestParam(defaultValue = "1000000") String pivot) {
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        Message message = new Message();

        try {
            //최대 15개
            if(list.size() > 15) throw new Exception();
            
            //중복된 코인 제거
            list.removeIf(target -> OnExecuteCoin.set.contains(target));
            if(list.isEmpty()) throw new Exception();
            OnExecuteCoin.set.addAll(list);      //싱글톤으로 실행중인 코인 확인
            
            runSocket.runSocket(list, Long.parseLong(pivot));
        }catch (Exception e){
            message.setMessage("잘못된 요청");
            message.setStatus(StatusEnum.BAD_REQUEST);
            return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
        }

        message.setMessage("OK");
        message.setStatus(StatusEnum.OK);
        message.setData(list);
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }



}
