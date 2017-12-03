/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.machinelearning;

import static junit.framework.Assert.assertEquals;
import junit.framework.TestCase;
import org.apache.log4j.Logger;
import static org.apache.log4j.Logger.getLogger;
import pt.mleiria.machinelearning.classification.LogisticRegression;
import pt.mleiria.machinelearning.matrixAlgebra.Matrix;
import pt.mleiria.machinelearning.matrixAlgebra.Vector;
import pt.mleiria.machinelearning.preprocess.FeatNormMeanStdev;
import pt.mleiria.numericalAnalysis.utils.IOUtils;
import pt.mleiria.utils.FileLoader;

/**
 *
 * @author Manuel Leiria <manuel.leiria at gmail.com>
 */
public class LogisticRegressionTest extends TestCase {

    private static final Logger LOG = getLogger("mlearningLog");

    private Matrix featuresX;
    private Vector outputY;
    private IOUtils iou = new IOUtils();

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        String separator = ",";
        try {
            FileLoader fl = new FileLoader("input/studentsGrade.txt");
            final Matrix a = fl.getFileCsvToMatrix(true, separator);
            LOG.info("Students Grade:\n" + a.toString());

            Matrix[] splitedM = a.split(1);
            featuresX = splitedM[0];
            outputY = splitedM[1].getColumn(0);

        } catch (Exception ex) {
            LOG.error(ex);
        }
    }
    /**
     * 
     */
    public void testGradientDescent() {
        double alpha = 0.001;
        int numIter = 1;
        double precision = 0.00001;
        //System.out.println(featuresX.toString());
        //System.out.println(outputY.toString());
        
        //final FeatNormMeanStdev ftn = new FeatNormMeanStdev();
        //final Matrix normalizedM = ftn.normalize(featuresX);
        //System.out.println(normalizedM.toString());
        
        LogisticRegression gd = new LogisticRegression(featuresX, outputY, alpha);
        gd.setMaximumIterations(numIter);
        gd.setDesiredPrecision(precision);
        gd.evaluate();
        final double[] costH = gd.getCostHistory();
        iou.saveArrayToFile("src/test/resources/output/JGD_LG.txt", costH, "JG", "NUMERIC");
        assertEquals(32.072733877455654, costH[0]);
        assertEquals(-3.63029143940436, gd.getTheta().component(0));
        assertEquals(1.166362350335582, gd.getTheta().component(1));

        double expected = 4519.7678677017675;
        double[][] predict = new double[1][2];
        predict[0][0] = 1;
        predict[0][1] = 3.5;
        Matrix m = new Matrix(predict);
        LOG.info("Final Theta " + gd.getTheta().toString());
        LOG.info("Prediction Matrix \n" + m.toString());
        assertEquals(expected, m.product(gd.getTheta()).component(0) * 10000.0);
    }

}
