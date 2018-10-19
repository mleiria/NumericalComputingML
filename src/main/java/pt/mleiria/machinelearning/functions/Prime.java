/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.machinelearning.functions;

import static java.lang.Math.sqrt;

/**
 *
 * @author manuel
 */
public class Prime {

    public static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        if (n == 2) {
            return true;
        }
        if (n % 2 == 0) {
            return false;
        }
        final int m = (int) sqrt(n);
        for (int i = 3; i <= m; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    /*http://mathworld.wolfram.com/NewtonsIteration.html
    
    public static boolean isPrime(BigInteger n){
        if (n.compareTo(BigInteger.ONE) <= 0) return false;
        final BigInteger TWO = new BigInteger("2");
        if (n.compareTo(TWO) == 0 ) return true;
        if (n.divideAndRemainder(TWO)[1].compareTo(BigInteger.ZERO)  == 0) return false;
        
        
        
        return true;
    }
     */
}
