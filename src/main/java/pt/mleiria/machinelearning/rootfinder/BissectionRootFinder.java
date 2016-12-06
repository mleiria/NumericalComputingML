/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.machinelearning.rootfinder;

import pt.mleiria.machinelearning.interfaces.OneVarFunction;
import pt.mleiria.numericalAnalysis.exception.InvalidIntervalException;

/**
 *
 * @author manuel
 */
public class BissectionRootFinder extends RootFinder {

    /**
     * a: x-coordinate of the left of the interval 
     * b: x-coordinate of the
     * right of the interval
     */
    private final double a, b;
    /**
     * x-negative value
     */
    private double xNeg;
    /**
     * x-middle value
     */
    private double xMid;
    /**
     * x-positive value
     */
    private double xPos;
    /**
     * previous x-middle value
     */
    private double prevXMid;
    /**
     * f(xNeg)
     */
    private double fNeg;
    /**
     * f(xMid)
     */
    private double fMid;
    /**
     * f(xPos)
     */
    private double fPos;

    /**
     *
     * @param f
     * @param a
     * @param b
     * @param maxIter
     * @param desiredPrecison
     */
    public BissectionRootFinder(final OneVarFunction f, final double a, final double b, 
            final int maxIter, final double desiredPrecison) {
        super(f);
        setMaximumIterations(maxIter);
        setDesiredPrecision(desiredPrecison);
        this.a = a;
        this.b = b;
    }

    @Override
    public void initializeIterations() {
        final double yMin = f.value(a);
        final double yMax = f.value(b);
        xMid = Math.abs(b - a) / 2.0;
        if(f.value(a) < 0){
            xNeg = a;
            xPos = b;
            fNeg = yMin;
            fPos = yMax;
        }else{
            xNeg = b;
            xPos = a;
            fNeg = yMax;
            fPos = yMin;
        }
    }

    
    /**
     *
     * @return
     */
    @Override
    public double getRoot() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * The interval is invalid if f(xMin) and f(xMax) have the same signs
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
        if(f.value(xMid) < 0){
            xNeg = xMid;
            fNeg = fMid;
        }else{
            xPos = xMid;
            fPos = fMid;
        }
        prevXMid = xMid;
        xMid = (xNeg + xPos)/2;
        
        return Math.abs(xMid - prevXMid);
    }

}
