/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.machinelearning.statistics;

import java.util.Arrays;
import pt.mleiria.machinelearning.matrixAlgebra.Vector;

/**
 *
 * @author manuel
 */
public class ColumnStatistics {

    private final double[] col;
    private final StatisticalMoments sm = new StatisticalMoments();
    private final int dimension;
    /**
     * 
     * @param column 
     */
    public ColumnStatistics(final double[] column) {
        this.col = column;
        this.dimension = column.length;
        processValues();
    }
    /**
     * 
     * @param v 
     */
    public ColumnStatistics(final Vector v) {
        this.col = v.toComponents();
        this.dimension = v.dimension();
        processValues();
    }
    /**
     * 
     */
    private void processValues(){
        for(int i = 0; i < col.length; i++){
            sm.accumulate(col[i]);
        }
    }
    
    /**
     * 
     * @return 
     */
    public double getAverage(){
        return sm.average();
    }
    /**
     * 
     * @return 
     */
    public double getStandardDeviation(){
        return sm.standardDeviation();
    }
    /**
     * 
     * @return 
     */
    public double getUnnormalizedVariance(){
        return sm.unnormalizedVariance();
    }
    /**
     * 
     * @return 
     */
    public double getVariance(){
        return sm.variance();
    }
    /**
     * 
     * @return 
     */
    public double getKurtosis(){
        return sm.kurtosis();
    }
    /**
     * 
     * @return 
     */
    public double getSkewness(){
        return sm.skewness();
    }
    /**
     * 
     * @return 
     */
    public double getMedian(){
        final double[] sortedArr = new double[dimension];
        System.arraycopy(this.col, 0, sortedArr, 0, dimension);
        Arrays.sort(sortedArr);
        if(dimension%2 == 0){
            return (sortedArr[dimension/2] + sortedArr[dimension/2 + 1]) / 2;
        }else{
            return sortedArr[dimension/2 + 1];
        }
    }

}
