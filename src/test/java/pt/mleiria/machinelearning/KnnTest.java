/**
 *
 */
package pt.mleiria.machinelearning;

import org.apache.log4j.Logger;

import junit.framework.Assert;
import junit.framework.TestCase;
import static org.apache.log4j.Logger.getLogger;
import pt.mleiria.machinelearning.classification.KNN;
import pt.mleiria.machinelearning.matrixAlgebra.Matrix;
import pt.mleiria.machinelearning.matrixAlgebra.Vector;
import static org.apache.log4j.Logger.getLogger;

/**
 * @author manuel
 *
 */
public class KnnTest extends TestCase {

    /**
     * Logger
     */
    private static final Logger LOG = getLogger("mlearningLog");

    private final String dataFile = "/home/manuel/tools/adalineProcesses/mlearning/knn/iris.data";
    private KNN knn;

    @Override
    protected void setUp() {
        knn = new KNN(dataFile);
        knn.loadDataSet();
    }

    /**
     *
     */
    public void testEuclideanDistance() {
        double expectedDistance = 3.4641016151377544;
        Vector v1 = new Vector(new double[]{2, 2, 2, 0});
        Vector v2 = new Vector(new double[]{4, 4, 4, 1});
        double distance = knn.euclideanDistance(v1, v2, 3);

        assertEquals(expectedDistance, distance);
    }

    /**
     *
     */
    public void testNeighbors() {
        double[][] trainSetComp = new double[][]{{2, 2, 2, 0}, {4, 4, 4, 1}};
        double[] testInstanceComp = new double[]{5, 5, 5};
        Matrix trainSet = new Matrix(trainSetComp);
        Vector testInstance = new Vector(testInstanceComp);
        Matrix res = knn.getNeighbors(trainSet, testInstance, 1);

        LOG.info("Neighbors:" + res.toString());
        assertEquals(4.0, res.component(0, 0));
        assertEquals(4.0, res.component(0, 1));
        assertEquals(4.0, res.component(0, 1));
        assertEquals(1.0, res.component(0, 3));
    }

    /**
     *
     */
    public void testGetResponse() {
        double[][] components = new double[][]{{1, 1, 1, 0}, {2, 2, 2, 0}, {3, 3, 3, 1}};
        Matrix neighbors = new Matrix(components);
        String response = knn.getResponse(neighbors);
        LOG.info("Voted Class:" + response);
        assertEquals("Iris-setosa", response);
    }

}
