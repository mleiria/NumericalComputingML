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
import pt.mleiria.numericalAnalysis.utils.Utils;

/**
 * @author manuel
 *
 */
public class LeastSquaresVectorTest extends TestCase{
	
	private static final Logger log = Logger.getLogger("mlearningLog");
	
	private Matrix X;
	private Vector t;
	
	@Override
    protected void setUp(){
		final double[][] x = new double[3][2];
		x[0][0] = 1;
		x[0][1] = 1;
		x[1][0] = 1;
		x[1][1] = 3;
		x[2][0] = 1;
		x[2][1] = 5;
		X = new Matrix(x);
		t = new Vector(new double[]{4.8, 11.3, 17.2});
	}
	
	public void testLS(){
		final Matrix XT = X.transpose();
		final Matrix XX = XT.multiply(X);
		final Matrix XI = XX.inverse();
		final Matrix X1 = XI.multiply(XT);
		final Vector res = X1.product(t);
		
		
		final double intercept =  new BigDecimal(res.component(0), Utils.setPrecision(2)).doubleValue();
		final double slope = new BigDecimal(res.component(1), Utils.setPrecision(2)).doubleValue();

		log.info("Intercept:" + intercept + "; Slope:" + slope);
		
		Assert.assertEquals(1.8, intercept);
		Assert.assertEquals(3.1, slope);
		
	}

}
