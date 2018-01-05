/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.machinelearning.classification.knn;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *
 * @author Manuel Leiria <manuel.leiria at gmail.com>
 */
public class KnnClassifierParallelIndividual {
    private final List<? extends Row> dataSet;
    private final int k;
    private final ThreadPoolExecutor executor;
    private final int numThreads;
    private final boolean parallelSort;
    /**
     * 
     * @param dataSet
     * @param k
     * @param factor
     * @param parallelSort 
     */
    public KnnClassifierParallelIndividual(final List<? extends Row> dataSet, final int k, final int factor, 
            final boolean parallelSort) {
        this.dataSet = dataSet;
        this.k = k;
        numThreads = factor * (Runtime.getRuntime().availableProcessors());
        executor = (ThreadPoolExecutor)Executors.newFixedThreadPool(numThreads);
        this.parallelSort = parallelSort;
    }
    /**
     * 
     * @param example
     * @return
     * @throws Exception 
     */
    public String classify(final Row example) throws Exception{
        final Distance[] distances = new Distance[dataSet.size()];
        final CountDownLatch endController = new CountDownLatch(dataSet.size());
        
        int index = 0;
        for(Row localExample: dataSet){
            final IndividualDistanceTask task = new IndividualDistanceTask(distances, index, localExample, 
                    example, endController);
            executor.execute(task);
            index++;
        }
        endController.await();
        if(parallelSort){
            Arrays.parallelSort(distances);
        }else{
            Arrays.sort(distances);
        }
        final Map<String, Integer> results = new HashMap<>();
        for(int i = 0; i < k; i++){
            final Row localExample = dataSet.get(distances[i].getIndex());
            final String tag = localExample.getTag();
            results.merge(tag, 1, (a, b) -> a + b);
        }
        return Collections.max(results.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
    /**
     * Method that finish the execution of the executor
     */
    public void destroy(){
        executor.shutdown();
    }
}
