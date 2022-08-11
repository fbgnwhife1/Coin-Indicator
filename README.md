# Imitate SIGBIT
https://sigbtc.pro/derivatives

## Project Goals
* upbit 실시간 체결내역 db 저장 (完)
* 클라이언트가 요청한 실시간 체결 내역 실시간 전송 (完)
* 클라이언트가 가격 피벗 요청 가능 (完)
* 시장 트렌드 & 지수 api
* bitmax api 이용 고래 포지션 확인
* 호가잔량정보를 이용한 데이트레이딩전략의 수익성 분석 (http://koreascience.or.kr/article/JAKO201922441756714.pdf)


## Usage
* url : ws://localhost:9091/ws/v1
* ex) request : {
            "list" : [
            {"code" : "KRW-BTC"}, 
            {"code" : "KRW-DOGE"}, 
            {"code" : "KRW-ETH"}],
            pivot : 2000000
            }
 
 
 ## Reference
 * https://sas-study.tistory.com/432
 * http://koreascience.or.kr/article/JAKO201922441756714.pdf