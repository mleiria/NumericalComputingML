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
import pt.mleiria.machinelearning.statistics.EuclideanDistance;

/**
 *
 * @author Manuel Leiria <manuel.leiria at gmail.com>
 */
public class KnnClassifier {

    private final List<? extends Row> dataSet;
    private final int k;

    /**
     *
     * @param dataSet
     * @param k
     */
    public KnnClassifier(final List<? extends Row> dataSet, final int k) {
        this.dataSet = dataSet;
        this.k = k;
    }
    /**
     * 
     * @param example
     * @return 
     */
    public String classify(final Row example) {
        final Distance[] distances = new Distance[dataSet.size()];
        int index = 0;

        for (final Row localExample : dataSet) {
            distances[index] = new Distance();
            distances[index].setIndex(index);
            distances[index].setDistance(new EuclideanDistance().getRelation(
                    localExample.getExample(), example.getExample()));
            index++;
        }
        Arrays.sort(distances);
        final Map<String, Integer> results = new HashMap<>();
        for(int i = 0; i < k; i++){
            final Row localExample = dataSet.get(distances[i].getIndex());
            final String tag = localExample.getTag();
            results.merge(tag, 1, (a, b) -> a + b);
        }
        return Collections.max(results.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
}
