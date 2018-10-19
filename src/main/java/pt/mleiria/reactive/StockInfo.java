/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.reactive;

import java.util.Random;
import yahoofinance.YahooFinance;

/**
 *
 * @author manuel
 */
public class StockInfo {

    private final String theTicker;
    private final double theValue;

    public StockInfo(final String theTicker, final double theValue) {
        this.theTicker = theTicker;
        this.theValue = theValue;
    }

    public static StockInfo fetch(final String thicker){
        Random rnd = new Random();
        final double value = rnd.nextDouble();
        if(value > 0.8) throw new RuntimeException("SHIT");
        
        return new StockInfo(thicker, value);
    }

    @Override
    public String toString() {
        return "StockInfo{" + "theTicker=" + theTicker + ", theValue=" + theValue + '}';
    }

    public String getTheTicker() {
        return theTicker;
    }

    public double getTheValue() {
        return theValue;
    }

}
