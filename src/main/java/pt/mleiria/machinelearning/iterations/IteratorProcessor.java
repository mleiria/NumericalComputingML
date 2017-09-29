/*
 * A general structure for managing iterations
 */
package pt.mleiria.machinelearning.iterations;

import org.apache.log4j.Logger;
import static org.apache.log4j.Logger.getLogger;

/**
 *
 * @author manuel
 */
public abstract class IteratorProcessor {

    protected static final Logger log = getLogger("mlearningLog");

    /**
     * Number of iterations performed.
     */
    protected int iterations;
    /**
     * Maximum allowed number of iterations.
     */
    private int maximumIterations = 5000;
    /**
     * Desired precision.
     */
    private double desiredPrecision;
    /**
     * Achieved precision.
     */
    private double precision;

    /**
     * Generic constructor.
     */
    public IteratorProcessor() {
    }

    /**
     * Performs the iterative process
     */
    public void evaluate() {
        iterations = 0;
        initializeIterations();
        while (iterations++ < maximumIterations) {
            precision = evaluateIteration();
            if (hasConverged()) {
                log.info("Converged at iteration: [" + iterations + "]");
                log.info("Converged with precision:[" + precision + "]");
                break;
            }
        }
        finalizeIterations();
    }

    /**
     * Evaluate the result of the current iteration.
     *
     * @return the estimated precision of the result.
     */
    abstract public double evaluateIteration();

    /**
     * Clean-up operations
     */
    public void finalizeIterations() {
        log.info("Iterations:[" + iterations + "]");
    }

    /**
     * @return desired precision.
     */
    public double getDesiredPrecision() {
        return desiredPrecision;
    }

    /**
     * @return the number of iterations performed.
     */
    public int getIterations() {
        return iterations;
    }

    /**
     * @return the maximum allowed number of iterations.
     */
    public int getMaximumIterations() {
        return maximumIterations;
    }

    /**
     * @return the attained precision.
     */
    public double getPrecision() {
        return precision;
    }

    /**
     * Check to see if the result has been attained.
     *
     * @return boolean
     */
    public boolean hasConverged() {
        return precision < desiredPrecision;
    }

    /**
     * Initializes internal parameters to start the iterative process.
     */
    public void initializeIterations() {
    }

    /**
     * Defines the desired precision.
     *
     * @param prec
     */
    public void setDesiredPrecision(double prec)
            throws IllegalArgumentException {
        if (prec <= 0) {
            throw new IllegalArgumentException("Non-positive precision: " + prec);
        }
        desiredPrecision = prec;
    }

    /**
     * Defines the maximum allowed number of iterations.
     *
     * @param maxIter
     */
    public void setMaximumIterations(int maxIter)
            throws IllegalArgumentException {
        if (maxIter < 1) {
            throw new IllegalArgumentException("Non-positive maximum iteration: " + maxIter);
        }
        maximumIterations = maxIter;
    }
}
