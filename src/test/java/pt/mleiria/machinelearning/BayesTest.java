package pt.mleiria.machinelearning;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import junit.framework.Assert;
import junit.framework.TestCase;
import pt.mleiria.machinelearning.classification.Bayes;
import pt.mleiria.machinelearning.matrixAlgebra.Matrix;
import pt.mleiria.numericalAnalysis.utils.ViewUtils;

public class BayesTest extends TestCase{

	/**
     * Logger
     */
    private static final Logger log = Logger.getLogger("mlearningLog");
    
    private final String dataFile = "/home/manuel/Tools/adalineProcesses/mlearning/bayes/pima-indians-diabetes.data.csv";
	private Bayes bayes; 
	
	
	@Override
    protected void setUp(){
		bayes = new Bayes(dataFile);
		bayes.loadDataSet();
	}
	
	public void testSeparatedByClass(){
		final double[][] components = new double[3][3];
        components[0][0] = 1;
        components[0][1] = 20;
        components[0][2] = 1;
        components[1][0] = 2;
        components[1][1] = 21;
        components[1][2] = 0;
        components[2][0] = 3;
        components[2][1] = 22;
        components[2][2] = 1;
        Matrix a = new Matrix(components);
        Map<Integer, Matrix> separated = bayes.separateByClass(a);
        for (Map.Entry<Integer, Matrix> entry : separated.entrySet()) {
        	log.info(entry.getKey());
        	log.info("\n" + entry.getValue().toString());
        }
        
        Assert.assertEquals(2, separated.size());
	}
	
	public void testGetAvgStdev(){
		double[][] componentsM0 = new double[2][3];
		double[][] componentsM1 = new double[2][3];
		
		componentsM0[0][0] = 2;
		componentsM0[0][1] = 21;
		componentsM0[0][2] = 0;
		componentsM0[1][0] = 4;
		componentsM0[1][1] = 22;
		componentsM0[1][2] = 0;
		Matrix m0 = new Matrix(componentsM0);
		
		componentsM1[0][0] = 1;
		componentsM1[0][1] = 20;
		componentsM1[0][2] = 1;
		componentsM1[1][0] = 3;
		componentsM1[1][1] = 22;
		componentsM1[1][2] = 1;
		Matrix m1 = new Matrix(componentsM1);
		
		Map<Integer, Matrix> map = new HashMap<Integer, Matrix>();
		map.put(new Integer(0), m0);
		map.put(new Integer(1), m1);
	
		Map<Integer, Matrix> res = bayes.getAvgStdev(map);
		log.info("m0:\n" + res.get(0).toString());
		log.info("m1:\n" + res.get(1).toString());
		
		
		Assert.assertEquals(3.0, res.get(0).component(0, 0));
		Assert.assertEquals(0.7071067811865476, res.get(0).component(1, 1));
		
	}
	
}
