/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.machinelearning;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.apache.log4j.Logger;
import static org.apache.log4j.Logger.getLogger;
import pt.mleiria.machinelearning.functions.Log;
import pt.mleiria.machinelearning.functions.Sigmoid;
import pt.mleiria.machinelearning.interfaces.OneVarFunction;
import pt.mleiria.machinelearning.matrixAlgebra.Matrix;
import pt.mleiria.machinelearning.matrixAlgebra.MatrixUtils;
import static pt.mleiria.machinelearning.matrixAlgebra.MatrixUtils.toVector;
import pt.mleiria.machinelearning.matrixAlgebra.Vector;
import pt.mleiria.utils.FileLoader;

/**
 *
 * @author manuel
 */
public class LogisticRegressionTests extends TestCase {

    private static final Logger LOG = getLogger("mlearningLog");

    private Matrix a;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        String separator = ",";
        FileLoader fl = new FileLoader("input/studentsGrade.txt");
        a = fl.getFileCsvToMatrix(true, separator);
        LOG.info("Students Data:" + a.toString());
    }
    
    public void testSplit(){
        final Matrix[] split = a.split(1);
        final Matrix featuresX = split[0];
        final Vector outputY = toVector(split[1]);
        LOG.info("OutputY:" + outputY.toString());
        assertEquals(a.rows(), outputY.dimension());
    }
    
    public void testSigmoid(){
        final double[][] components = new double[2][2];
        components[0][0] = 1;
        components[0][1] = 2;
        components[1][0] = 3;
        components[1][1] = 4;
        a = new Matrix(components);
        LOG.info("\n" + a.toString());
        OneVarFunction<Matrix> sigmoid = new Sigmoid();
        Matrix sigmoidMatrixRes = sigmoid.value(a); 
        LOG.info("Sigmoid:" + sigmoidMatrixRes.toString());
        assertEquals(a.rows(), sigmoidMatrixRes.rows());
        assertEquals(a.columns(), sigmoidMatrixRes.columns());
        final double sigmoid2 = 0.8807970779778823;
        final double sigmoid1 = 0.7310585786300049;
        assertEquals(sigmoid1, sigmoidMatrixRes.component(0, 0));
        assertEquals(sigmoid2, sigmoidMatrixRes.component(0, 1));
    }
    public void testLogFunction(){
        final double[][] components = new double[2][2];
        components[0][0] = 1.5;
        components[0][1] = 2.3;
        components[1][0] = 3;
        components[1][1] = 4;
        a = new Matrix(components);
        LOG.info("\n" + a.toString());
        OneVarFunction<Matrix> log = new Log();
        Matrix LogMatrixRes = log.value(a); 
        LOG.info("Log:" + LogMatrixRes.toString());
        assertEquals(a.rows(), LogMatrixRes.rows());
        assertEquals(a.columns(), LogMatrixRes.columns());
        final double log1 = 0.4054651081081644;
        assertEquals(log1, LogMatrixRes.component(0, 0));
    }
}
