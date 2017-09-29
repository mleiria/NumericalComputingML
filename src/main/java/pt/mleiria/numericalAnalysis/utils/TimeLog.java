/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.numericalAnalysis.utils;

import static java.lang.System.currentTimeMillis;

/**
 *
 * @author manuel
 */
public final class TimeLog {

    private final long start;

    /**
     * Create a TimeLog object.
     */
    public TimeLog() {
        start = currentTimeMillis();
    }

    /**
     * Return elapsed time (in seconds) since this object was created.
     *
     * @return
     */
    public double elapsedTime() {
        long now = currentTimeMillis();
        return (now - start) / 1000.0;
    }
}
