/**
 *
 */
package pt.mleiria.machinelearning.classification;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Double.parseDouble;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import java.util.ArrayList;
import static java.util.Collections.reverseOrder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import static org.apache.log4j.Logger.getLogger;

import pt.mleiria.machinelearning.matrixAlgebra.Matrix;
import pt.mleiria.machinelearning.matrixAlgebra.Vector;
import pt.mleiria.machinelearning.preprocess.ConvertToNumericDummy;

/**
 * @author manuel
 *
 */
public class KNN {

    protected static final Logger log = getLogger("mlearningLog");
    /**
     * Caminho para o ficheiro onde esta o conjunto de dados
     */
    private final String dataFile;
    /**
     *
     */
    private double split;
    /**
     * Matriz com o conjunto de dados para treino
     */
    private Matrix trainingSet;
    /**
     * Matriz com o conjunto de dados para testes
     */
    private Matrix testSet;
    /**
     * Matriz com o conjunto total de dados
     */
    private Matrix dataSet;
    /**
     * Classe auxiliar para converter Strings em numéricos
     */
    private final ConvertToNumericDummy ctd;

    /**
     *
     * @param dataFile
     */
    public KNN(final String dataFile) {
        this.dataFile = dataFile;
        ctd = new ConvertToNumericDummy();
    }

    /**
     *
     * @param dataFile
     * @param split
     */
    public KNN(final String dataFile, final double split) {
        this(dataFile);
        this.split = split;
    }

    /**
     *
     */
    public void loadDataSet() {
        String line;
        final String cvsSplitBy = ",";
        final List<String[]> tmpArray = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(dataFile))) {

            while ((line = br.readLine()) != null) {
                tmpArray.add(line.split(cvsSplitBy));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        final double[][] components = new double[tmpArray.size()][];
        for (int i = 0; i < tmpArray.size(); i++) {
            final String[] row = tmpArray.get(i);
            components[i] = new double[row.length];
            for (int j = 0; j < row.length - 1; j++) {
                components[i][j] = parseDouble(row[j]);
            }
            components[i][row.length - 1] = ctd.put(row[row.length - 1]);
        }
        dataSet = new Matrix(components);
        if (split != 0) {
            splitDataSet(dataSet);
        }
        log.info("ConvertToNumericDummy:\n" + ctd.toString());
    }

    /**
     *
     * @param dataSet
     */
    private void splitDataSet(Matrix dataSet) {
        final Matrix[] m = dataSet.trainTestSplit(split, true);
        trainingSet = m[0];
        testSet = m[1];
        log.info("TrainingSet:\n" + trainingSet.toString());
        log.info("TestSet:\n" + testSet.toString());
    }

    /**
     *
     * @param instance1
     * @param instance2
     * @param length
     * @return
     */
    public double euclideanDistance(final Vector instance1, final Vector instance2, final int length) {
        double distance = 0;
        for (int i = 0; i < length; i++) {
            distance += pow((instance1.component(i) - instance2.component(i)), 2);
        }
        return sqrt(distance);
    }

    /**
     *
     * @param dataSet
     * @param testInstance
     * @param k
     * @return
     */
    public Matrix getNeighbors(final Matrix dataSet, final Vector testInstance, int k) {
        final Map<Vector, Double> neighbors = new HashMap<>(dataSet.rows());
        final int length = testInstance.dimension() - 1;
        for (int i = 0; i < dataSet.rows(); i++) {
            final double dist = euclideanDistance(testInstance, dataSet.getRow(i), length);
            neighbors.put(dataSet.getRow(i), dist);
        }
        final Map<Vector, Double> neighborsSorted = sortByValue(neighbors, false);
        int i = 0;
        final Iterator<Entry<Vector, Double>> it = neighborsSorted.entrySet().iterator();
        double[][] components = new double[k][];
        while (it.hasNext() && i < k) {
            components[i] = it.next().getKey().toComponents();
            i++;
        }
        return new Matrix(components);
    }

    /**
     *
     * @param map
     * @return a sorted map by value
     */
    private <K, V extends Comparable<? super V>> Map<K, V> sortByValue(final Map<K, V> map, final boolean isReverse) {
        Stream<Entry<K, V>> s;
        if (isReverse) {
            s = map.entrySet()
                    .stream().
                    sorted(comparingByValue(reverseOrder()));
        } else {
            s = map.entrySet()
                    .stream()
                    .sorted(comparingByValue());
        }

        return s.collect(toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (e1, e2) -> e1,
                LinkedHashMap::new
        ));
    }

    /**
     *
     * @param neighbors
     * @return
     */
    public String getResponse(final Matrix neighbors) {
        final Map<String, Integer> classVotes = new HashMap<>();
        for (int i = 0; i < neighbors.rows(); i++) {
            final String label = ctd.getRealValue(neighbors.component(i, neighbors.columns() - 1));
            classVotes.put(label, classVotes.getOrDefault(label, 0) + 1);
        }
        return sortByValue(classVotes, true).entrySet()
                                            .iterator()
                                            .next()
                                            .getKey();
    }

    /**
     *
     * @param testSet
     * @param predictions
     * @return
     */
    public double getAccuracy(final Matrix testSet, final String[] predictions) {
        double correct = 0;
        for (int i = 0; i < testSet.rows(); i++) {
            if (ctd.getRealValue(testSet.component(i, testSet.columns() - 1)).equals(predictions[i])) {
                correct += 1;
            }
        }
        return (correct / (double) testSet.rows()) * 100.0;
    }

    public static void main(String[] args) {
        boolean isSplit = false;
        // With split data
        if (isSplit) {
            KNN knn = new KNN("/home/manuel/tools/adalineProcesses/mlearning/knn/iris.data", 0.66);
            knn.loadDataSet();
            int k = 3;
            List<String> results = new ArrayList<>();
            for (int i = 0; i < knn.testSet.rows(); i++) {
                Matrix neighbors = knn.getNeighbors(knn.trainingSet, knn.testSet.getRow(i), k);
                String result = knn.getResponse(neighbors);
                results.add(result);
                String actual = knn.ctd.getRealValue(knn.testSet.component(i, knn.testSet.columns() - 1));
                if (!result.equals(actual)) {
                    log.info("Fail!");
                }
                log.info("Predicted:" + result + "; Actual:" + actual);
            }
            double accuracy = knn.getAccuracy(knn.testSet, results.toArray(new String[results.size()]));
            log.info("Accuracy: " + accuracy);
        } else {
            KNN knn = new KNN("/home/manuel/tools/adalineProcesses/mlearning/knn/iris_MinusOne.data");
            knn.loadDataSet();
            int k = 3;
            Vector testVector = new Vector(new double[]{6.3, 3.3, 4.7, 1.6});
            Matrix neighbors = knn.getNeighbors(knn.dataSet, testVector, k);
            log.info("Neighbors:\n" + neighbors.toString());
            String result = knn.getResponse(neighbors);
            log.info("Predicted:" + result + "; Actual:" + knn.ctd.getRealValue(1.0));
        }

    }
}
