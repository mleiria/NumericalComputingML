/**
 *
 */
package pt.mleiria.machinelearning.classification;

import pt.mleiria.machinelearning.functions.Sigmoid;
import pt.mleiria.machinelearning.matrixAlgebra.Matrix;
import pt.mleiria.machinelearning.matrixAlgebra.Vector;
import pt.mleiria.machinelearning.regression.GradientDescent;
import pt.mleiria.machinelearning.functions.Log;

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
        final Vector a = getFeaturesX().product(getTheta());
        final Matrix h = new Sigmoid().value(new Matrix(a));
        final Matrix logH = new Log().value(h);
        final Vector firstTerm = logH.product(getOutputY()).product(-1.0);

        double coeff = 1.0 / (2.0 * getDataSize());

    }

    /* (non-Javadoc)
     * @see pt.mleiria.machinelearning.iterations.IteratorProcessor#evaluateIteration()
     */
    @Override
    public double evaluateIteration() {
        // TODO Auto-generated method stub
        return 0;
    }

}
