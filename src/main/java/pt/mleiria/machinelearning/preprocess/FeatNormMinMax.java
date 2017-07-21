/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.machinelearning.preprocess;

import java.util.Arrays;

import pt.mleiria.machinelearning.matrixAlgebra.Matrix;
import pt.mleiria.machinelearning.matrixAlgebra.Vector;

/**
 *
 * @author manuel
 */
public class FeatNormMinMax implements FeatureNormalization {

    private final double min;
    private final double max;

    public FeatNormMinMax() {
        this.min = 0.0;
        this.max = 1.0;

    }

    public FeatNormMinMax(double min, double max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public Matrix normalize(Matrix matrix) {
        final int rows = matrix.rows();
        final int columns = matrix.columns();
        //double[][] components = matrix.toComponents();
        /**
         * The normalized matrix
         */
        final double[][] newComponents = new double[rows][columns];
        for (int i = 0; i < columns; i++) {
            final double[] colVector = new double[rows];
            for (int j = 0; j < rows; j++) {
                colVector[j] = matrix.component(j, i);
            }
            double[] menorMaior = getMinMax(colVector);
            for (int j = 0; j < rows; j++) {
                newComponents[j][i] = min + 
                                        ((matrix.component(j, i) - menorMaior[0]) / 
                                        (menorMaior[1] - menorMaior[0]) * 
                                        (max - min));
            }
        }

        return new Matrix(newComponents);

    }
    /**
     * 
     */
    @Override
    public Vector normalize(Vector vector) {
    	throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     *
     * @param column
     * @return a double[2] with Menor in double[0] and Maior in double[1]
     */
    private double[] getMinMax(final double[] column) {
        final double[] tmpArr = column;
        Arrays.sort(tmpArr);
        final double[] result = new double[2];
        /**
         * Menor
         */
        result[0] = tmpArr[0];
        /**
         * Maior
         */
        result[1] = tmpArr[tmpArr.length - 1];
        return result;
    }

}
