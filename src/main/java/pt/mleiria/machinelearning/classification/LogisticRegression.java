/**
 *
 */
package pt.mleiria.machinelearning.classification;

import static java.lang.Math.abs;
import static java.lang.System.out;
import pt.mleiria.machinelearning.functions.Sigmoid;
import pt.mleiria.machinelearning.matrixAlgebra.Matrix;
import pt.mleiria.machinelearning.matrixAlgebra.Vector;
import pt.mleiria.machinelearning.regression.GradientDescent;
import pt.mleiria.machinelearning.functions.Log;
import pt.mleiria.machinelearning.matrixAlgebra.MatrixUtils;

/**
 * @author manuel
 *
 */
public class LogisticRegression extends GradientDescent {

    /**
     *
     * @param featuresX
     * @param outputY
     * @param alpha
     */
    public LogisticRegression(final Matrix featuresX, final Vector outputY, final double alpha) {
        super(featuresX, outputY, alpha);
    }
    /**
     * 
     * @param featuresX
     * @param outputY
     * @param alpha
     * @param theta 
     */
    public LogisticRegression(final Matrix featuresX, final Vector outputY, final double alpha, final Vector theta) {
        super(featuresX, outputY, alpha, theta);
    }
    
    /* (non-Javadoc)
     * @see pt.mleiria.machinelearning.regression.GradientDescent#computeCost()
     */
    @Override
    public void computeCost() {
        final double coeff = -1. / getOutputY().dimension();
        final Matrix probM = probability();
        //out.println(probM);
        final Matrix logM = new Log().value(probM);
        final Vector a = logM.transpose().product(getOutputY());
        Matrix onesM = MatrixUtils.Ones(getOutputY().dimension(), 1);
        final Vector ones = MatrixUtils.toVector(onesM);
        
        onesM = MatrixUtils.Ones(probM.rows(), probM.columns());
        final Vector b = ones.subtract(getOutputY());
        
        final Matrix c = onesM.subtract(probM);
        final Matrix d = new Log().value(c).transpose();
        final Vector e = d.product(b);
        getCostHistory()[iterations] = coeff * a.add(e).component(0);
        //out.println("getCostHistory:"+getCostHistory()[iterations]);
        /*
        final double coeff = -1.0 / (double)getDataSize();
        final Vector a = getFeaturesX().product(getTheta());
        System.out.println("[+]a:"+a.toString());
        final Matrix b = new Sigmoid().value(new Matrix(a));
        System.out.println("[+]Sigmoid:"+b.toString());
        final Matrix c = new Log().value(b);
        System.out.println("[+]Log:"+c.toString());
        final Matrix d = c.transpose();
        final double e = d.product(getOutputY()).component(0);
        //System.out.println("[+]e:"+e);
        final Matrix ones = MatrixUtils.Ones(b.rows(), b.columns());
        final Matrix f = ones.subtract(b);
        final Matrix g = new Log().value(f);
        final Matrix h = g.transpose();
        final Vector vOnes = MatrixUtils.toVector(MatrixUtils.Ones(getOutputY().dimension(), 1));
        final Vector i = vOnes.subtract(getOutputY());
        final double j = h.product(i).component(0);
        //System.out.println("[+]j:"+j);
        final double result = coeff * (e + j);
        System.out.println("[+]result:"+result);
        getCostHistory()[iterations] = result;
        */
    }

    /* (non-Javadoc)
     * @see pt.mleiria.machinelearning.iterations.IteratorProcessor#evaluateIteration()
     */
    @Override
    public double evaluateIteration() {;
        final Vector resM = MatrixUtils.toVector(probability());
        final Vector a = resM.subtract(getOutputY());
        final Vector b = getFeaturesX().transpose().product(a).product(1.0 / getOutputY().dimension());
        
        setTheta(getTheta().subtract(b.product(getAlpha())));
    
        computeCost();
        double precision = getCostHistory()[iterations] - getCostHistory()[iterations - 1];
        out.println("Precision:"+abs(precision));
        return abs(precision);
        /*
        double coeff = -(getAlpha() / (double) getDataSize());
        final Vector a = getFeaturesX().product(getTheta());
        final Vector b = MatrixUtils.toVector(new Sigmoid().value(new Matrix(a)));
        final Vector c = b.subtract(getOutputY());
        final Vector d = getFeaturesX().transpose().product(c);
        final Vector e = d.product(coeff);
        setTheta(getTheta().subtract(e));
        computeCost();
        double precision = getCostHistory()[iterations] - getCostHistory()[iterations - 1];
        return abs(precision);
*/
    }

    public Matrix probability(){
        final Vector theta = getFeaturesX().product(getTheta());
        out.println("theta:"+theta.toString());
        final Matrix res = new Sigmoid().value(new Matrix(theta));
        return res;
    }
}
