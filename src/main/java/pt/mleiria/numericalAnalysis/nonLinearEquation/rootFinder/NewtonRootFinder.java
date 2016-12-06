/**
 *
 */
package pt.mleiria.numericalAnalysis.nonLinearEquation.rootFinder;

import pt.mleiria.machinelearning.interfaces.OneVarFunctionDerivative;
import pt.mleiria.numericalAnalysis.exception.InvalidIntervalException;
import pt.mleiria.numericalAnalysis.exception.IterationCountExceededException;
import pt.mleiria.numericalAnalysis.utils.DataAccumulator;
import pt.mleiria.numericalAnalysis.utils.Epsilon;
import pt.mleiria.numericalAnalysis.utils.Utils;

/**
 * @author Manuel
 *
 */
public final class NewtonRootFinder extends AbstractRootFinder {

    private double am;

    private double bm;

    private double xm, xm1;

    /**
     * @param df
     * @param maxIters
     * @param comparisonTollerance limite de erro admissivel. A iteraccao para
     * quando o valor encontrado for menor ou igual comparisonTollerance
     * @param a coordenada x do limite inferior do intervalo
     * @param b coordenada x do limite superior do intervalo
     * @param x_0
     * @throws pt.mleiria.numericalAnalysis.exception.InvalidIntervalException
     */
    public NewtonRootFinder(OneVarFunctionDerivative df, int maxIters,
            double comparisonTollerance, double a, double b, double x_0)
            throws InvalidIntervalException {

        super(df, maxIters, comparisonTollerance);
        checkInterval(a, b);
        // Inicializacao
        this.xm = x_0;
    }

    /**
     * @param f a funcao a encontrar o zero
     * @param maxIters o numero maximo de iteraccoes permitidas
     * @param a coordenada x do limite inferior do intervalo
     * @param b coordenada x do limite superior do intervalo
     * @param x_0
     * @throws InvalidIntervalException
     */
    public NewtonRootFinder(OneVarFunctionDerivative f, int maxIters,
            double a, double b, double x_0) throws InvalidIntervalException {

        super(f, maxIters, Epsilon.doubleValue());
        checkInterval(a, b, x_0);
        // Inicializacao
        this.xm = x_0;
    }

    public void checkInterval(double a, double b, double x_0)
            throws InvalidIntervalException {
        if (x_0 > b || x_0 < a) {
            throw new InvalidIntervalException();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see mleiria.nonLinearEquation.rootFinder.AbstractRootFinder#computeNextPosition()
     */
    @Override
    public void computeNextPosition() {
        xm1 = xm - f.value(xm) / df.derivative(xm);

    }

    /*
     * (non-Javadoc)
     * 
     * @see mleiria.nonLinearEquation.rootFinder.AbstractRootFinder#findRoot()
     */
    @Override
    public void findRoot() throws IterationCountExceededException {
        // Start Logger
        String[][] data = new String[][]{
            {String.valueOf(getIterationCount()), "5"},
            {Utils.formataNumero(xm1, "##0.00000000"), "20"},
            {
                Utils.formataNumero(f.value(xm1),
                "##0.00000000"), "20"},
            {
                Utils.formataNumero(1.41421356 - xm1,
                "##0.00000000"), "20"}};
        DataAccumulator.feedAccumulator(data);

        Utils.print(String.valueOf(getIterationCount()), 5);
        Utils.print(Utils.formataNumero(xm1,
                "##0.00000000"), 20);
        Utils.print(Utils.formataNumero(f
                .value(xm1), "##0.00000000"), 20);
        Utils.print(Utils.formataNumero(
                1.41421356 - xm1, "##0.00000000"), 20);
        Utils.println();
        // End Logger
        checkIterationCount();
        computeNextPosition();
        n++;
        if (hasConverged()) {
            // Start Logger
            data = new String[][]{
                {String.valueOf(getIterationCount()), "5"},
                {Utils.formataNumero(xm1, "##0.00000000"),
                    "20"},
                {
                    Utils.formataNumero(f.value(xm1),
                    "##0.00000000"), "20"},
                {
                    Utils.formataNumero(1.41421356 - xm1,
                    "##0.00000000"), "20"}};
            DataAccumulator.feedAccumulator(data);
            Utils.clearFile("Newton.txt");
            Utils.appendToFile("Newton.txt", true,
                    DataAccumulator.getData());
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
        if ((Math.abs(xm1 - xm) <= comparisonTollerance)
                || (f.value(xm1) <= comparisonTollerance && f.value(xm1) >= -comparisonTollerance)) {
            return true;
        } else {
            return false;
        }
    }

}
