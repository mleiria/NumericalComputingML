/**
 *
 */
package pt.mleiria.numericalAnalysis.nonLinearEquation.rootFinder;

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
 * @author Manuel
 *
 */
public class ImprovedRegulaFalsiRootFinder extends RegulaFalsiRootFinder {

    protected double positionF;
    protected double positionG;

    /**
     * @param f a funcao a encontrar o zero
     * @param maxIters o numero maximo de iteraccoes admitidas
     * @param comparisonTollerance limite de erro admissivel. A iteraccao para
     * quando o valor encontrado for <= comparisonTollerance @param a coordenad
     * a x do limite inferior do intervalo
     * @param b coordenada x do limite superior do intervalo
     * @throws InvalidIntervalException
     */
    public ImprovedRegulaFalsiRootFinder(OneVarFunction<Double> f, int maxIters,
            double comparisonTollerance, double a, double b)
            throws InvalidIntervalException {
        super(f, maxIters, comparisonTollerance, a, b);
        //Inicializacao
        this.positionF = f.value(a);
        this.positionG = f.value(b);
    }

    /**
     * @param f a funcao a encontrar o zero
     * @param maxIters o numero maximo de iteraccoes permitidas
     * @param a coordenada x do limite inferior do intervalo
     * @param b coordenada x do limite superior do intervalo
     * @throws InvalidIntervalException
     */
    public ImprovedRegulaFalsiRootFinder(OneVarFunction<Double> f, int maxIters, double a, double b)
            throws InvalidIntervalException {
        super(f, maxIters, a, b);
        //Inicializacao
        this.positionF = f.value(a);
        this.positionG = f.value(b);
    }

    /**
     * Computa a posicao seguinte de x
     */
    public void computeNextPosition() {

        xm1 = bm - positionG * ((bm - am) / (positionG - positionF));
    }

    /**
     * O intervalo [a, b] onde se quer encontrar o erro
     *
     * @throws IterationCountExceededException
     */
    public void findRoot() throws IterationCountExceededException {
        // Start Logger
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
            clearFile("ImprovedRegulaFalsi.txt");
            appendToFile("ImprovedRegulaFalsi.txt", true,
                    getData());
            DataAccumulator.reset();
            // End Logger
            reset();
        } else {
            if (f.value(xm1) * f.value(am) < 0.0) {
                bm = xm1;
                positionG = f.value(xm1);
                if (f.value(xm) * f.value(xm1) > 0) {
                    positionF /= 2;
                }
            } else {
                am = xm1;
                positionF = f.value(xm1);
                if (f.value(xm) * f.value(xm1) > 0) {
                    positionG /= 2;
                }
            }
            xm = xm1;
            findRoot();
        }
    }

}
