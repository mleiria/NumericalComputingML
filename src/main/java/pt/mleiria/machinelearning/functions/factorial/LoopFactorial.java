/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.machinelearning.functions.factorial;

import pt.mleiria.machinelearning.interfaces.Factorial;
import java.math.BigInteger;

/**
 *
 * @author manuel
 */
public class LoopFactorial implements Factorial {

    @Override
    public BigInteger doFactorial(int n) {
        BigInteger val = BigInteger.ONE;
        for (int i = 1; i <= n; i++) {
            val = val.multiply(BigInteger.valueOf(i));
        }
        return val;
    }

}
