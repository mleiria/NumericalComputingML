/**
 *
 */
package pt.mleiria.numericalAnalysis.nonLinearEquation.rootFinder;

import pt.mleiria.numericalAnalysis.exception.IterationCountExceededException;

/**
 * @author Manuel
 *
 */
public interface RootFinder {

    /**
     * Indica se o algoritmo convergiu
     *
     * @return true se sim, false c.c.
     */
    public boolean hasConverged();

    /**
     * @throws IterationCountExceededException
     */
    public abstract void findRoot() throws IterationCountExceededException;

    /**
     * Computa a posicao seguinte de x
     */
    public void computeNextPosition();
}
