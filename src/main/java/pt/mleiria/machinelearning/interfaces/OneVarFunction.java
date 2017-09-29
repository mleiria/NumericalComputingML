package pt.mleiria.machinelearning.interfaces;

/**
 *
 * @author manuel
 */
public interface OneVarFunction<T> {

    /**
     * Returns the value of the function for the specified variable value.
     *
     * @param <T>
     * @param x
     * @return a function of the form f(x)
     */
    public T value(T x);

}
