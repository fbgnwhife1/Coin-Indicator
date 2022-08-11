package upbit_candle.candle.Analyze.RSI;


import java.util.Arrays;

public class RSI {
    private int period;
    private EMA ema;

    public RSI(int period) {
        this.period = period;
        this.ema = new EMA(2*period - 1);
    }

    public double[] count(final double[] prizes) {

        final double[] up = new double[prizes.length - 1];
        final double[] down = new double[prizes.length - 1];
        for (int i = 0; i < prizes.length - 1; i++) {
            if (prizes[i] > prizes[i + 1]) {
                up[i] = prizes[i] - prizes[i + 1];
                down[i] = 0;
            }
            if (prizes[i] < prizes[i + 1]) {
                down[i] = Math.abs(prizes[i] - prizes[i + 1]);
                up[i] = 0;
            }
        }

        final int emaLength = prizes.length - 2 * period;
        double[] rsis = new double[0];
        if (emaLength > 0) {
            final double[] emus = new double[emaLength];
            final double[] emds = new double[emaLength];
            ema.count(up, 0, emus);
            ema.count(down, 0, emds);

            rsis = new double[emaLength];
            for (int i = 0; i < rsis.length; i++) {
                rsis[i] = 100 - (100 / (double) (1 + emus[i] / emds[i]));
            }
        }

        return rsis;
    }
}
