/**
 *
 */
package pt.mleiria.machinelearning.classification;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import pt.mleiria.machinelearning.classification.knn.Distance;
import pt.mleiria.machinelearning.matrixAlgebra.Matrix;
import pt.mleiria.machinelearning.matrixAlgebra.MatrixUtils;
import pt.mleiria.machinelearning.matrixAlgebra.Vector;
import pt.mleiria.machinelearning.statistics.DistanceMetric;
import pt.mleiria.machinelearning.statistics.DistanceTask;

/**
 * @author manuel
 *
 */
public class KNN {

    private final int k;
    private final Matrix featuresX;
    private final Vector outputY;

    /**
     *
     * @param featuresX
     * @param outputY
     * @param k
     */
    public KNN(final Matrix featuresX, final Vector outputY, final int k) {
        this.featuresX = featuresX;
        this.outputY = outputY;
        this.k = k;
    }

    /**
     *
     * @param dataSet
     * @param k
     */
    public KNN(final Matrix dataSet, final int k) {
        this.k = k;
        final Matrix[] aux = dataSet.split(1);
        featuresX = aux[0];
        outputY = MatrixUtils.toVector(aux[1]);
    }

    /**
     *
     * @param example
     * @param dm DistanceMetric
     * @return
     */
    public Double classifySerial(final Vector example, final DistanceMetric dm) {
        final Distance[] distances = new Distance[outputY.dimension()];
        for (int i = 0; i < outputY.dimension(); i++) {
            distances[i] = new Distance();
            distances[i].setIndex(i);
            distances[i].setDistance(dm.getRelation(featuresX.getRow(i), example, example.dimension()));
        }
        Arrays.sort(distances);
        return computeFinal(distances);
    }
    /**
     *
     * @param example
     * @param dm
     * @param factor 
     * @return
     */
    public Double classifyParallel(final Vector example, final DistanceMetric dm, final int factor)
            throws Exception {
        final int numThreads = factor * Runtime.getRuntime().availableProcessors();
        final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(numThreads);

        final Distance[] distances = new Distance[outputY.dimension()];
        final CountDownLatch endControler = new CountDownLatch(numThreads);
        final int length = outputY.dimension() / numThreads;
        int startIndex = 0;
        int endIndex = length;

        for (int i = 0; i < numThreads; i++) {
            DistanceTask task = new DistanceTask(distances, startIndex, endIndex, 
                    featuresX, example, endControler, dm);
            startIndex = endIndex;
            if (i < numThreads - 2) {
                endIndex = endIndex + length;
            } else {
                endIndex = outputY.dimension();
            }
            executor.execute(task);
        }
        endControler.await();
        Arrays.parallelSort(distances);
        
        return computeFinal(distances);
    }
    /**
     * 
     * @param distances
     * @return 
     */
    private double computeFinal(final Distance[] distances){
                final Map<Double, Integer> results = new HashMap<>();
        for (int i = 0; i < k; i++) {
            final Double label = outputY.component(distances[i].getIndex());
            results.merge(label, 1, (a, b) -> a + b);
        }
        return Collections.max(results.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
    /*
    public String classifyParallel(final Vector example){
        
    }
     */

}
