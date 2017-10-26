package pt.mleiria.machinelearning.statistics;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import java.util.List;

/**
 *
 * @author manuel
 */
public class PearsonCorrelation implements DistanceMetric<List<Double[]>> {

    @Override
    public double getRelation(final List<Double[]> data) {
        double x = 0;
        double y = 0;
        double xx = 0;
        double yy = 0;
        double xy = 0;
        final int n = data.size();

        for (final Double[] d : data) {
            x += d[0];
            y += d[1];
            xx += d[0] * d[0];
            yy += d[1] * d[1];
            xy += d[0] * d[1];
        }
        return (n * xy - x * y) / (sqrt((n * xx - pow(x, 2)) * (n * yy - pow(y, 2))));
    }
}
