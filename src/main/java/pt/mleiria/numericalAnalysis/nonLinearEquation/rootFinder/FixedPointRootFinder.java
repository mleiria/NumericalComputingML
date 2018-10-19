/**
 *
 */
package pt.mleiria.numericalAnalysis.nonLinearEquation.rootFinder;

import static java.lang.Math.abs;
import static java.lang.String.valueOf;
import static java.lang.String.valueOf;
import static java.lang.String.valueOf;
import pt.mleiria.machinelearning.interfaces.OneVarFunction;
import pt.mleiria.numericalAnalysis.exception.IterationCountExceededException;
import pt.mleiria.numericalAnalysis.utils.DataAccumulator;
import static pt.mleiria.numericalAnalysis.utils.DataAccumulator.feedAccumulator;
import static pt.mleiria.numericalAnalysis.utils.DataAccumulator.getData;
import pt.mleiria.numericalAnalysis.utils.Epsilon;
import static pt.mleiria.numericalAnalysis.utils.Epsilon.doubleValue;
import pt.mleiria.numericalAnalysis.utils.Utils;
import static pt.mleiria.numericalAnalysis.utils.Utils.appendToFile;
import static pt.mleiria.numericalAnalysis.utils.Utils.clearFile;
import static pt.mleiria.numericalAnalysis.utils.Utils.formataNumero;
import static pt.mleiria.numericalAnalysis.utils.Utils.print;
import static pt.mleiria.numericalAnalysis.utils.Utils.println;

/**
 * @author Manuel
 *
 */
public class FixedPointRootFinder extends AbstractRootFinder {

    private double xm, xm1;

    private OneVarFunction<Double> iteratorFunction;

    public FixedPointRootFinder(OneVarFunction f, int maxIters,
            double comparisonTollerance, OneVarFunction iteratorFunction,
            double x_0) {
        super(f, maxIters, comparisonTollerance);
        // Inicializacao
        this.iteratorFunction = iteratorFunction;
        this.xm = x_0;
    }

    public FixedPointRootFinder(OneVarFunction f, int maxIters,
            OneVarFunction iteratorFunction, double x_0) {
        super(f, maxIters, doubleValue());
        // Inicializacao
        this.iteratorFunction = iteratorFunction;
        this.xm = x_0;
    }

    /* (non-Javadoc)
	 * @see mleiria.nonLinearEquation.rootFinder.AbstractRootFinder#computeNextPosition()
     */
    @Override
    public void computeNextPosition() {
        xm1 = iteratorFunction.value(xm);

    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see mleiria.nonLinearEquation.rootFinder.AbstractRootFinder#findRoot()
     */
    @Override
    public void findRoot() throws IterationCountExceededException {
//		 Start Logger
        String[][] data = new String[][]{
            {valueOf(getIterationCount()), "5"},
            {formataNumero(xm1, "##0.00000000"), "20"},
            {
                formataNumero(f.value(xm1),
                "##0.00000000"), "20"},
            {
                formataNumero(1.41421356 - xm1,
                "##0.00000000"), "20"}};
        feedAccumulator(data);

        print(valueOf(getIterationCount()), 5);
        print(formataNumero(xm1,
                "##0.00000000"), 20);
        print(formataNumero(f
                .value(xm1), "##0.00000000"), 20);
        print(formataNumero(
                1.41421356 - xm1, "##0.00000000"), 20);
        println();
        // End Logger
        checkIterationCount();
        computeNextPosition();
        n++;
        if (hasConverged()) {
            // Start Logger
            data = new String[][]{
                {valueOf(getIterationCount()), "5"},
                {formataNumero(xm1, "##0.00000000"),
                    "20"},
                {
                    formataNumero(f.value(xm1),
                    "##0.00000000"), "20"},
                {
                    formataNumero(1.41421356 - xm1,
                    "##0.00000000"), "20"}};
            feedAccumulator(data);
            clearFile("FixedPoint.txt");
            appendToFile("FixedPoint.txt", true,
                    getData());
            DataAccumulator.reset();
            // End Logger
            reset();
        } else {
            xm = xm1;
            findRoot();
        }

    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see mleiria.nonLinearEquation.rootFinder.AbstractRootFinder#hasConverged()
     */
    @Override
    public boolean hasConverged() {
        if ((abs(xm1 - xm) <= comparisonTollerance)
                || (f.value(xm1) <= comparisonTollerance && f.value(xm1) >= -comparisonTollerance)) {
            return true;
        } else {
            return false;
        }
    }

}
