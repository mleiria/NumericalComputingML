/**
 * 
 */
package pt.mleiria.machinelearning.regression;

import java.math.BigDecimal;

import pt.mleiria.machinelearning.matrixAlgebra.Matrix;
import pt.mleiria.machinelearning.matrixAlgebra.Vector;
import pt.mleiria.numericalAnalysis.utils.Utils;

/**
 * @author manuel
 *
 */
public class LeastSquares {

	private final Matrix features;
	private final Vector target;

	public LeastSquares(final Matrix features, final Vector target) {
		this.features = features;
		this.target = target;

	}
	/**
	 * 
	 * @return
	 */
	public Vector getCoeficients(){
		return compute();
	}
	/**
	 * 
	 * @return
	 */
	private Vector compute(){
		final Matrix XT = features.transpose();
		final Matrix XX = XT.multiply(features);
		final Matrix XI = XX.inverse();
		final Matrix X1 = XI.multiply(XT);
		final Vector res = X1.product(target);
		return res;
	}
}
