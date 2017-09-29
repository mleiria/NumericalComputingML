/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.machinelearning.functions;

import static java.lang.Math.log;
import pt.mleiria.machinelearning.interfaces.OneVarFunction;
import pt.mleiria.machinelearning.matrixAlgebra.Matrix;

/**
 *
 * @author manuel
 */
public class Log implements OneVarFunction<Matrix> {

    @Override
    public Matrix value(final Matrix x) {
        final double[][] components = x.toComponents();
        final double[][] newComponents = new double[x.rows()][x.columns()];
        for (int i = 0; i < x.rows(); i++) {
            for (int j = 0; j < x.columns(); j++) {
                newComponents[i][j] = log(components[i][j]);
            }
        }
        return new Matrix(newComponents);

    }

}
