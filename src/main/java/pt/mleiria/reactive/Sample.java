/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.reactive;

import io.reactivex.Observable;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author manuel
 */
public class Sample {

    public static void main(String[] args) {

        List<String> symbols = Arrays.asList("GOOG", "AMZN");

        Observable<StockInfo> feed = StockServer.getFeed(symbols);
        System.out.println("Got observable...");

        //Become observer to the observable
        feed
            //.onErrorResumeNext()
            .filter(stockInfo -> stockInfo.getTheValue() > 0.5)
            .subscribe(
                    stockInfo -> System.out.println(stockInfo),
                    err -> System.out.println("ERROR:" + err),
                    () -> System.out.println("DONE")
            );
    }
}
