/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.machinelearning.functions;

import pt.mleiria.machinelearning.interfaces.OneVarFunctionDerivative;
import pt.mleiria.machinelearning.matrixAlgebra.Matrix;
import pt.mleiria.machinelearning.matrixAlgebra.MatrixUtils;

/**
 *
 * @author Manuel Leiria <manuel.leiria at gmail.com>
 */
public class SigmoidDerivative implements OneVarFunctionDerivative<Matrix>{

    @Override
    public Matrix derivative(final Matrix x) {
        final Matrix ones = MatrixUtils.Ones(x.rows(), x.columns());
        final Matrix sigmoid = value(x);
        final Matrix aux = ones.subtract(sigmoid);
      
        return sigmoid.transpose().multiply(aux);
    }

    @Override
    public Matrix value(final Matrix x) {
        return new Sigmoid().value(x);
    }
    
    
    
}
