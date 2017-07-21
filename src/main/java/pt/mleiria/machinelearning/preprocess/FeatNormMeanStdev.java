package pt.mleiria.machinelearning.preprocess;

import pt.mleiria.machinelearning.matrixAlgebra.Matrix;
import pt.mleiria.machinelearning.matrixAlgebra.Vector;
import pt.mleiria.machinelearning.statistics.StatisticalMoments;

/**
 *
 * @author manuel
 */
public class FeatNormMeanStdev implements FeatureNormalization {

    private double[] mean;
    private double[] stdev;

    /**
     * new Matrix = (elements - mean)/std
     *
     * @param matrix the Matrix to be normalizes
     * @return the normalized matrix
     */
    @Override
    public Matrix normalize(final Matrix matrix) {
        mean = new double[matrix.columns()];
        stdev = new double[matrix.columns()];

        final int rows = matrix.rows();
        final int columns = matrix.columns();
        double[][] components = matrix.toComponents();
        /**
         * The normalized matrix
         */
        final double[][] newComponents = new double[rows][columns];
        for (int i = 0; i < columns; i++) {
            final StatisticalMoments sm = new StatisticalMoments();
            int j = 0;
            for (j = 0; j < rows; j++) {
                sm.accumulate(components[j][i]);
            }
            mean[i] = sm.average();
            stdev[i] = sm.standardDeviation();
            for (j = 0; j < rows; j++) {
                newComponents[j][i] = (components[j][i] - mean[i]) / stdev[i];
            }
        }
        return new Matrix(newComponents);
    }
    /**
     * 
     * @param v
     * @return
     */
    @Override
    public Vector normalize(final Vector v){
    	mean = new double[1];
    	stdev = new double[1];
    	final int size = v.dimension();
    	
    	final StatisticalMoments sm = new StatisticalMoments();
        for (int j = 0; j < size; j++) {
            sm.accumulate(v.component(j));
        }
        mean[0] = sm.average();
        stdev[0] = sm.standardDeviation();
        final double components[] = new double[size];
        for (int j = 0; j < size; j++) {
            components[j] = (v.component(j) - mean[0]) / stdev[0];
        }
        return new Vector(components);
    }

    /**
     *
     * @return an array with the mean of all columns
     */
    public double[] getMean() {
        return mean;
    }

    /**
     *
     * @return an array with the stdev of all columns
     */
    public double[] getStdev() {
        return stdev;
    }

}
