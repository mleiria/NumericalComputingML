/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dummy;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import static java.lang.System.out;
import pt.mleiria.machinelearning.functions.matrix.Log;
import pt.mleiria.machinelearning.functions.matrix.Sigmoid;
import pt.mleiria.machinelearning.matrixAlgebra.Matrix;
import pt.mleiria.machinelearning.matrixAlgebra.MatrixUtils;
import pt.mleiria.machinelearning.matrixAlgebra.Vector;
import pt.mleiria.numericalAnalysis.utils.IOUtils;

/**
 *
 * @author manuel
 */
public class LinearClassifier {
    private static final String PATH = "/media/manuel/workspace/data/";
    private static final String INPUT_FILE = PATH + "train.txt";
    private static final String TARGET_FILE = PATH + "target.txt";
    private static final String SEPARATOR = ",";
    private Matrix X;
    private Vector Y;
    
    public static void main(String[] args){
        final LinearClassifier lc = new LinearClassifier();
        final Matrix expandedM = lc.expand();
        //out.println("eM:"+expandedM);
        Vector sampleW = new Vector(new double[]{-1. , -0.6, -0.2,  0.2,  0.6,  1. });
        //Vector sampleW = new Vector(new double[]{0. , 0., 0.,  0.,  0.,  1. });
            
        final Matrix resM = lc.probability(expandedM, sampleW);
        
        out.println(resM.toString());
        out.println("Loss:"+lc.computeLoss(expandedM, lc.Y, sampleW));
        //lc.computeGrad(expandedM, sampleW);
    }

    public LinearClassifier() {
        try {
            final double[][] x = IOUtils.loadFileToComponents(INPUT_FILE, SEPARATOR);
            X = new Matrix(x);
            final Double[] y = IOUtils.loadFileToDoubleArray(TARGET_FILE);
            Y = new Vector(Stream.of(y).mapToDouble(Double::doubleValue).toArray());
        } catch (IOException ex) {
            Logger.getLogger(LinearClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Matrix expand(){
        final double[][] components = MatrixUtils.expand(X, 6).toComponents();
        for(int i = 0; i < X.rows(); i++){
            components[i][2] = Math.pow(components[i][0], 2);
            components[i][3] = Math.pow(components[i][1], 2);
            components[i][4] = components[i][0] * components[i][1];
            components[i][5] = 1;
        }
        return new Matrix(components);
    }
    
    public Matrix probability(final Matrix m, final Vector w){
        final Vector theta = m.product(w);
        final Matrix res = new Sigmoid().value(new Matrix(theta));
        return res;
    }
    
    public double computeLoss(Matrix m, Vector y, Vector w){
        final double coeff = -1. / y.dimension();
        final Matrix probM = probability(m, w);
        out.println(probM);
        final Matrix logM = new Log().value(probM);
        final Vector a = logM.transpose().product(y);
        Matrix onesM = MatrixUtils.Ones(y.dimension(), 1);
        final Vector ones = MatrixUtils.toVector(onesM);
        
        onesM = MatrixUtils.Ones(probM.rows(), probM.columns());
        final Vector b = ones.subtract(y);
        
        final Matrix c = onesM.subtract(probM);
        final Matrix d = new Log().value(c).transpose();
        final Vector e = d.product(b);
        return coeff * a.add(e).component(0);
    }
    
    public Vector computeGrad(final Matrix m, final Vector w){
        final Vector resM = MatrixUtils.toVector(probability(m, w));
        final Vector a = resM.subtract(Y);
        //out.println(a.toString());
        final Vector b = m.transpose().product(a).product(1.0 / Y.dimension());
        //out.println(b.toString());
        out.println("bNorm:"+b.norm());
        return b;
    }
}
