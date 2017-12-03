/**
 *
 */
package pt.mleiria.machinelearning.classification;

import static java.lang.Math.abs;
import pt.mleiria.machinelearning.functions.Sigmoid;
import pt.mleiria.machinelearning.matrixAlgebra.Matrix;
import pt.mleiria.machinelearning.matrixAlgebra.Vector;
import pt.mleiria.machinelearning.regression.GradientDescent;
import pt.mleiria.machinelearning.functions.Log;
import pt.mleiria.machinelearning.matrixAlgebra.MatrixUtils;
import pt.mleiria.numericalAnalysis.utils.IOUtils;
import pt.mleiria.numericalAnalysis.utils.ViewUtils;

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

    /* (non-Javadoc)
     * @see pt.mleiria.machinelearning.regression.GradientDescent#computeCost()
     */
    @Override
    public void computeCost() {
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
    }

    /* (non-Javadoc)
     * @see pt.mleiria.machinelearning.iterations.IteratorProcessor#evaluateIteration()
     */
    @Override
    public double evaluateIteration() {;
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
    }

    
}
