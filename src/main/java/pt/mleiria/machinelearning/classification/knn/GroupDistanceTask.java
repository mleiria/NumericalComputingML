/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.machinelearning.classification.knn;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import pt.mleiria.machinelearning.statistics.EuclideanDistance;

/**
 *
 * @author Manuel Leiria <manuel.leiria at gmail.com>
 */
public class GroupDistanceTask implements Runnable {

    /**
     * Array of distances
     */
    private Distance[] distances;

    /**
     * Indexes that determines the examples of the train data this task will
     * process
     */
    private int startIndex, endIndex;

    /**
     * Example of the test data we want to classify
     */
    private Row example;

    /**
     * Data set with the train data examples
     */
    private List<? extends Row> dataSet;

    /**
     * Synchronization mechanism to control the end of the task
     */
    private CountDownLatch endControler;

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
    public GroupDistanceTask(Distance[] distances, int startIndex,
            int endIndex, List<? extends Row> dataSet, Row example,
            CountDownLatch endControler) {
        this.distances = distances;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.example = example;
        this.dataSet = dataSet;
        this.endControler = endControler;
    }

    @Override
    public void run() {
        for (int index = startIndex; index < endIndex; index++) {
            Row localExample = dataSet.get(index);
            distances[index] = new Distance();
            distances[index].setIndex(index);
            distances[index].setDistance(new EuclideanDistance().getRelation(localExample.getExample(), example.getExample()));
        }
        endControler.countDown();
    }


}
