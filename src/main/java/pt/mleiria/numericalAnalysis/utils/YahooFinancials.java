/**
 *
 */
package pt.mleiria.numericalAnalysis.utils;

import java.util.Date;

/**
 * @author manuel
 *
 */
public class YahooFinancials {

    public enum Header {
        DATE, OPEN, HIGH, LOW, CLOSE, VOLUME, ADJ_CLOSE
    };

    private final Date date;
    private final double open;
    private final double high;
    private final double low;
    private final double close;
    private final double volume;
    private final double adjClose;

    public YahooFinancials(final Date date, final double open, final double high, final double low, final double close,
            final double volume, final double adjClose) {
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.volume = volume;
        this.close = close;
        this.adjClose = adjClose;
    }

    public Date getDate() {
        return date;
    }

    public double getOpen() {
        return open;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getClose() {
        return close;
    }

    public double getVolume() {
        return volume;
    }

    public double getAdjClose() {
        return adjClose;
    }

}
