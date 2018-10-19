/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.machinelearning;

import org.apache.log4j.Logger;

import junit.framework.TestCase;
import static org.apache.log4j.Logger.getLogger;
import pt.mleiria.machinelearning.matrixAlgebra.Matrix;
import pt.mleiria.machinelearning.matrixAlgebra.Vector;
import pt.mleiria.machinelearning.preprocess.FeatNormMeanStdev;
import pt.mleiria.machinelearning.preprocess.FeatureNormalization;
import pt.mleiria.machinelearning.regression.LinearRegression;
import pt.mleiria.numericalAnalysis.utils.IOUtils;
import static pt.mleiria.numericalAnalysis.utils.ViewUtils.showArrayContents;
import pt.mleiria.utils.FileLoader;

/**
 *
 * @author manuel
 */
public class GradientDescentTest extends TestCase {

    private static final Logger mlearningLog = getLogger("mlearningLog");
    private Matrix a;
    private Matrix aMulti;
    private Matrix featuresX;
    private Vector outputY;
    private IOUtils iou = new IOUtils();

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        String separator = ",";
        try {
            FileLoader fl = new FileLoader("input/ex1data1.txt");
            a = fl.getFileCsvToMatrix(true, separator);
            mlearningLog.info("EX1DATA1:\n" + a.toString());

            fl = new FileLoader("input/ex1data2.txt");
            aMulti = fl.getFileCsvToMatrix(true, separator);
            mlearningLog.info("EX1DATA2:\n" + aMulti.toString());

            Matrix[] splitedM = aMulti.split(1);
            featuresX = splitedM[0];
            outputY = splitedM[1].getColumn(0);

        } catch (Exception ex) {
            mlearningLog.error(ex);
        }

    }

    /**
     *
     */
    public void testFeatureNormalization() {
        final FeatNormMeanStdev ftn = new FeatNormMeanStdev();
        final Matrix normalizedM = ftn.normalize(featuresX);
        mlearningLog.info("Mu " + showArrayContents(ftn.getMean()));
        mlearningLog.info("Sigma " + showArrayContents(ftn.getStdev()));
        assertEquals(2000.6808510638298, ftn.getMean()[0]);
        assertEquals(3.1702127659574466, ftn.getMean()[1]);
        assertEquals(0.13000986907454057, normalizedM.component(0, 0));
    }

    /**
     *
     */
    public void testGradientDescentMulti() {
        mlearningLog.info("START Gradient Descent Multi...");
        double alpha = 0.01;
        int numIter = 400;
        double precision = 0.00001;
        //Normalize featuresX
        final FeatureNormalization ftn = new FeatNormMeanStdev();
        final Matrix featuresXNorm = ftn.normalize(this.featuresX);
        mlearningLog.info("Normalized Matrix (10 rows) \n" + featuresXNorm.toString(10));

        LinearRegression gd = new LinearRegression(featuresXNorm, outputY, alpha);
        gd.setMaximumIterations(numIter);
        gd.setDesiredPrecision(precision);
        gd.evaluate();
        iou.saveArrayToFile("src/test/resources/output/JGDM.txt", gd.getCostHistory(), "JGM", "NUMERIC");
        mlearningLog.info("Cost History \n" + showArrayContents(gd.getCostHistory()));
        assertEquals(334302.06399327697, gd.getTheta().component(0));
        assertEquals(100087.11600584642, gd.getTheta().component(1));
        assertEquals(3673.548450928262, gd.getTheta().component(2));
        mlearningLog.info("END Gradient Descent...");

    }

    /**
     *
     */
    public void testGradientDescent() {
        double alpha = 0.01;
        int numIter = 1500;
        double precision = 0.00001;
        final Matrix[] splitedM = a.split(1);
        final Matrix featuresX = splitedM[0];
        final Vector outputY = splitedM[1].getColumn(0);

        LinearRegression gd = new LinearRegression(featuresX, outputY, alpha);
        gd.setMaximumIterations(numIter);
        gd.setDesiredPrecision(precision);
        gd.evaluate();
        final double[] costH = gd.getCostHistory();
        iou.saveArrayToFile("src/test/resources/output/JGD.txt", costH, "JG", "NUMERIC");
        assertEquals(32.072733877455654, costH[0]);
        assertEquals(-3.63029143940436, gd.getTheta().component(0));
        assertEquals(1.166362350335582, gd.getTheta().component(1));

        double expected = 4519.7678677017675;
        double[][] predict = new double[1][2];
        predict[0][0] = 1;
        predict[0][1] = 3.5;
        Matrix m = new Matrix(predict);
        mlearningLog.info("Final Theta " + gd.getTheta().toString());
        mlearningLog.info("Prediction Matrix \n" + m.toString());
        assertEquals(expected, m.product(gd.getTheta()).component(0) * 10000.0);
    }

}
