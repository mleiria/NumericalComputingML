package pt.mleiria.machinelearning.statistics;
/**
 *
 * @author manuel
 * @param <X>
 * @param <Y>
 */
@FunctionalInterface
public interface DistanceMetric<X, Y> {

    /**
     *
     * @param x
     * @param y
     * @param size
     * @return
     */
    double getRelation(X x, Y y, int size);
}
