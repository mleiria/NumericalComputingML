/**
 * 
 */
package pt.mleiria.machinelearning.regression;

import pt.mleiria.machinelearning.matrixAlgebra.Matrix;
import pt.mleiria.machinelearning.matrixAlgebra.Vector;

/**
 * @author manuel
 *
 */
public class LeastSquares {

    private final Matrix features;
    private final Vector target;
    
    private Vector result;

    public LeastSquares(final Matrix features, final Vector target) {
	this.features = features;
	this.target = target;

    }

    /**
     * 
     * @return
     */
    public Vector getCoeficients() {
	return compute();
    }
    
    public double lostFunction(){
	if(null != result && result.dimension() != 0){
	    final Matrix XW = features.multiply(new Matrix(result));
	    final Matrix tXW = new Matrix(target).subtract(XW);
	    final Matrix tXWT = tXW.transpose();
    
	    return (1.0 / features.rows()) * tXWT.multiply(tXW).component(0, 0); 
	}else{
	    compute();
	    return lostFunction();
	}
	
    }

    /**
     * 
     * @return
     */
    private Vector compute() {
	final Matrix XT = features.transpose();
	final Matrix XX = XT.multiply(features);
	final Matrix XI = XX.inverse();
	final Matrix X1 = XI.multiply(XT);
	final Vector res = X1.product(target);
	result = res;
	return res;
    }
}
