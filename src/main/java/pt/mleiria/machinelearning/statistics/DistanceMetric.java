package pt.mleiria.machinelearning.statistics;
/**
 *
 * @author manuel
 * @param <T>
 */
@FunctionalInterface
public interface DistanceMetric<T> {

    /**
     *
     * @param t
     * @return
     */
    double getRelation(T t);
}
