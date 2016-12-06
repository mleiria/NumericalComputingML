/**
 * NOTA: Condicao suficiente de convergencia do metodo: f e continua em [a, b] e
 * tal que f(a)f(b) < 0
 */
package pt.mleiria.numericalAnalysis.nonLinearEquation.rootFinder;

import pt.mleiria.machinelearning.interfaces.OneVarFunction;
import pt.mleiria.numericalAnalysis.exception.InvalidIntervalException;
import pt.mleiria.numericalAnalysis.exception.IterationCountExceededException;
import pt.mleiria.numericalAnalysis.utils.DataAccumulator;
import pt.mleiria.numericalAnalysis.utils.Epsilon;
import pt.mleiria.numericalAnalysis.utils.Utils;

/**
 * @author Manuel Leiria
 *
 */
public class BissectionRootFinder extends AbstractRootFinder {

    private double am;

    private double bm;

    private double xm, xm1;

    /**
     * @param f a funcao a encontrar o zero
     * @param maxIters
     * @param comparisonTollerance limite de erro admissivel. A iteraccao para
     * quando o valor encontrado for menor ou igual comparisonTollerance
     * @param a coordenada x do limite inferior do intervalo
     * @param b coordenada x do limite superior do intervalo
     * @throws InvalidIntervalException
     */
    public BissectionRootFinder(OneVarFunction f, int maxIters,
            double comparisonTollerance, double a, double b)
            throws InvalidIntervalException {

        super(f, maxIters, comparisonTollerance);
        checkInterval(a, b);
        // Inicializacao
        this.am = a;
        this.bm = b;
        this.xm = b;
    }

    /**
     * @param f a funcao a encontrar o zero
     * @param maxIters o numero maximo de iteraccoes permitidas
     * @param a coordenada x do limite inferior do intervalo
     * @param b coordenada x do limite superior do intervalo
     * @throws InvalidIntervalException
     */
    public BissectionRootFinder(OneVarFunction f, int maxIters, double a,
            double b) throws InvalidIntervalException {

        super(f, maxIters, Epsilon.doubleValue());
        checkInterval(a, b);
        // Inicializacao
        this.am = a;
        this.bm = b;
        this.xm = b;
    }

    /**
     * Computa a posicao seguinte de x
     */
    public void computeNextPosition() {
        xm1 = (am + bm) * 0.5;
    }

    /**
     * Indica se o algoritmo convergiu ou nao
     *
     * @return true se convergiu, false c.c.
     */
    public boolean hasConverged() {

        if ((Math.abs(xm1 - xm) <= comparisonTollerance)
                || (f.value(xm1) <= comparisonTollerance && f.value(xm1) >= -comparisonTollerance)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * O intervalo [a, b] onde se quer encontrar o erro
     *
     * @throws IterationCountExceededException
     */
    public void findRoot() throws IterationCountExceededException {
        //Start Logger
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
        //End Logger
        checkIterationCount();
        computeNextPosition();
        n++;
        if (hasConverged()) {
            reset();
        } else {
            if (f.value(xm1) * f.value(am) < 0.0) {
                bm = xm1;
            } else {
                am = xm1;
            }
            xm = xm1;
            findRoot();
        }
    }

    /**
     * @return a raiz da funcao
     */
    public double getRoot() {
        return xm;
    }
}
