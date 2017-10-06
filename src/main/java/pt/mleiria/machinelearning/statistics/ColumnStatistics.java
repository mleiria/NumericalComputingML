/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.machinelearning.statistics;

import static java.lang.System.arraycopy;
import static java.util.Arrays.sort;
import pt.mleiria.machinelearning.matrixAlgebra.Vector;

/**
 *
 * @author manuel
 */
public class ColumnStatistics {

    private final double[] col;
    private final StatisticalMoments sm;
    private final int dimension;
    private double maxValue;
    private double minValue;

    /**
     *
     * @param column
     */
    public ColumnStatistics(final double[] column) {
        this.col = column;
        this.dimension = column.length;
        sm = new StatisticalMoments();
        processValues();
    }

    /**
     *
     * @param v
     */
    public ColumnStatistics(final Vector v) {
        this.col = v.toComponents();
        this.dimension = v.dimension();
        sm = new StatisticalMoments();
        processValues();
    }

    /**
     *
     */
    private void processValues() {
        maxValue = minValue = col[0];
        for (int i = 0; i < col.length; i++) {
            sm.accumulate(col[i]);
            if (col[i] > maxValue) {
                maxValue = col[i];
            }
            if (col[i] < minValue) {
                minValue = col[i];
            }
        }
    }

    /**
     *
     * @return
     */
    public double getAverage() {
        return sm.average();
    }

    /**
     *
     * @return
     */
    public double getStandardDeviation() {
        return sm.standardDeviation();
    }

    /**
     *
     * @return
     */
    public double getUnnormalizedVariance() {
        return sm.unnormalizedVariance();
    }

    /**
     *
     * @return
     */
    public double getVariance() {
        return sm.variance();
    }

    /**
     *
     * @return
     */
    public double getKurtosis() {
        return sm.kurtosis();
    }

    /**
     *
     * @return
     */
    public double getSkewness() {
        return sm.skewness();
    }

    /**
     *
     * @return the maximum value of the vector
     */
    public double getMaxValue() {
        return maxValue;
    }

    /**
     *
     * @return the minimum value of the vector
     */
    public double getMinValue() {
        return minValue;
    }

    /**
     *
     * @return
     */
    public double getMedian() {
        final double[] sortedArr = new double[dimension];
        arraycopy(this.col, 0, sortedArr, 0, dimension);
        sort(sortedArr);
        if (dimension % 2 == 0) {
            return (sortedArr[dimension / 2] + sortedArr[dimension / 2 + 1]) / 2;
        } else {
            return sortedArr[dimension / 2 + 1];
        }
    }

    /**
     *
     * @return
     */
    public double[] getAutoCorrelation() {
        final double[] autoCorrelation = new double[dimension];
        final double variance = getUnnormalizedVariance();
        final double average = getAverage();
        for (int k = 0; k < dimension; k++) {
            for (int t = 0; t < dimension - k - 1; t++) {
                autoCorrelation[k] += (col[t] - average) * (col[t + k] - average);
            }
            autoCorrelation[k] /= variance;
        }
        return autoCorrelation;
    }

}
