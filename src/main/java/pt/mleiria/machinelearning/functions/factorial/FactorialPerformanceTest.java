/**
 *
 */
package pt.mleiria.machinelearning.functions.factorial;

import static java.lang.Long.MAX_VALUE;
import static java.lang.Runtime.getRuntime;
import static java.lang.System.nanoTime;
import static java.lang.System.out;
import java.math.BigInteger;
import java.util.function.Function;
import static pt.mleiria.machinelearning.functions.factorial.FactorialFactory.FactorialTypes.LOOP;
import static pt.mleiria.machinelearning.functions.factorial.FactorialFactory.FactorialTypes.PARALLEL;
import static pt.mleiria.machinelearning.functions.factorial.FactorialFactory.FactorialTypes.RECURSIVE;

import pt.mleiria.machinelearning.interfaces.Factorial;

/**
 * @author manuel
 *
 */
public class FactorialPerformanceTest {

    private static final FactorialFactory ff = new FactorialFactory();
    private static final int n = 100000;

    public static void main(String[] args) {
        int cores = getRuntime().availableProcessors();
        out.println(cores);
    }

    public static BigInteger loopFactorial(final int n) {
        final Factorial lf = ff.getFactorialAlgorithm(LOOP);
        return lf.doFactorial(n);
    }

    public static BigInteger parallelFactorial(final int n) {
        final Factorial lf = ff.getFactorialAlgorithm(PARALLEL);
        return lf.doFactorial(n);
    }

    public static BigInteger recursiveFactorial(final int n) {
        final Factorial lf = ff.getFactorialAlgorithm(RECURSIVE);
        return lf.doFactorial(n);
    }

    public static long measureFactPerf(final Function<Integer, BigInteger> adder, final int n) {
        long fastest = MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            final long start = nanoTime();
            final BigInteger fact = adder.apply(n);
            final long duration = (nanoTime() - start) / 1_000_000;
            out.println("Result: " + fact);
            if (duration < fastest) {
                fastest = duration;
            }
        }
        return fastest;
    }
}
