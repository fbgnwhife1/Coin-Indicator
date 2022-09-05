# Imitate SIGBIT
https://sigbtc.pro/derivatives

## Project Goals
* 업비트 데이터 서버, 클라이언트 요청 서버 분리 (完)
* 업비트 실시간 체결내역 db 저장 (完)
* 하루마다 체결내역 csv파일 저장 후 db 삭제 (完)
* 클라이언트가 요청한 실시간 체결 내역 실시간 전송 (完)
* 시장 트렌드 & 지수 api (完)
* RSI 캐시 적용 (完)
* 공포 탐욕 지수 api (完)
* 호가잔량 이용 BSI 계산 (完)
* 호가잔량정보를 이용한 데이트레이딩전략의 수익성 분석 
(http://koreascience.or.kr/article/JAKO201922441756714.pdf)

## Usage
* url : ws://localhost:{gateway-port}/ws/v1
* ex) request : {
            "list" : [
            {"code" : "KRW-BTC"}, 
            {"code" : "KRW-DOGE"}, 
            {"code" : "KRW-ETH"}],
            pivot : 2000000
            }
* RSI - http://localhost:{gateway-port}/v1/analyze/rsi/KRW-BTC
* BSI - http://localhost:{gateway-port}/v1/analyze/bsi/KRW-BTC
 
## Service
* https://github.com/fbgnwhife1/get-upbit-data-service
* https://github.com/fbgnwhife1/trade-indicator
* https://github.com/fbgnwhife1/Service-Socket
* https://github.com/fbgnwhife1/service-search-Eureka

## To do next
* Cache 성능 테스트
* ws 성능 테스트
* BSI 기능 추가
* 서비스 명확히 분리

 ## Technologies Used
* Java 15
* Spring Boot 2.7.x
* spring-boot-starter-websocket
* okhttp3
* Spring Cloud Gateway
* Eureka
* MariaDB
* Spring Data JPA
* QueryDsl
* RabbitMQ

 ## Reference
 * https://alternative.me/
 * https://sas-study.tistory.com/432
 * http://koreascience.or.kr/article/JAKO201922441756714.pdf
 * https://github.com/piomin/analyzer
 * 디지털자산 공포-탐욕 지수 Methodology and rules | Dunamu Datavalue Team