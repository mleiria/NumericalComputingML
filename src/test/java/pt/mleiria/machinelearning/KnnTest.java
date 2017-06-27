/**
 * 
 */
package pt.mleiria.machinelearning;

import org.apache.log4j.Logger;

import junit.framework.Assert;
import junit.framework.TestCase;
import pt.mleiria.machinelearning.classification.KNN;
import pt.mleiria.machinelearning.matrixAlgebra.Matrix;
import pt.mleiria.machinelearning.matrixAlgebra.Vector;

/**
 * @author manuel
 *
 */
public class KnnTest extends TestCase{
	
	/**
     * Logger
     */
    private static final Logger log = Logger.getLogger("mlearningLog");
    
	private final String dataFile = "/home/manuel/Tools/adalineProcesses/mlearning/knn/iris.data";
	private KNN knn; 
	
	@Override
    protected void setUp(){
		knn = new KNN(dataFile);
		knn.loadDataSet();
	}
	/**
	 * 
	 */
	public void testEuclideanDistance(){
		double expectedDistance = 3.4641016151377544;
		Vector v1 = new Vector(new double[]{2, 2, 2, 0});
		Vector v2 = new Vector(new double[]{4, 4, 4, 1});
		double distance = knn.euclideanDistance(v1, v2, 3);
		
		Assert.assertEquals(expectedDistance, distance);
	}
	/**
	 * 
	 */
	public void testNeighbors(){
		double[][] trainSetComp = new double[][]{{2, 2, 2, 0}, {4, 4, 4, 1}};
		double[] testInstanceComp = new double[]{5, 5, 5};
		Matrix trainSet = new Matrix(trainSetComp);
		Vector testInstance = new Vector(testInstanceComp);
		Matrix res = knn.getNeighbors(trainSet, testInstance, 1);
		
		log.info("Neighbors:" + res.toString());
		Assert.assertEquals(4.0, res.component(0, 0));
		Assert.assertEquals(4.0, res.component(0, 1));
		Assert.assertEquals(4.0, res.component(0, 1));
		Assert.assertEquals(1.0, res.component(0, 3));
	}
	/**
	 * 
	 */
	public void testGetResponse(){
		double[][] components = new double[][]{{1, 1, 1, 0}, {2, 2, 2, 0}, {3, 3, 3, 1}};
		Matrix neighbors = new Matrix(components);
		String response = knn.getResponse(neighbors);
		log.info("Voted Class:" + response);
		Assert.assertEquals("Iris-setosa", response);
	}
	
}
