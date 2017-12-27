/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.machinelearning.classification;

import dummy.LinearClassifier;
import java.io.IOException;
import pt.mleiria.machinelearning.matrixAlgebra.Matrix;
import pt.mleiria.machinelearning.matrixAlgebra.Vector;
import static java.lang.System.out;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import pt.mleiria.machinelearning.functions.Sigmoid;
import pt.mleiria.machinelearning.functions.Log;
import pt.mleiria.machinelearning.matrixAlgebra.MatrixUtils;
import pt.mleiria.numericalAnalysis.utils.IOUtils;
import pt.mleiria.numericalAnalysis.utils.ViewUtils;
/**
 *
 * @author Manuel Leiria <manuel.leiria at gmail.com>
 */
public class LRTest {
    private static final String PATH = "/media/manuel/workspace/data/";
    private static final String INPUT_FILE = PATH + "train.txt";
    private static final String TARGET_FILE = PATH + "target.txt";
    private static final String SEPARATOR = ",";
    private Matrix X;
    private Vector outputY;
    
    public static void main(String[] args){
        LRTest lrt = new LRTest();
        final Matrix featuresX = lrt.expand(lrt.X);
        final Vector w = new Vector(new double[]{0 , 0, 0,  0,  0,  1. });
        final LogisticRegression lr = new LogisticRegression(featuresX, lrt.outputY, 0.1, w);
        lr.setDesiredPrecision(0.01);
        lr.setMaximumIterations(100);
        lr.evaluate();
        out.println("Cost.\n"+ViewUtils.showArrayContents(lr.getCostHistory()));
        
    }

    public LRTest() {
        try {
            final double[][] x = IOUtils.loadFileToComponents(INPUT_FILE, SEPARATOR);
            X = new Matrix(x);
            final Double[] y = IOUtils.loadFileToDoubleArray(TARGET_FILE);
            outputY = new Vector(Stream.of(y).mapToDouble(Double::doubleValue).toArray());
        } catch (IOException ex) {
            Logger.getLogger(LinearClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Matrix expand(final Matrix m){
        final double[][] components = MatrixUtils.expand(m, 5).toComponents();
        for(int i = 0; i < m.rows(); i++){
            components[i][2] = Math.pow(components[i][0], 2);
            components[i][3] = Math.pow(components[i][1], 2);
            components[i][4] = components[i][0] * components[i][1];
            //components[i][5] = 1;
        }
        return new Matrix(components);
    }
    
}
