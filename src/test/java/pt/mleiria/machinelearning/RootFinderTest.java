/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.machinelearning;

import junit.framework.Assert;
import junit.framework.TestCase;
import pt.mleiria.machinelearning.interfaces.OneVarFunction;
import pt.mleiria.machinelearning.interfaces.OneVarFunctionDerivative;
import pt.mleiria.machinelearning.rootfinder.NewtonRootFinder;
import pt.mleiria.numericalAnalysis.mathUtils.Function.RootFunctions;


/**
 *
 * @author manuel
 */
public class RootFinderTest extends TestCase {
    
    private OneVarFunction f = RootFunctions.function("x^2 - 2");
    
    public void testNewtoonRootFinder(){
        final double desiredPrecision = 5E-12;
            NewtonRootFinder nrf;
            double a = 0;
            double b = 4;
            nrf = new NewtonRootFinder((OneVarFunctionDerivative)f, a, b, 110);
            nrf.setDesiredPrecision(desiredPrecision);
            nrf.evaluate();
            Assert.assertEquals(1.4142135623834682, nrf.getRoot());

    }
    
    
}
