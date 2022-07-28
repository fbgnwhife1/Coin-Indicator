package upbit_candle.candle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CandleApplication {

    public static void main(String[] args) throws Exception{
//        ApiKey key = ApiKey.getInstance();
//        System.out.println(key.getAccess_key());

        SpringApplication.run(CandleApplication.class, args);
//        RunSocket socket = new RunSocket();
//        socket.run();

    }
}
