/**
 *
 */
package pt.mleiria.numericalAnalysis.nonLinearEquation.rootFinder;

import static java.lang.String.valueOf;
import java.util.logging.Level;
import static java.util.logging.Level.INFO;
import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;
import pt.mleiria.machinelearning.interfaces.OneVarFunction;
import pt.mleiria.machinelearning.interfaces.OneVarFunctionDerivative;
import pt.mleiria.numericalAnalysis.exception.InvalidIntervalException;
import pt.mleiria.numericalAnalysis.exception.IterationCountExceededException;

/**
 * @author Manuel
 */
public abstract class AbstractRootFinder implements RootFinder {

    private final Logger logger = getLogger(this.getClass().getName());
    //Funcao para a qual pretendemos encontrar a raiz
    protected OneVarFunction<Double> f;
    protected OneVarFunctionDerivative<Double> df;
    //Contador de iteraccoes
    protected int n;
    //Numero maximo de iteraccoes
    protected int maxIters;
    //Erro maximo admissivel
    protected double comparisonTollerance;

    /**
     * @param f
     * @param maxIters
     * @param comparisonTollerance
     */
    public AbstractRootFinder(OneVarFunction f, int maxIters, double comparisonTollerance) {
        this.f = f;
        this.maxIters = maxIters;
        this.comparisonTollerance = comparisonTollerance;
        this.n = 0;
    }

    /**
     * @param df
     * @param maxIters
     * @param comparisonTollerance
     */
    public AbstractRootFinder(OneVarFunctionDerivative df, int maxIters, double comparisonTollerance) {
        this.df = df;
        this.f = df;
        this.maxIters = maxIters;
        this.comparisonTollerance = comparisonTollerance;
        this.n = 0;
    }

    /**
     * Verifica o intervalo [a, b]
     *
     * @param a coordenada x do limite inferior do intervalo
     * @param b coordenada x do limite superior do intervalo
     * @throws InvalidIntervalException
     */
    public void checkInterval(double a, double b) throws InvalidIntervalException {
        logger.log(INFO, "a = {0}, b = {1}", new Object[]{a, b});
        logger.log(INFO, "f(a) = {0}, f(b) = {1}", new Object[]{f.value(a), f.value(b)});
        logger.log(INFO, "f(a)*f(b) = {0}", valueOf(f.value(a) * f.value(b)));
        //O intervalo e invalido se f(a) tiver o mesmo sinal que f(b)
        if (f.value(a) * f.value(b) > 0) {
            throw new InvalidIntervalException();
        }
    }

    /**
     * Retorna a iteracao corrente
     *
     * @return a contagem
     */
    public int getIterationCount() {
        return n;
    }

    /**
     * Verifica se a contagem excedeu o numero maximo de iteraccoes
     *
     * @throws IterationCountExceededException
     */
    protected void checkIterationCount()
            throws IterationCountExceededException {
        if (n > maxIters) {
            throw new IterationCountExceededException();
        }
    }

    /**
     * Reset.
     */
    protected void reset() {
        n = 0;
    }

    /**
     * O procedimento iteractivo
     */
    public abstract void findRoot() throws IterationCountExceededException;

    /**
     * Computa a posicao seguinte de x
     */
    public abstract void computeNextPosition();

    /**
     *
     * Indica se o algoritmo convergiu
     *
     * @return true se sim, false c.c.
     */
    public abstract boolean hasConverged();

}
