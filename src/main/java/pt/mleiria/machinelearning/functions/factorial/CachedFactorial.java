/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.machinelearning.functions.factorial;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author manuel
 */
public class CachedFactorial extends RecursiveFactorial {

    static Map<Integer, BigInteger> cache = new HashMap<>();

    @Override
    public BigInteger doFactorial(int n) {

        BigInteger val;
        if (null != (val = cache.get(n))) {
            return val;
        }
        val = super.doFactorial(n);
        cache.put(n, val);
        return val; 
    }

}
