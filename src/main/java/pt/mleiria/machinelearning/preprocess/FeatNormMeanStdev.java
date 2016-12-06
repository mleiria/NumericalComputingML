package pt.mleiria.machinelearning.preprocess;

import pt.mleiria.machinelearning.matrixAlgebra.Matrix;
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
