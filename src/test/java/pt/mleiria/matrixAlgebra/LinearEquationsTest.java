/**
 * 
 */
package pt.mleiria.matrixAlgebra;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import junit.framework.Assert;
import junit.framework.TestCase;
import pt.mleiria.machinelearning.matrixAlgebra.LinearEquations;
import pt.mleiria.machinelearning.matrixAlgebra.Vector;
import pt.mleiria.numericalAnalysis.utils.ViewUtils;

/**
 * @author manuel
 *
 */
public class LinearEquationsTest extends TestCase{
	
	private static final Logger log = Logger.getLogger("mlearningLog");
	
	public void testLinearEquation(){
		double[][] rows = new double[3][3];
		rows[0][0] = 2.0;
		rows[0][1] = 1.0;
		rows[0][2] = 4.0;
		rows[1][0] = 6.0;
		rows[1][1] = 1.0;
		rows[1][2] = 0.0;
		rows[2][0] = -1.0;
		rows[2][1] = 2.0;
		rows[2][2] = -10.0;
		
		double[] y = new double[]{2.0, -10.0, -4.0};
		
		LinearEquations le = new LinearEquations(rows, y);
		Vector solution = le.solution();
		DecimalFormat df = new DecimalFormat("#.#");
		df.setRoundingMode(RoundingMode.CEILING);
		
		log.info("Solution:" + solution.toString());
		log.info("Matrix:" + le.toString());
		
		Assert.assertEquals("1", df.format(solution.component(2)));
	}

}
