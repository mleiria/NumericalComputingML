/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.numericalAnalysis;

import static org.apache.log4j.Logger.getLogger;
import static pt.mleiria.machinelearning.functions.factorial.FactorialFactory.FactorialTypes.CACHED;
import static pt.mleiria.machinelearning.functions.factorial.FactorialFactory.FactorialTypes.LOOP;
import static pt.mleiria.machinelearning.functions.factorial.FactorialFactory.FactorialTypes.PARALLEL;
import static pt.mleiria.machinelearning.functions.factorial.FactorialFactory.FactorialTypes.RECURSIVE;

import java.math.BigInteger;

import org.apache.log4j.Logger;

import junit.framework.TestCase;
import pt.mleiria.machinelearning.functions.factorial.FactorialFactory;
import pt.mleiria.machinelearning.interfaces.Factorial;

/**
 *
 * @author manuel
 */
public class FactorialTest extends TestCase{
    private static final Logger log = getLogger("mlearningLog");
    //private final int n = 10;
    private final int n = 50;
    private final BigInteger result = new BigInteger("30414093201713378043612608166064768844377641568960512000000000000");
    //private final BigInteger result = BigInteger.valueOf(3628800);
    private FactorialFactory ff;
    
    @Override
    protected void setUp() throws Exception {
        ff = new FactorialFactory();
    }
    /**
     * 
     */
    public void testLoopFactorial(){
        Factorial lf = ff.getFactorialAlgorithm(LOOP);
        log.info("Result Loop Factorial:" + result);
        assertEquals(result, lf.doFactorial(n));
    }
    /**
     * 
     */
    public void testRecursiveFactorial(){
        Factorial lf = ff.getFactorialAlgorithm(RECURSIVE);
        assertEquals(result, lf.doFactorial(n));
    }
    /**
     * 
     */
    public void testCachedFactorial(){
        Factorial lf = ff.getFactorialAlgorithm(CACHED);
        assertEquals(result, lf.doFactorial(n));
    }
    public void testParallelFactorial(){
        Factorial lf = ff.getFactorialAlgorithm(PARALLEL);
        assertEquals(result, lf.doFactorial(n));
    }
}
