/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.machinelearning.statistics;

import java.util.concurrent.CountDownLatch;
import pt.mleiria.machinelearning.classification.knn.Distance;
import pt.mleiria.machinelearning.matrixAlgebra.Matrix;
import pt.mleiria.machinelearning.matrixAlgebra.Vector;

/**
 *
 * @author manuel
 */
public class DistanceTask implements Runnable {

    /**
     * Array of distances
     */
    private final Distance[] distances;
    /**
     * Indexes that determines the examples of the train data this task will
     * process
     */
    private final int startIndex, endIndex;
    /**
     * Example of the test data we want to classify
     */
    private final Vector example;
    /**
     * Data set with the train data examples
     */
    private final Matrix featuresX;
    /**
     * Synchronization mechanism to control the end of the task
     */
    private final CountDownLatch endControler;
    
    private final DistanceMetric distanceMetric;

    /**
     * Constructor of the class. Initializes all the internal data
     *
     * @param distances Array of distances
     * @param startIndex Start index that determines the examples of the train
     * data this task will process
     * @param endIndex End index that determines the examples of the train data
     * this task will process
     * @param dataSet Data set with the train data examples
     * @param example Example of the test data we want to classify
     * @param endControler Synchronization mechanism to control the end of the
     * task
     */
    public DistanceTask(final Distance[] distances, final int startIndex, final int endIndex,
            final Matrix featuresX, final Vector example, final CountDownLatch endControler, 
            final DistanceMetric distanceMetric) {
        this.distances = distances;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.featuresX = featuresX;
        this.example = example;
        this.endControler = endControler;
        this.distanceMetric = distanceMetric;
    }

    @Override
    public void run() {
        for (int index = startIndex; index < endIndex; index++) {
            final Vector localExample = featuresX.getRow(index);
            distances[index] = new Distance();
            distances[index].setIndex(index);
            distances[index].setDistance(
                    distanceMetric.getRelation(
                            localExample, example, example.dimension()));
        }
        endControler.countDown();
    }

}
