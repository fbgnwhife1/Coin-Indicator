package upbit_candle.candle.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import upbit_candle.candle.Response.Message;
import upbit_candle.candle.Response.StatusEnum;
import upbit_candle.candle.Service.AnalyzerService;
import upbit_candle.candle.Service.OrderBookService;
import upbit_candle.candle.Service.forTest.ForTest;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
public class AnalyzeController {

    private final AnalyzerService analyzerService;

    @GetMapping("v1/analyze/rsi/{marketId}")
    public ResponseEntity<Message> getRSI(@PathVariable String marketId, Integer period){
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        Message message = new Message();

        try {
            if(period == null) period = 14;
            message.setData(analyzerService.RSI(marketId, period));
        }catch (Exception e){
            message.setMessage(e.getMessage());
            message.setStatus(StatusEnum.BAD_REQUEST);
            return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
        }

        message.setMessage("OK");
        message.setStatus(StatusEnum.OK);
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    @GetMapping("v1/analyze/bsi/{marketId}")
    public ResponseEntity<Message> getBSI(@PathVariable String marketId){
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        Message message = new Message();

        try {
            message.setData(analyzerService.BSI(marketId));
        }catch (Exception e){
            message.setMessage(e.getMessage());
            message.setStatus(StatusEnum.BAD_REQUEST);
            return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
        }

        message.setMessage("OK");
        message.setStatus(StatusEnum.OK);
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }
}
