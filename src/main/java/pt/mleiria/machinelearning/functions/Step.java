/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.machinelearning.functions;

import pt.mleiria.machinelearning.interfaces.OneVarFunction;

/**
 *
 * @author Manuel Leiria <manuel.leiria at gmail.com>
 */
public class Step implements OneVarFunction<Double>{

    @Override
    public Double value(Double x) {
        return x >= 0. ? 1. : -1.;
    }
    
    
    
}
