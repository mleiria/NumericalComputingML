/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.machinelearning.functions;

import pt.mleiria.machinelearning.interfaces.OneVarFunction;

/**
 *
 * @author manuel
 */
public class Heaviside implements OneVarFunction{

    private final double threshold;

    public Heaviside(final double threshold) {
        this.threshold = threshold;
    }
    
    
    
    @Override
    public double value(double x) {
        if (x > threshold) {
            return 1.0;
        }
        return 0.0;
    }
    
}
