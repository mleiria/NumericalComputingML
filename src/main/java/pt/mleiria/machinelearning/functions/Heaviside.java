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
public class Heaviside implements OneVarFunction<Double> {

    private final Double threshold;

    public Heaviside(final Double threshold) {
        this.threshold = threshold;
    }

    @Override
    public Double value(Double x) {
        return x > threshold ? 1.0 : 0.0;
    }

}
