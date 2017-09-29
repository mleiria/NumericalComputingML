package pt.mleiria.machinelearning.regression;

import static java.lang.Math.abs;
import pt.mleiria.machinelearning.matrixAlgebra.Matrix;
import pt.mleiria.machinelearning.matrixAlgebra.Vector;

/**
 *
 * @author manuel
 */
public class LinearRegression extends GradientDescent {

    /**
     *
     * @param featuresX
     * @param outputY
     * @param alpha
     */
    public LinearRegression(final Matrix featuresX, final Vector outputY, final double alpha) {
        super(featuresX, outputY, alpha);
    }

    @Override
    public void computeCost() {
        double coeff = 1.0 / (2.0 * getDataSize());
        final Vector a = getFeaturesX().product(getTheta());
        final Vector b = a.subtract(getOutputY());
        final double d = b.product(b);
        final double result = d * coeff;
        getCostHistory()[iterations] = result;
    }

    /**
     * theta = theta - (alpha / m) * X' * (X * theta - y);
     *
     * b = X * theta c = b - y d = X' * c e = (alpha/m) * d
     *
     * @return
     */
    @Override
    public double evaluateIteration() {
        double coeff = (getAlpha() / (double) getDataSize());
        Vector b = getFeaturesX().product(getTheta());
        Vector c = b.subtract(getOutputY());
        Matrix d = getFeaturesX().transpose();
        Vector e = d.product(c);
        Vector f = e.product(coeff);
        setTheta(getTheta().subtract(f));
        computeCost();
        double precision = getCostHistory()[iterations] - getCostHistory()[iterations - 1];
        return abs(precision);
    }

}
