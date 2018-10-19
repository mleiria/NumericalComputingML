/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.machinelearning.functions;

import static java.lang.Math.exp;
import java.util.Arrays;
import static java.util.Arrays.asList;
import pt.mleiria.machinelearning.interfaces.ActivationFunction;

/**
 *
 * @author manuel
 */
public class SoftMax implements ActivationFunction<Double[]> {

    @Override
    public Double[] value(Double[] x) {
        final int n = x.length;
        final Double[] y = new Double[n];
        double sum = 0.;
        
        double max;
        //To prevent overflow
        max = asList(x)
                    .stream()
                    .mapToDouble(Double::doubleValue)
                    .max()
                    .orElse(0.);

        for (int i = 0; i < n; i++) {
            y[i] = exp(x[i] - max);
            sum += y[i];
        }

        for (int i = 0; i < n; i++) {
            y[i] /= sum;
        }

        return y;

    }

}
