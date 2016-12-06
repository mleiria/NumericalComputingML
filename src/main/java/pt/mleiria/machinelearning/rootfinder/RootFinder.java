/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.machinelearning.rootfinder;

import pt.mleiria.machinelearning.interfaces.OneVarFunction;
import pt.mleiria.machinelearning.iterations.IteratorProcessor;
import pt.mleiria.numericalAnalysis.exception.InvalidIntervalException;

/**
 *
 * @author manuel
 */
public abstract class RootFinder extends IteratorProcessor{
    
    protected final OneVarFunction f;
    /**
     * 
     * @param f 
     */
    public RootFinder(OneVarFunction f) {
        this.f = f;
    }
    /**
     * 
     * @return 
     */
    public abstract double getRoot();
    
    /**
     * 
     * @throws InvalidIntervalException 
     */   
    public abstract void checkInterval() throws InvalidIntervalException;
    
}
