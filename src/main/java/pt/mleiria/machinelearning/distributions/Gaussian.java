/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.machinelearning.distributions;

import static java.lang.Math.sqrt;
import java.util.Optional;
import java.util.Random;

/**
 *
 * @author manuel.leiria
 */
public final class Gaussian {
    
    private final double mean;
    private final double var;
    private final Random rnd;
    /**
     * 
     * @param mean
     * @param var
     * @param rnd 
     */
    public Gaussian(double mean, double var, Optional<Random> rnd) {
        if(var < 0.0 ){
            throw new IllegalArgumentException("var must be positive");
        }
        this.mean = mean;
        this.var = var;
        this.rnd = rnd.orElse(new Random());
    }
    /**
     * 
     * @return 
     */
    public double random(){
        return rnd.nextGaussian() * sqrt(var) + mean;
    }
    
    
    
}
