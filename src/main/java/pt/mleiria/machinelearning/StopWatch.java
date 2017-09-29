/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.machinelearning;

import static java.lang.System.currentTimeMillis;

/**
 *
 * @author manuel
 */
public class StopWatch {

    private final long start;

    public StopWatch() {
        start = currentTimeMillis();
    }

    public double elapsedTime() {
        double elapsed = (currentTimeMillis() - start) / 1000.0;
        return elapsed;
    }

}
