package pt.mleiria.machinelearning.regression;

import pt.mleiria.machinelearning.iterations.IteratorProcessor;
import pt.mleiria.machinelearning.matrixAlgebra.Matrix;
import pt.mleiria.machinelearning.matrixAlgebra.Vector;

/**
 *
 * @author manuel
 */
public class GradientDescent extends IteratorProcessor {

    private Matrix featuresX;
    private Vector outputY;
    private Vector theta;
    private double alpha;
    private final int dataSize;
    private double[] costHistory;
    /**
     * 
     * @param featuresX
     * @param outputY
     * @param alpha 
     */
    public GradientDescent(Matrix featuresX, Vector outputY, double alpha) {
        this.featuresX = featuresX;
        this.outputY = outputY;
        this.alpha = alpha;
        this.dataSize = outputY.dimension();
        
        
    }
    /**
     * 
     */
    @Override
    public void initializeIterations() {
        costHistory = new double[getMaximumIterations() + 1];
        setFeaturesX(featuresX.addOnes());
        setTheta(new Vector(featuresX.columns()));
        computeCost();
    }
    /**
     * 
     */
    private void computeCost() {
        double coeff = 1.0 / (2.0 * dataSize);
        final Vector a = featuresX.product(theta);
        final Vector b = a.subtract(outputY);
        //final Matrix c = b.transpose();
        final double d = b.product(b);
        final double result = d * coeff;
        costHistory[iterations] = result;
    }

    /**
     * theta = theta - (alpha / m) * X' * (X * theta - y);
     *
     * b = X * theta 
     * c = b - y 
     * d = X' * c 
     * e = (alpha/m) * d
     *
     * @return
     */
    @Override
    public double evaluateIteration() {
        double coeff = (alpha / (double) dataSize);
        Vector b = featuresX.product(theta);
        Vector c = b.subtract(outputY);
        Matrix d = featuresX.transpose();
        Vector e = d.product(c);
        Vector f = e.product(coeff);
        theta = theta.subtract(f);
        computeCost();
        double precision = costHistory[iterations] - costHistory[iterations - 1];
        return Math.abs(precision);
    }

    /**
     * @return the featuresX
     */
    public Matrix getFeaturesX() {
        return featuresX;
    }

    /**
     * @param featuresX the featuresX to set
     */
    public void setFeaturesX(Matrix featuresX) {
        this.featuresX = featuresX;
    }

    /**
     * @return the outputY
     */
    public Vector getOutputY() {
        return outputY;
    }

    /**
     * @param outputY the outputY to set
     */
    public void setOutputY(Vector outputY) {
        this.outputY = outputY;
    }

    /**
     * @return the theta
     */
    public Vector getTheta() {
        return theta;
    }

    /**
     * @param theta the theta to set
     */
    public void setTheta(Vector theta) {
        this.theta = theta;
    }

    /**
     * @return the alpha
     */
    public double getAlpha() {
        return alpha;
    }

    /**
     * @param alpha the alpha to set
     */
    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double[] getCostHistory() {
        return costHistory;
    }

}
