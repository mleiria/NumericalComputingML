/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.reactive;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author manuel
 */
public class StockServer {

    public static Observable<StockInfo> getFeed(List<String> symbols) {
        System.out.println("Created..");
        return Observable.create(emitter -> emitPrice(emitter, symbols));
    }

    private static void emitPrice(ObservableEmitter<StockInfo> emitter, List<String> symbols) {
        System.out.println("Ready to emit...");
        
        int count = 0;
        while(count < 10){
            symbols.stream()
                    .map(StockInfo::fetch)
                    .forEach(emitter::onNext);
            sleep(1000);
            count++;
            
            //if(count > 2) emitter.onError(new RuntimeException("oops"));
            
        }
        emitter.onComplete();
    }
    
    public static void sleep(int ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Logger.getLogger(StockServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
