package upbit_candle.candle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CandleApplication {

    public static void main(String[] args) throws Exception{
        SpringApplication.run(CandleApplication.class, args);
    }
}
