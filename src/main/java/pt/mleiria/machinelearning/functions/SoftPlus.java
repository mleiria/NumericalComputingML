/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.machinelearning.functions;

import static java.lang.Math.exp;
import static java.lang.Math.log;
import pt.mleiria.machinelearning.interfaces.OneVarFunction;

/**
 *
 * @author manuel
 */
public class SoftPlus implements OneVarFunction<Double>{

    @Override
    public Double value(Double x) {
        return log(1 + exp(x));
    }
    
    
}
