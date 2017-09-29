/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.machinelearning;

import java.util.DoubleSummaryStatistics;

import org.apache.log4j.Logger;

import junit.framework.TestCase;
import static org.apache.log4j.Logger.getLogger;
import pt.mleiria.machinelearning.matrixAlgebra.Matrix;
import pt.mleiria.machinelearning.matrixAlgebra.Vector;

/**
 *
 * @author manuel
 */
public class VectorTest extends TestCase {
    
    
    private static final Logger log = getLogger("mlearningLog");
    Matrix a;
    
     @Override
    protected void setUp() throws Exception {
        super.setUp();
        final double[][] components = new double[3][2];
        components[0][0] = 1;
        components[0][1] = 2;
        components[1][0] = 3;
        components[1][1] = 4;
        components[2][0] = 5;
        components[2][1] = 6;
        a = new Matrix(components);
        log.info("\n" + a.toString());
    }
    
    public void testVectorProduct(){
        double[] a = new double[]{1, 2};
        double[] b = new double[]{3, 4};
        Vector v1 = new Vector(a);
        Vector v2 = new Vector(b);
        Matrix c = v1.tensorProduct(v2);
        log.info(c.toString());
        assertEquals(2, c.columns());
        assertEquals(2, c.rows());
    }
    
    public void testVectorMatrixProduct(){
        double[] v = new double[]{1, 2, 3};
        Vector v1 = new Vector(v);
        Vector c = v1.product(a);
        log.info("Vector c: " + c.toString());
        assertEquals(2, c.dimension());
    }
    
    public void testGetSummaryStatistics(){
	double[] v = new double[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        Vector v1 = new Vector(v);
        
        DoubleSummaryStatistics stats = v1.getSummaryStatistics();
        assertEquals(9, v1.dimension());
        assertEquals(45.0, stats.getSum());
        
    }
    
}
