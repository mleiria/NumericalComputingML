/**
 *
 */
package pt.mleiria.numericalAnalysis.nonLinearEquation.rootFinder;

import static java.lang.Math.abs;
import static java.lang.String.valueOf;
import static java.lang.String.valueOf;
import static java.lang.String.valueOf;
import pt.mleiria.machinelearning.interfaces.OneVarFunction;
import pt.mleiria.numericalAnalysis.exception.InvalidIntervalException;
import pt.mleiria.numericalAnalysis.exception.IterationCountExceededException;
import pt.mleiria.numericalAnalysis.utils.DataAccumulator;
import static pt.mleiria.numericalAnalysis.utils.DataAccumulator.feedAccumulator;
import static pt.mleiria.numericalAnalysis.utils.DataAccumulator.getData;
import pt.mleiria.numericalAnalysis.utils.Utils;
import static pt.mleiria.numericalAnalysis.utils.Utils.appendToFile;
import static pt.mleiria.numericalAnalysis.utils.Utils.clearFile;
import static pt.mleiria.numericalAnalysis.utils.Utils.formataNumero;
import static pt.mleiria.numericalAnalysis.utils.Utils.print;
import static pt.mleiria.numericalAnalysis.utils.Utils.println;

/**
 * @author nb026
 *
 */
public class SecantRootFinder extends AbstractRootFinder {

    private double xm;  //O valor x[m]
    private double xmp1;  //O valor x[m+1]		
    private double xmm1;  //O valor x[m-1]

    /**
     * @param f
     * @param maxIters
     * @param comparisonTollerance
     * @param x_1 O valor x[-1]
     * @param x_0 O valor x[0]
     * @param a O limite inferior do intervalo
     * @param b O limite superior do intervalo
     * @throws InvalidIntervalException
     */
    public SecantRootFinder(OneVarFunction f, int maxIters, double comparisonTollerance, double x_1, double x_0, double a, double b) throws InvalidIntervalException {
        super(f, maxIters, comparisonTollerance);
        checkInterval(a, b, x_1, x_0);
        //Inicializacao
        this.xmm1 = x_1;
        this.xm = x_0;

    }

    /**
     * @param a o limite inferior do intervalo
     * @param b o limite superior do intervalo
     * @param x_1 valor inicial para a iterac��o
     * @param x_0 valor inicial para a iterac��o
     * @throws InvalidIntervalException se x_1 e x_0 nao estiverem contidos no
     * intervalo [a,b]
     */
    public void checkInterval(double a, double b, double x_1, double x_0) throws InvalidIntervalException {
        if (x_0 < a || x_0 > b) {
            throw new InvalidIntervalException();
        }
        if (x_1 < a || x_1 > b) {
            throw new InvalidIntervalException();
        }
        if (x_0 == x_1) {
            throw new InvalidIntervalException();
        }
    }

    /* (non-Javadoc)
	 * @see mleiria.nonLinearEquation.rootFinder.AbstractRootFinder#computeNextPosition()
     */
    @Override
    public void computeNextPosition() {
        xmp1 = xm - f.value(xm) * ((xm - xmm1) / (f.value(xm) - f.value(xmm1)));

    }

    /* (non-Javadoc)
	 * @see mleiria.nonLinearEquation.rootFinder.AbstractRootFinder#findRoot()
     */
    @Override
    public void findRoot() throws IterationCountExceededException {
        // Start Logger
        String[][] data = new String[][]{
            {valueOf(getIterationCount()), "5"},
            {formataNumero(xmp1, "##0.00000000"), "20"},
            {
                formataNumero(f.value(xmp1),
                "##0.00000000"), "20"},
            {
                formataNumero(1.41421356 - xmp1,
                "##0.00000000"), "20"}};
        feedAccumulator(data);

        print(valueOf(getIterationCount()), 5);
        print(formataNumero(xmp1,
                "##0.00000000"), 20);
        print(formataNumero(f
                .value(xmp1), "##0.00000000"), 20);
        print(formataNumero(
                1.41421356 - xmp1, "##0.00000000"), 20);
        println();
        // End Logger

        checkIterationCount();
        computeNextPosition();
        n++;
        if (hasConverged()) {
            // Start Logger
            data = new String[][]{
                {valueOf(getIterationCount()), "5"},
                {formataNumero(xmp1, "##0.00000000"),
                    "20"},
                {
                    formataNumero(f.value(xmp1),
                    "##0.00000000"), "20"},
                {
                    formataNumero(1.41421356 - xmp1,
                    "##0.00000000"), "20"}};
            feedAccumulator(data);
            clearFile("Secant.txt");
            appendToFile("Secant.txt", true,
                    getData());
            DataAccumulator.reset();
            // End Logger
            reset();
        } else {
            xmm1 = xm;
            xm = xmp1;
            findRoot();
        }

    }

    /* (non-Javadoc)
	 * @see mleiria.nonLinearEquation.rootFinder.AbstractRootFinder#hasConverged()
     */
    @Override
    public boolean hasConverged() {
        return (abs(xmp1 - xm) <= comparisonTollerance);

    }
}
