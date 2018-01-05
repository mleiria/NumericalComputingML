package pt.mleiria.machinelearning.classification.knn;

import java.util.concurrent.CountDownLatch;
import pt.mleiria.machinelearning.statistics.EuclideanDistance;

/**
 * Task that of the fine-grained concurrent version
 *
 * @author author
 *
 */
public class IndividualDistanceTask implements Runnable {

    /**
     * Array of distances
     */
    private Distance[] distances;

    /**
     * Index of the example of the train data
     */
    private int index;

    /**
     * Example of the train data
     */
    private Row localExample;

    /**
     * Example we want to classify
     */
    private Row example;

    /**
     * Synchronization mechanism to control the end of tasks
     */
    private CountDownLatch endControler;

    /**
     * Constructor of the class. Initializes the internal data
     *
     * @param distances Array of distances
     * @param index Index of the train data
     * @param localExample Example of the train data
     * @param example Example we want to classify
     * @param endControler Synchronization mechanism to control the end of the
     * task
     */
    public IndividualDistanceTask(final Distance[] distances, final int index, final Row localExample, 
            final Row example,
            final CountDownLatch endControler) {
        this.distances = distances;
        this.index = index;
        this.localExample = localExample;
        this.example = example;
        this.endControler = endControler;
    }

    @Override
    /**
     * Concurrent task that calculates the distance between the train example
     * and the example we want to classify
     */
    public void run() {
        distances[index] = new Distance();
        distances[index].setIndex(index);
        distances[index].setDistance(new EuclideanDistance().getRelation(localExample.getExample(), example.getExample()));
        endControler.countDown();
    }

}
