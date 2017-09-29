/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.machinelearning.functions.factorial;

import pt.mleiria.machinelearning.interfaces.Factorial;

/**
 *
 * @author manuel
 */
public class FactorialFactory {

    /**
     *
     */
    public enum FactorialTypes {
        CACHED, LOOP, RECURSIVE, PARALLEL;
    }

    /**
     * Defaults to Recursive Factorial
     *
     * @param ft
     * @return
     */
    public Factorial getFactorialAlgorithm(FactorialTypes ft) {
        if (null == ft) {
            return new RecursiveFactorial();
        }
        switch (ft) {
            case CACHED:
                return new CachedFactorial();
            case LOOP:
                return new LoopFactorial();
            case RECURSIVE:
                return new RecursiveFactorial();
            case PARALLEL:
                return new ParallelFactorial();
            default:
                return new RecursiveFactorial();
        }
    }
}
