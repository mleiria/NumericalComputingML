/**
 * 
 */
package pt.mleiria.machinelearning;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import junit.framework.Assert;
import junit.framework.TestCase;
import pt.mleiria.machinelearning.matrixAlgebra.Matrix;
import pt.mleiria.machinelearning.matrixAlgebra.Vector;
import pt.mleiria.machinelearning.regression.LeastSquares;
import pt.mleiria.numericalAnalysis.utils.Utils;

/**
 * @author manuel
 *
 */
public class LeastSquaresVectorTest extends TestCase {

	private static final Logger log = Logger.getLogger("mlearningLog");

	private Matrix X;
	private Vector t;

	@Override
	protected void setUp() {
		final double[][] x = new double[3][2];
		x[0][0] = 1;
		x[0][1] = 1;
		x[1][0] = 1;
		x[1][1] = 3;
		x[2][0] = 1;
		x[2][1] = 5;
		X = new Matrix(x);
		t = new Vector(new double[] { 4.8, 11.3, 17.2 });
	}

	public void testLS() {
		final LeastSquares ls = new LeastSquares(X, t);
		final Vector res = ls.getCoeficients();

		final double intercept = new BigDecimal(res.component(0), Utils.setPrecision(2)).doubleValue();
		final double slope = new BigDecimal(res.component(1), Utils.setPrecision(2)).doubleValue();

		log.info("Intercept Simple:" + intercept + "; Slope:" + slope);
		log.info(res.toString());
		Assert.assertEquals(1.8, intercept);
		Assert.assertEquals(3.1, slope);

	}

	public void testQuadraticFitting() {
		final double[][] x = new double[3][2];
		x[0][0] = 1;
		x[0][1] = 1;
		x[1][0] = 1;
		x[1][1] = 3;
		x[2][0] = 1;
		x[2][1] = 5;
		final Matrix originalFeatures = new Matrix(x);
		final Vector target = new Vector(new double[] { 4.8, 11.3, 17.2 });
		// build a new column with x^2
		final double[] tmp = new double[target.dimension()];
		for (int i = 0; i < tmp.length; i++) {
			tmp[i] = Math.pow(x[i][1], 2);
		}
		final Matrix features = originalFeatures.append(new Vector(tmp));
		final LeastSquares ls = new LeastSquares(features, target);
		final Vector res = ls.getCoeficients();

		final double w0 = new BigDecimal(res.component(0), Utils.setPrecision(2)).doubleValue();
		final double w1 = new BigDecimal(res.component(1), Utils.setPrecision(2)).doubleValue();
		final double w2 = new BigDecimal(res.component(2), Utils.setPrecision(2)).doubleValue();

		log.info("Intercept Quadratic: [" + w0 + ";" + w1 + ";" + w2);
		Assert.assertEquals(3, features.columns());
	}

	public void testCubicFitting() {
		final double[][] x = new double[3][2];
		x[0][0] = 1;
		x[0][1] = 1;
		x[1][0] = 1;
		x[1][1] = 3;
		x[2][0] = 1;
		x[2][1] = 5;
		final Matrix originalFeatures = new Matrix(x);
		final Vector target = new Vector(new double[] { 4.8, 11.3, 17.2 });
		// build a new column with x^2
		final double[] tmp = new double[target.dimension()];
		for (int i = 0; i < tmp.length; i++) {
			tmp[i] = Math.pow(x[i][1], 2);
		}
		// build a new column with x^3
		final double[] tmp1 = new double[target.dimension()];
		for (int i = 0; i < tmp1.length; i++) {
			tmp1[i] = Math.pow(x[i][1], 3);
		}
	
		final Matrix features = originalFeatures.append(new Vector(tmp));
		final Matrix finalFeatures = features.append(new Vector(tmp1));
		final LeastSquares ls = new LeastSquares(finalFeatures, target);
		final Vector res = ls.getCoeficients();

		final double w0 = new BigDecimal(res.component(0), Utils.setPrecision(2)).doubleValue();
		final double w1 = new BigDecimal(res.component(1), Utils.setPrecision(2)).doubleValue();
		final double w2 = new BigDecimal(res.component(2), Utils.setPrecision(2)).doubleValue();
		final double w3 = new BigDecimal(res.component(3), Utils.setPrecision(2)).doubleValue();

		log.info("Intercept Polynomial: [" + w0 + ";" + w1 + ";" + w2 + ";" + w3);
		Assert.assertEquals(4, finalFeatures.columns());
	}

}
