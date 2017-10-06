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
