package pt.mleiria.machinelearning.interfaces;

/**
 *
 * @author manuel
 */
public interface OneVarFunctionDerivative extends OneVarFunction {

    /**
     * Retorna o valor da derivada da funcao para o valor especificado da
     * variavel
     *
     * @param x o valor par o qual se quer calcular df(x)/dx
     * @return df(x)/dx
     */
    public double derivative(double x);

}
