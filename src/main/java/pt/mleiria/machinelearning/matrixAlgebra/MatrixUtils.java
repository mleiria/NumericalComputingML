/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.machinelearning.matrixAlgebra;

import static java.lang.Math.random;
import static java.lang.Math.sqrt;

/**
 *
 * @author manuel
 */
public class MatrixUtils {

    /**
     *
     * @param rows
     * @param cols
     * @return a Matrix of 1.0
     */
    public static Matrix Ones(final int rows, final int cols) {
        final double[][] components = new double[rows][cols];
        // for each row
        for (int i = 0; i < rows; i++) {
            // for each column
            for (int j = 0; j < cols; j++) {
                components[i][j] = 1.0;
            }
        }
        return new Matrix(components);
    }

    /**
     * Creates a matrix with random elements uniformly distributed on the
     * interval (0, 1).
     *
     * @param rows
     * @param cols
     * @return Matrix
     */
    public static Matrix rand(final int rows, final int cols) {
        final double[][] components = new double[rows][cols];
        // for each row
        for (int i = 0; i < rows; i++) {
            // for each column
            for (int j = 0; j < cols; j++) {
                components[i][j] = random();
            }
        }
        return new Matrix(components);
    }

    /**
     *
     * @param rows
     * @param cols
     * @return an Identity Matrix
     */
    public static Matrix identity(final int rows, final int cols) {
        final double[][] components = new double[rows][cols];
        if (rows != cols) {
            throw new IllegalArgumentException("Only Square matrix supported.");
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == j) {
                    components[i][j] = 1;
                } else {
                    components[i][j] = 0;
                }
            }
        }
        return new Matrix(components);
    }
    /**
     * 
     * @param matrix
     * @return 
     */
    public static Vector toVector(final Matrix matrix) {
        if (matrix.columns() != 1) {
            throw new IllegalArgumentException("Only Matrix with 1 column can be converted");
        }
        final double[] d = new double[matrix.rows()];
        for (int i = 0; i < matrix.rows(); i++) {
            d[i] = matrix.component(i, 0);
        }
        return new Vector(d);
    }
    /**
     * Trace Operator
     * @param matrix
     * @return 
     */
    public static double trace(final Matrix matrix){
        final int rows = matrix.rows();
        final int cols = matrix.columns();
        double sum = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == j) {
                    sum += matrix.component(j, j);
                } 
            }
        }
        return sum;
    }
    /**
     * 
     * @param matrix
     * @return 
     */
    public static double frobeniusNorm(final Matrix matrix){
        final Matrix transMatrix = matrix.transpose();
        return sqrt(trace(matrix.multiply(transMatrix)));
    }

}
