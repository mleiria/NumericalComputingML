/**
 *
 */
package pt.mleiria.machinelearning.regression;

import pt.mleiria.machinelearning.iterations.IteratorProcessor;
import pt.mleiria.machinelearning.matrixAlgebra.Matrix;
import pt.mleiria.machinelearning.matrixAlgebra.Vector;

/**
 * @author manuel
 *
 */
public abstract class GradientDescent extends IteratorProcessor {

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
    public GradientDescent(final Matrix featuresX, final Vector outputY, final double alpha) {
        this.featuresX = featuresX;
        this.outputY = outputY;
        this.alpha = alpha;
        this.dataSize = outputY.dimension();
    }
    
    public GradientDescent(final Matrix featuresX, final Vector outputY, final double alpha, final Vector theta) {
        this(featuresX, outputY, alpha);
        this.theta = theta;
    }

    /**
     *
     */
    @Override
    public void initializeIterations() {
        costHistory = new double[getMaximumIterations() + 1];
        //setFeaturesX(featuresX.addOnes());
        if(theta == null){
            setTheta(new Vector(featuresX.columns()));
        }
        computeCost();
    }

    /**
     *
     */
    public abstract void computeCost();

    /**
     * @return the featuresX
     */
    public Matrix getFeaturesX() {
        return featuresX;
    }

    /**
     * @param featuresX the featuresX to set
     */
    public void setFeaturesX(final Matrix featuresX) {
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
    public void setOutputY(final Vector outputY) {
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
    public void setTheta(final Vector theta) {
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
    public void setAlpha(final double alpha) {
        this.alpha = alpha;
    }

    public double[] getCostHistory() {
        return costHistory;
    }

    public int getDataSize() {
        return dataSize;
    }

    public void setCostHistory(final double[] costHistory) {
        this.costHistory = costHistory;
    }
}
