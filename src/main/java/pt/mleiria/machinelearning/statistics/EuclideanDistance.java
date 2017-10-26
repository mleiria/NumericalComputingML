package pt.mleiria.machinelearning.statistics;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import java.util.List;
/**
 *
 * @author manuel
 */
public class EuclideanDistance implements DistanceMetric<List<Double[]>> {

    @Override
    public double getRelation(final List<Double[]> data) {
        double res = 0;
        res = data.stream()
                  .map(d -> pow(d[0] - d[1], 2))
                  .reduce(res, (accumulator, _item) -> accumulator + _item);
        return 1.0 / (1.0 + sqrt(res));
    }
}
