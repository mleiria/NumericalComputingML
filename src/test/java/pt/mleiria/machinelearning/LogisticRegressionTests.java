/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.machinelearning;

import junit.framework.TestCase;
import org.apache.log4j.Logger;
import static org.apache.log4j.Logger.getLogger;
import pt.mleiria.machinelearning.classification.LogisticRegression_OLD;
import pt.mleiria.machinelearning.functions.matrix.Log;
import pt.mleiria.machinelearning.functions.matrix.Sigmoid;
import pt.mleiria.machinelearning.interfaces.OneVarFunction;
import pt.mleiria.machinelearning.matrixAlgebra.Matrix;
import pt.mleiria.machinelearning.matrixAlgebra.MatrixUtils;
import pt.mleiria.machinelearning.matrixAlgebra.Vector;
import pt.mleiria.utils.FileLoader;

/**
 *
 * @author manuel
 */
public class LogisticRegressionTests extends TestCase {

    private static final Logger LOG = getLogger("mlearningLog");

    private Matrix X;
    private Vector outputY;
    

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        final String separator = ",";
        FileLoader fl = new FileLoader("input/train.txt");
        X = fl.getFileCsvToMatrix(true, separator);
        LOG.info("Synthetic Data Input:" + X.toString());
        fl = new FileLoader("input/target.txt");
        outputY = fl.loadFileToVector();
        LOG.info("Synthetic Data Output:" + outputY.toString());

    }
    
    /**
     * 
     */
    public void testSigmoid(){
        final double[][] components = new double[2][2];
        components[0][0] = 1;
        components[0][1] = 2;
        components[1][0] = 3;
        components[1][1] = 4;
        final Matrix a = new Matrix(components);
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
    /**
     * 
     */
    public void testLogFunction(){
        final double[][] components = new double[2][2];
        components[0][0] = 1.5;
        components[0][1] = 2.3;
        components[1][0] = 3;
        components[1][1] = 4;
        final Matrix a = new Matrix(components);
        LOG.info("\n" + a.toString());
        OneVarFunction<Matrix> log = new Log();
        Matrix LogMatrixRes = log.value(a); 
        LOG.info("Log:" + LogMatrixRes.toString());
        assertEquals(a.rows(), LogMatrixRes.rows());
        assertEquals(a.columns(), LogMatrixRes.columns());
        final double log1 = 0.4054651081081644;
        assertEquals(log1, LogMatrixRes.component(0, 0));
    }
    
    public void testLogRegression(){
        //Expand Matrix to quadratic terms
        final Matrix expandedMatrix = MatrixUtils.expand(X);
        assertEquals(5, expandedMatrix.columns());
        //Append a column of ones
        final Matrix featuresX = expandedMatrix.append(MatrixUtils.Ones(expandedMatrix.rows()));
        assertEquals(6, featuresX.columns());
        assertEquals(1.0, featuresX.component(0, featuresX.columns() - 1));
        assertEquals(1.49281180493, featuresX.component(featuresX.rows() - 1, 0));
        assertEquals(1.7269350793864087, featuresX.component(featuresX.rows() -1, featuresX.columns() - 2));
        final Vector sampleW = new Vector(new double[]{-1. , -0.6, -0.2,  0.2,  0.6,  1. });
        
        //Vector sampleW = new Vector(new double[]{0. , 0., 0.,  0.,  0.,  1. });
        LogisticRegression_OLD lr = new LogisticRegression_OLD(featuresX, outputY, 0.1, sampleW);
        System.out.println("prob"+lr.probability().toString());
        assertEquals(0.380399850984, lr.probability().component(0, 0));
        lr.setAlpha(0.1);
        lr.setMaximumIterations(100);
        //lr.evaluate();
        System.out.println(new Vector(lr.getCostHistory()).toString());
        assertEquals(0.6934069659685858, lr.getCostHistory()[0]);
        
        
    }
}
