/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.machinelearning.rootfinder;

import static java.lang.Math.abs;
import static java.lang.Math.abs;
import pt.mleiria.machinelearning.interfaces.OneVarFunctionDerivative;
import pt.mleiria.numericalAnalysis.exception.InvalidIntervalException;

/**
 *
 * @author manuel
 */
public class NewtonRootFinder extends RootFinder {

    private double xm, xm1;
    /**
     * Interval [a, b] where to find root
     */
    private final double a, b;
    /**
     *
     */
    private final double startingPoint;

    private final OneVarFunctionDerivative<Double> df;

    /**
     *
     * @param df Function derivative The function must be continuous and
     * derivative in the interval
     * @param a
     * @param b
     * @param sp Starting point
     */
    public NewtonRootFinder(final OneVarFunctionDerivative df, final double a, final double b, final double sp) {
        super(df);
        this.a = a;
        this.b = b;
        this.df = df;
        this.startingPoint = sp;

    }

    @Override
    public void initializeIterations() {
        try {
            checkInterval();
        } catch (InvalidIntervalException ex) {
            log.error(ex);
            throw new IllegalArgumentException("Invalid Interval");
        }
        this.xm = startingPoint;
    }

    /**
     *
     * @throws InvalidIntervalException
     */
    @Override
    public void checkInterval() throws InvalidIntervalException {
        if (f.value(a) * f.value(b) > 0) {
            throw new InvalidIntervalException();
        }
    }

    /**
     *
     * @return
     */
    @Override
    public double evaluateIteration() {
        xm1 = xm - f.value(xm) / df.derivative(xm);
        return abs(xm1 - xm);
    }

    /**
     *
     * @return
     */
    @Override
    public boolean hasConverged() {
        if ((abs(xm1 - xm) <= getDesiredPrecision())
                || (f.value(xm1) <= getDesiredPrecision() && f.value(xm1) >= -getDesiredPrecision())) {
            return true;
        } else {
            xm = xm1;
            return false;
        }
    }

    @Override
    public double getRoot() {
        return xm;
    }

}
