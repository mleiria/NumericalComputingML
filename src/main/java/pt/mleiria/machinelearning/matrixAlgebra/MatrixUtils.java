/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.machinelearning.matrixAlgebra;

import static java.lang.Math.pow;
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
    /**
     * 
     * @param m
     * @param cols
     * @return 
     */
    public static Matrix expand(final Matrix m, final int cols){
        if(cols <= m.columns()){
            throw new IllegalArgumentException("Number of cols must be > " + m.columns());
        }
        final double[][] components = new double[m.rows()][cols];
        for(int i = 0; i < m.rows(); i++){
            for(int j = 0; j < cols; j++){
                if(j < m.columns()){
                    components[i][j] = m.component(i, j);
                }else{
                    components[i][j] = 0.0;
                }
            }
        }
        return new Matrix(components);
    }
    /**
     * 
     * @param dimension
     * @return 
     */
    public static Vector Ones(final int dimension){
        final double[] components = new double[dimension];
        for(int i = 0; i < dimension; i++){
            components[i] = 1.0;
        }
        return new Vector(components);
    }

    //TODO Generalizar
    public static Matrix expand(final Matrix m){
        final double[][] components = expand(m, 5).toComponents();
        for(int i = 0; i < m.rows(); i++){
            components[i][2] = pow(components[i][0], 2);
            components[i][3] = pow(components[i][1], 2);
            components[i][4] = components[i][0] * components[i][1];
            //components[i][5] = 1;
        }
        return new Matrix(components);
    }
}
