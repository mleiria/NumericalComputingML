/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.machinelearning;

/**
 *
 * @author manuel
 */
public class StopWatch {

    private long start;
    
    public StopWatch() {
        start = System.currentTimeMillis();
    }
    
    public double elapsedTime(){
        double elapsed = (System.currentTimeMillis() - start) / 1000.0;
        return elapsed;
    }
    
    

    
}
