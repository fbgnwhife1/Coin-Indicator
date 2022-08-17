# Imitate SIGBIT
https://sigbtc.pro/derivatives

## Project Goals
* upbit 실시간 체결내역 db 저장 (完)
* 클라이언트가 요청한 실시간 체결 내역 실시간 전송 (完)
* 클라이언트가 가격 피벗 요청 가능 (完)
* 시장 트렌드 & 지수 api (完)
* 클라이언트 피벗 요청 마켓별로 설정 기능
* 공포 탐욕 지수 api (完)
* 호가잔량 이용 BSI 계산 (完)
* 호가잔량정보를 이용한 데이트레이딩전략의 수익성 분석  
(http://koreascience.or.kr/article/JAKO201922441756714.pdf)

## Usage
* url : ws://localhost:9091/ws/v1
* ex) request : {
            "list" : [
            {"code" : "KRW-BTC"}, 
            {"code" : "KRW-DOGE"}, 
            {"code" : "KRW-ETH"}],
            pivot : 2000000
            }
* RSI - http://localhost:9091/v1/analyze/rsi/KRW-BTC?period=14
* BSI - http://localhost:9091/v1/analyze/bsi/KRW-BTC
 
 ## Technologies Used
* Java 15
* Spring Boot 2.7.2
* spring-boot-starter-websocket
* okhttp3
* Spring Data jpa
* QueryDsl
 
 ## Reference
 * https://alternative.me/
 * https://sas-study.tistory.com/432
 * http://koreascience.or.kr/article/JAKO201922441756714.pdf
 * https://github.com/piomin/analyzer
 * 디지털자산 공포-탐욕 지수 Methodology and rules | Dunamu Datavalue Team