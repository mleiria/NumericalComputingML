/**
 *
 */
package pt.mleiria.machinelearning;

import java.io.IOException;
import java.util.logging.Level;
import org.apache.log4j.Logger;

import junit.framework.TestCase;
import pt.mleiria.machinelearning.classification.KNN;
import pt.mleiria.machinelearning.matrixAlgebra.Matrix;
import pt.mleiria.machinelearning.matrixAlgebra.Vector;
import static org.apache.log4j.Logger.getLogger;
import pt.mleiria.machinelearning.matrixAlgebra.MatrixUtils;
import pt.mleiria.machinelearning.preprocess.ConvertToNumericDummy;
import pt.mleiria.machinelearning.statistics.DistanceMetric;
import pt.mleiria.machinelearning.statistics.EuclideanDistance;
import pt.mleiria.numericalAnalysis.utils.IOUtils;
import pt.mleiria.numericalAnalysis.utils.StopWatch;

/**
 * @author manuel
 *
 */
public class KnnTest extends TestCase {

    /**
     * Logger
     */
    private static final Logger LOG = getLogger("mlearningLog");

    private final String dataFileIris = "/home/manuel/tools/adalineProcesses/mlearning/knn/iris_MinusOne.data";
    private final String dataFileBank = "/home/manuel/tools/adalineProcesses/mlearning/knn/bank.data";
    private final String dataFileBankTest = "/home/manuel/tools/adalineProcesses/mlearning/knn/bank.test";

    private ConvertToNumericDummy converter;
    private Matrix dataSet;

    @Override
    protected void setUp() {
        
    }

    public void testClassifySerialBankDataSet() {
        try {
            converter = new ConvertToNumericDummy();
            double[][] components = IOUtils.loadFileToComponentsWithLabelConversion(dataFileBank, ";", converter);
            dataSet = new Matrix(components);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(KnnTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals(39129, dataSet.rows());
        //assertEquals(5, dataSet.columns());
        assertEquals(2, converter.getSize());
        
        KNN knn = new KNN(dataSet, 3);
        Matrix testDataSet = loadBankTestSet();
        Matrix[] tmp = testDataSet.split(1);
        Matrix testFeaturesX = tmp[0];
        Vector testOutputY = MatrixUtils.toVector(tmp[1]);
        
        final DistanceMetric dm = new EuclideanDistance();
        final Vector example = testFeaturesX.getRow(0);
        final StopWatch sw = new StopWatch();
        final double res = knn.classifySerial(example, dm);
        assertEquals(testOutputY.component(0), res);
        LOG.info("Serial Bank DataSet predicted:" + converter.getRealValue(res) + " in " + sw.elapsedTime() + " secs");
        LOG.info("Serial Bank DataSet real:" + converter.getRealValue(testOutputY.component(0)));
    }
    
    public void testClassifyParallelBankDataSet() throws Exception{
        try {
            converter = new ConvertToNumericDummy();
            double[][] components = IOUtils.loadFileToComponentsWithLabelConversion(dataFileBank, ";", converter);
            dataSet = new Matrix(components);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(KnnTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals(39129, dataSet.rows());
        //assertEquals(5, dataSet.columns());
        assertEquals(2, converter.getSize());
        
        KNN knn = new KNN(dataSet, 3);
        Matrix testDataSet = loadBankTestSet();
        Matrix[] tmp = testDataSet.split(1);
        Matrix testFeaturesX = tmp[0];
        Vector testOutputY = MatrixUtils.toVector(tmp[1]);
        
        final DistanceMetric dm = new EuclideanDistance();
        final Vector example = testFeaturesX.getRow(0);
        final StopWatch sw = new StopWatch();
        final double res = knn.classifyParallel(example, dm, 1);
        assertEquals(testOutputY.component(0), res);
        LOG.info("Parallel Bank DataSet predicted:" + converter.getRealValue(res) + " in " + sw.elapsedTime() + " secs");
        LOG.info("Parallel Bank DataSet real:" + converter.getRealValue(testOutputY.component(0)));
    }
    
    private Matrix loadBankTestSet(){
        Matrix testDataSet = null;
        try {
            ConvertToNumericDummy testConverter = new ConvertToNumericDummy();
            double[][] components = IOUtils.loadFileToComponentsWithLabelConversion(dataFileBankTest, ";", testConverter);
            testDataSet = new Matrix(components);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(KnnTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return testDataSet;
    }
    
    public void testClassifySerialIrisDataSet() {
        try {
            converter = new ConvertToNumericDummy();
            final double[][] components = IOUtils.loadFileToComponentsWithLabelConversion(dataFileIris, ",", converter);
            dataSet = new Matrix(components);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(KnnTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals(149, dataSet.rows());
        assertEquals(5, dataSet.columns());
        assertEquals(3, converter.getSize());
        
        KNN knn = new KNN(dataSet, 3);
        //5.9,3.0,5.1,1.8,Iris-virginica
        Vector example = new Vector(new double[]{5.9, 3.0, 5.1, 1.8});
        final DistanceMetric dm = new EuclideanDistance();
        double res = knn.classifySerial(example, dm);
        assertEquals("Iris-virginica", converter.getRealValue(res));
        LOG.info("Iris DataSet predicted:" + converter.getRealValue(res));
    }

    /**
     *
     */
    public void testEuclideanDistance() {
        double expectedDistance = 3.605551275463989;
        Vector v1 = new Vector(new double[]{2, 2, 2, 0});
        Vector v2 = new Vector(new double[]{4, 4, 4, 1});
        double distance = new EuclideanDistance().getRelation(v1, v2, v1.dimension());

        assertEquals(expectedDistance, distance);
    }

}
