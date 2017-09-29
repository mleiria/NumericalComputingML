/**
 *
 */
package pt.mleiria.machinelearning.functions.factorial;

import java.math.BigInteger;
import static java.math.BigInteger.ONE;
import java.util.stream.IntStream;
import static java.util.stream.IntStream.rangeClosed;

import pt.mleiria.machinelearning.interfaces.Factorial;

/**
 * @author manuel
 *
 */
public class ParallelFactorial implements Factorial {

    @Override
    public BigInteger doFactorial(int n) {
        return rangeClosed(2, n)
                .mapToObj(BigInteger::valueOf)
                .parallel()
                .reduce(ONE, (a, b) -> a.multiply(b));
    }
}
