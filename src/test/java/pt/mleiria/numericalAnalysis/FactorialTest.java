/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.numericalAnalysis;

import java.math.BigInteger;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.apache.log4j.Logger;
import pt.mleiria.machinelearning.functions.factorial.FactorialFactory;
import pt.mleiria.machinelearning.interfaces.Factorial;

/**
 *
 * @author manuel
 */
public class FactorialTest extends TestCase{
    private static final Logger log = org.apache.log4j.Logger.getLogger("mlearningLog");
    private final int n = 10;
    private final BigInteger result = BigInteger.valueOf(3628800);
    private FactorialFactory ff;
    
    @Override
    protected void setUp() throws Exception {
        ff = new FactorialFactory();
    }
    /**
     * 
     */
    public void testLoopFactorial(){
        Factorial lf = ff.getFactorialAlgorithm(FactorialFactory.FactorialTypes.LOOP);
        log.info("Result Loop Factorial:" + result);
        Assert.assertEquals(result, lf.doFactorial(n));
    }
    /**
     * 
     */
    public void testRecursiveFactorial(){
        Factorial lf = ff.getFactorialAlgorithm(FactorialFactory.FactorialTypes.RECURSIVE);
        Assert.assertEquals(result, lf.doFactorial(n));
    }
    /**
     * 
     */
    public void testCachedFactorial(){
        Factorial lf = ff.getFactorialAlgorithm(FactorialFactory.FactorialTypes.CACHED);
        Assert.assertEquals(result, lf.doFactorial(n));
    }
}
