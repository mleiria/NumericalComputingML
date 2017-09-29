/**
 * NOTA: Condicao suficiente de convergencia do metodo: f e continua em [a, b] e
 * tal que f(a)f(b) < 0
 */
package pt.mleiria.numericalAnalysis.nonLinearEquation.rootFinder;

import static java.lang.Math.abs;
import static java.lang.String.valueOf;
import static java.lang.String.valueOf;
import pt.mleiria.machinelearning.interfaces.OneVarFunction;
import pt.mleiria.numericalAnalysis.exception.InvalidIntervalException;
import pt.mleiria.numericalAnalysis.exception.IterationCountExceededException;
import pt.mleiria.numericalAnalysis.utils.DataAccumulator;
import static pt.mleiria.numericalAnalysis.utils.DataAccumulator.feedAccumulator;
import pt.mleiria.numericalAnalysis.utils.Epsilon;
import static pt.mleiria.numericalAnalysis.utils.Epsilon.doubleValue;
import pt.mleiria.numericalAnalysis.utils.Utils;
import static pt.mleiria.numericalAnalysis.utils.Utils.formataNumero;
import static pt.mleiria.numericalAnalysis.utils.Utils.print;
import static pt.mleiria.numericalAnalysis.utils.Utils.println;

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

        super(f, maxIters, doubleValue());
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

        if ((abs(xm1 - xm) <= comparisonTollerance)
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
