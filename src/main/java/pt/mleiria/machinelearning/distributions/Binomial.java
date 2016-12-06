/*
 * Se a v.a. que contem o numero de tentativas que resultam
 * em sucesso tem um dist. binomial com paramtros n e p, escrevemos
 * X ~ B(n, p). A prob. de ter exactamente k sucessos e 
 * f(k, n, p) = Cn,k * p^k (1-p) ^n-k
 */
package pt.mleiria.machinelearning.distributions;

import pt.mleiria.machinelearning.StopWatch;

/**
 *
 * @author manuel
 */
public class Binomial {

    public static double binomial1(int N, int k, double p) {
        if(N < 0 || k < 0){
            return 0.0;
        }
        if (k == 0) {
            return Math.pow(1 - p, N);
        } else {
            return ((p * (N - k + 1)) /(k * (1 - p))) * binomial1(N, k - 1, p);
        }
    }
    
    public static double binomial2(int N, int k, double p) {
        if (N == 0 && k == 0) return 1.0;
        if (N < 0 || k < 0) return 0.0;
        return (1.0 - p) *binomial1(N-1, k, p) + p*binomial1(N-1, k-1, p);
    }

    public static void main(String[] args) {
        int N = 1000;
        int k = 50;
        double p = 0.25;
        
        StopWatch st = new StopWatch();
        System.out.println(binomial1(N, k, p));
        System.out.println(st.elapsedTime());
        
        st = new StopWatch();
        System.out.println(binomial2(N, k, p));
        System.out.println(st.elapsedTime());
     
    }

}

