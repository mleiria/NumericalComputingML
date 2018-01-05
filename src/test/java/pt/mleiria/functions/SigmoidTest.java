/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.functions;

import static java.lang.Math.exp;
import junit.framework.TestCase;
import pt.mleiria.machinelearning.interfaces.OneVarFunction;
import pt.mleiria.machinelearning.matrixAlgebra.MatrixUtils;
import pt.mleiria.machinelearning.matrixAlgebra.Vector;

/**
 *
 * @author Manuel Leiria <manuel.leiria at gmail.com>
 */
public class SigmoidTest extends TestCase {

    public void testSigmoid() {
        final double[] x = new double[3];
        x[0] = 1.0;
        x[1] = 2.0;
        x[2] = 3.0;
        final Vector v = new Vector(x);

        final OneVarFunction<Vector> sigmoid = (Vector a) -> {
            final double[] d = a.toComponents();
            final double[] res = new double[d.length];
            for (int i = 0; i < d.length; i++) {
                res[i] = 1. / (1.0 + exp(-d[i]));
            }
            return new Vector(res);
        };

        final Vector res = sigmoid.value(v);
        assertEquals(0.7310585786300049, res.component(0));
        assertEquals(0.8807970779778823, res.component(1));
        assertEquals(0.9525741268224334, res.component(2));

        
        
        final OneVarFunction<Vector> sigmoidDerivative = (Vector a) -> {
            final Vector s = sigmoid.value(a);
            final Vector ones = MatrixUtils.Ones(s.dimension());
            return s.wiseMultiplication(ones.subtract(s));
        };
                
        final Vector resDev = sigmoidDerivative.value(v);
        System.out.println(resDev.component(0));
        System.out.println(resDev.component(1));
        System.out.println(resDev.component(2));
        assertEquals(0.19661193324148185, resDev.component(0));
        assertEquals(0.10499358540350662, resDev.component(1));
        assertEquals(0.045176659730912, resDev.component(2));
    }

}
