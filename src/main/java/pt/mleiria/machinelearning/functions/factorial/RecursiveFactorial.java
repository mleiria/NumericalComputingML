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
public class RecursiveFactorial implements Factorial{

    @Override
    public BigInteger doFactorial(int n) {
        BigInteger val;
        if(n == 0)return BigInteger.ONE;
        val = BigInteger.valueOf(n).multiply(doFactorial(n - 1));
        return val;
    }
    
}
