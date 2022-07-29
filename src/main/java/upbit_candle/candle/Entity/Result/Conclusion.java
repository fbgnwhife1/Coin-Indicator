package upbit_candle.candle.Entity.Result;

public enum Conclusion  {

    ticker("ticker", "현재가"),
    trade("trade", "체결"),
    orderbook("orderbook", "호가");

    private String type;
    private String name;

    Conclusion(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }
}
