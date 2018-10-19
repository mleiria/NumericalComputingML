package pt.mleiria.numericalAnalysis.mathUtils.Function;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import pt.mleiria.machinelearning.interfaces.OneVarFunctionDerivative;

/**
 * Carrega numa tabela global as funcoes cujas raizes pretendemos encontrar
 */
public class RootFunctions {

    /**
     * tabela de funcoes global
     */
    private static final Map<String, OneVarFunctionDerivative<Double>> TABLE = new HashMap<>(32);

    // Introduz as funcoes na tabela
    static {
        enterFunctions();
    }

    /**
     * Retorna a funcao para determinada hash key
     *
     * @param key the hash key
     * @return a funcao
     */
    public static OneVarFunctionDerivative<Double> function(String key) {
        return (OneVarFunctionDerivative) TABLE.get(key);
    }

    /**
     * @return um set de todas as chaves
     */
    public static Set<String> getAllKeys() {
        return TABLE.keySet();
    }

    /**
     * Enter all the functions into the global function table.
     */
    private static void enterFunctions() {
        // Funcao f(x) = x^2 - 2
        TABLE.put("x^2 - 2", new OneVarFunctionDerivative<Double>() {
            /*
             * Implementa o interface OneVariableFunction 
             * @param double x
             * @return double
             */
            @Override
            public Double value(Double x) {
                return x * x - 2;
            }

            /**
             *
             * @param x
             * @return
             */
            @Override
            public Double derivative(Double x) {
                return 2 * x;
            }
        });
        // Funcao f(x) = x^2 - 4
        TABLE.put("x^2 - 4", new OneVarFunctionDerivative<Double>() {
            /*
             * Implementa o interface OneVariableFunction 
             * @param double x
             * @return double
             */
            @Override
            public Double value(Double x) {
                return x * x - 4;
            }

            /**
             *
             * @param x
             * @return
             */
            @Override
            public Double derivative(Double x) {
                return 2 * x;
            }
        });
        // Funcao f(x) = sin(x)
        TABLE.put("sin(x)", new OneVarFunctionDerivative<Double>() {
            /*
             * Implementa o interface OneVariableFunction @param double x
             * @return double
             */
            @Override
            public Double value(Double x) {
                return sin(x);
            }

            /**
             *
             * @param x
             * @return
             */
            @Override
            public Double derivative(Double x) {
                return cos(x);
            }
        });
    }
}
