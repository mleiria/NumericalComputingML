/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.matrixAlgebra;

import junit.framework.TestCase;
import org.apache.log4j.Logger;
import static org.apache.log4j.Logger.getLogger;
import pt.mleiria.machinelearning.interfaces.OneVarFunction;

import pt.mleiria.machinelearning.matrixAlgebra.Matrix;
import static pt.mleiria.machinelearning.matrixAlgebra.MatrixUtils.identity;
import pt.mleiria.machinelearning.matrixAlgebra.Vector;
import pt.mleiria.machinelearning.preprocess.FeatNormMeanStdev;
import pt.mleiria.machinelearning.preprocess.FeatNormMinMax;

/**
 *
 * @author manuel
 */
public class MatrixTest extends TestCase {

    /**
     * Logger
     */
    private static final Logger log = getLogger("mlearningLog");
    private Matrix a;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        final double[][] components = new double[2][2];
        components[0][0] = 1;
        components[0][1] = 2;
        components[1][0] = 3;
        components[1][1] = 4;
        a = new Matrix(components);
        log.info("\n" + a.toString());
    }

    public void testGetVectorFromMatrix(){
    	Vector v = a.getRow(0);
    	log.info("Vector v:" + v.toString());
    	assertEquals(1.0, v.component(0));
    	assertEquals(2.0, v.component(1));
    }
    
    public void testMatrixAddition() {
        double[][] components = new double[2][2];
        components[0][0] = 1;
        components[0][1] = 2;
        components[1][0] = 3;
        components[1][1] = 4;
        Matrix b = new Matrix(components);

        Matrix c = a.add(b);
        assertEquals(2.0, c.component(0, 0));
        assertEquals(4.0, c.component(0, 1));
        assertEquals(6.0, c.component(1, 0));
        assertEquals(8.0, c.component(1, 1));
        log.info("\n" + c.toString());
    }

    public void testMatrixSubtraction() {
        double[][] components = new double[2][2];
        components[0][0] = 1;
        components[0][1] = 1;
        components[1][0] = 1;
        components[1][1] = 2;
        Matrix b = new Matrix(components);
        Matrix c = a.subtract(b);
        assertEquals(0.0, c.component(0, 0));
        assertEquals(1.0, c.component(0, 1));
        assertEquals(2.0, c.component(1, 0));
        assertEquals(2.0, c.component(1, 1));
        Matrix d = new Matrix(c.subtract(b).toComponents());
        assertEquals(-1.0, d.component(0, 0));
        assertEquals(0.0, d.component(0, 1));
        assertEquals(1.0, d.component(1, 0));
        assertEquals(0.0, d.component(1, 1));

    }

    public void testMatrixMultiplyScalar() {
        double x = 5.0;
        Matrix b = a.multiply(x);
        assertEquals(5.0, b.component(0, 0));
        assertEquals(10.0, b.component(0, 1));
        assertEquals(15.0, b.component(1, 0));
        assertEquals(20.0, b.component(1, 1));
        log.info("\n" + b.toString());
    }

    public void testMatrixMultiply() {
        double[][] components = new double[2][2];
        components[0][0] = 5;
        components[0][1] = 6;
        components[1][0] = 7;
        components[1][1] = 8;
        Matrix b = new Matrix(components);
        Matrix c = a.multiply(b);
        assertEquals(19.0, c.component(0, 0));
        assertEquals(22.0, c.component(0, 1));
        assertEquals(43.0, c.component(1, 0));
        assertEquals(50.0, c.component(1, 1));
        log.info("\n" + c.toString());
    }

    public void testMatrixIdentity() {
        double[][] components = new double[2][2];
        components[0][0] = 1;
        components[0][1] = 0;
        components[1][0] = 0;
        components[1][1] = 1;
        Matrix aa = new Matrix(components);
        Matrix b = identity(2, 2);
        assertEquals(aa.component(0, 0), b.component(0, 0));
        assertEquals(aa.component(0, 1), b.component(0, 1));
        assertEquals(aa.component(1, 0), b.component(1, 0));
        assertEquals(aa.component(1, 1), b.component(1, 1));
        log.info("Eye matrix: \n" + aa.toString());
    }

    public void testMatrixTranspose() {
        double[][] components = new double[4][4];
        components[0][0] = 16;
        components[0][1] = 2;
        components[0][2] = 3;
        components[0][3] = 13;
        components[1][0] = 5;
        components[1][1] = 11;
        components[1][2] = 10;
        components[1][3] = 8;
        components[2][0] = 9;
        components[2][1] = 7;
        components[2][2] = 6;
        components[2][3] = 12;
        components[3][0] = 4;
        components[3][1] = 14;
        components[3][2] = 15;
        components[3][3] = 1;
        Matrix a = new Matrix(components);
        log.info("Matrix a: \n" + a.toString());
        Matrix b = a.transpose();
        double[][] componentsTranspose = new double[4][4];
        componentsTranspose[0][0] = 16;
        componentsTranspose[0][1] = 5;
        componentsTranspose[0][2] = 9;
        componentsTranspose[0][3] = 4;
        componentsTranspose[1][0] = 2;
        componentsTranspose[1][1] = 11;
        componentsTranspose[1][2] = 7;
        componentsTranspose[1][3] = 14;
        componentsTranspose[2][0] = 3;
        componentsTranspose[2][1] = 10;
        componentsTranspose[2][2] = 6;
        componentsTranspose[2][3] = 15;
        componentsTranspose[3][0] = 13;
        componentsTranspose[3][1] = 8;
        componentsTranspose[3][2] = 12;
        componentsTranspose[3][3] = 1;
        Matrix c = new Matrix(componentsTranspose);
        log.info("Matrix a transposed: \n" + b.toString());
        assertFalse(!b.equals(c));
    }

    public void testFeatureNormalizationMeanStdev() {
        double[][] comps = new double[5][2];
        comps[0][0] = 2104;
        comps[1][0] = 1600;
        comps[2][0] = 2400;
        comps[3][0] = 1416;
        comps[4][0] = 3000;
        comps[0][1] = 3;
        comps[1][1] = 3;
        comps[2][1] = 3;
        comps[3][1] = 2;
        comps[4][1] = 4;
        Matrix a = new Matrix(comps);
        log.info("Matrix To Normalize: \n" + a.toString());
        FeatNormMeanStdev fn = new FeatNormMeanStdev();
        Matrix normalizedMatrix = fn.normalize(a);
        log.info("Matrix Normalized: \n" + normalizedMatrix.toString());
        log.info("Mean Column 0: " + fn.getMean()[0]);
        log.info("Mean Column 1: " + fn.getMean()[1]);
        log.info("Stdev Column 0: " + fn.getStdev()[0]);
        log.info("Stdev Column 1: " + fn.getStdev()[1]);
        assertEquals(2104.0, fn.getMean()[0]);
        assertEquals(3.0, fn.getMean()[1]);
    }

    public void testFeatureNormalizationMinMax() {
        double[][] comps = new double[5][2];
        comps[0][0] = 2104;
        comps[1][0] = 1600;
        comps[2][0] = 2400;
        comps[3][0] = 1416;
        comps[4][0] = 3000;
        comps[0][1] = 3;
        comps[1][1] = 3;
        comps[2][1] = 3;
        comps[3][1] = 2;
        comps[4][1] = 4;
        Matrix a = new Matrix(comps);
        log.info("Matrix To Normalize: \n" + a.toString());
        FeatNormMinMax fn = new FeatNormMinMax();
        Matrix normalizedMatrix = fn.normalize(a);
        log.info("Matrix Normalized: \n" + normalizedMatrix.toString());
        assertEquals(0.43434343434343436, normalizedMatrix.component(0, 0));
        assertEquals(0.5, normalizedMatrix.component(0, 1));
    }

    public void testMatrixSplit() {
        Matrix[] result = a.split(1);
        log.info("Matrix To Split: \n" + a.toString());
        log.info("Matrix[0]: \n" + result[0].toString());
        log.info("Matrix[1]: \n" + result[1].toString());
        assertEquals(1.0, result[0].component(0, 0));
        assertEquals(2.0, result[1].component(0, 0));
    }

    public void testTrainTestSplit() {
        double[][] components = new double[4][4];
        components[0][0] = 16;
        components[0][1] = 2;
        components[0][2] = 3;
        components[0][3] = 13;
        components[1][0] = 5;
        components[1][1] = 11;
        components[1][2] = 10;
        components[1][3] = 8;
        components[2][0] = 9;
        components[2][1] = 7;
        components[2][2] = 6;
        components[2][3] = 12;
        components[3][0] = 4;
        components[3][1] = 14;
        components[3][2] = 15;
        components[3][3] = 1;
        Matrix a = new Matrix(components);
        Matrix[] trainTest = a.trainTestSplit(0.9, false);
        Matrix train = trainTest[0];
        Matrix test = trainTest[1];
        log.info("Matrix To Train Test Split: \n" + a.toString());
        log.info("Train Matrix: \n" + train.toString());
        log.info("Test Matrix: \n" + test.toString());
        assertEquals(a.columns(), train.columns());
        assertEquals(a.columns(), test.columns());
        assertEquals(3, train.rows());
        assertEquals(1, test.rows());
    }

    public void testMatrixProductVector() {
        double[][] m = new double[2][2];
        m[0][0] = 1;
        m[1][0] = 3;
        m[0][1] = 2;
        m[1][1] = 4;
        Matrix ma = new Matrix(m);
        double[] n = new double[]{5, 6};
        Vector v = new Vector(n);
        Vector c = ma.product(v);
        assertEquals(2, c.dimension());
        assertEquals(17.0, c.component(0));
        assertEquals(39.0, c.component(1));
    }
    
    public void testAppendColumn(){
    	final Vector v = new Vector(new double[]{5.0,5.0});
    	final Matrix m = a.append(v);
    	log.info("Appended Matrix: \n" + m.toString());
    	assertEquals(3, m.columns());
    	assertEquals(5.0, m.component(0, 2));
    }
    
    public void testPolynomialOrder(){
	double[][] m = new double[2][2];
        m[0][0] = 1;
        m[1][0] = 1;
        m[0][1] = 2;
        m[1][1] = 2;
        Matrix ma = new Matrix(m);
        Matrix augmentedMatrix = ma.polynomialOrder(2);
        assertEquals(3, augmentedMatrix.columns());
        assertEquals(4.0, augmentedMatrix.component(1, 2));
    }
    
    public void testLambdaFunction(){
        OneVarFunction<Double> testFunc = elem -> elem * 2;
        assertEquals(4.0, getLambdaValue(2.0, elem -> elem * 2));
    }
    
    private Double getLambdaValue(Double d, OneVarFunction<Double> f){
        return f.value(d);
    }

}
