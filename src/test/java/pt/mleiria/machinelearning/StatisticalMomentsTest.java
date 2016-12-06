/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.machinelearning;

import junit.framework.Assert;
import junit.framework.TestCase;
import pt.mleiria.machinelearning.statistics.StatisticalMoments;


/**
 *
 * @author manuel
 */
public class StatisticalMomentsTest extends TestCase{
    
    private StatisticalMoments sm;
    private double[] x = new double[5];
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        sm = new StatisticalMoments(2);
        for(int i = 0; i < x.length; i++){
            x[i] = 2;
        }
    }
    
    public void testAverage(){
        for(int i= 0; i < x.length; i++){
            sm.accumulate(x[i]);
        }
        Assert.assertEquals(2.0, sm.average());
    }
    
}
