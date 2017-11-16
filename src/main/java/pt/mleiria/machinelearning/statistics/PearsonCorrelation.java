package pt.mleiria.machinelearning.statistics;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import pt.mleiria.machinelearning.matrixAlgebra.Vector;

/**
 *
 * @author manuel
 */
public class PearsonCorrelation implements DistanceMetric<Vector, Vector> {

    @Override
    public double getRelation(final Vector a, final Vector b, final int len) {
        double x = 0;
        double y = 0;
        double xx = 0;
        double yy = 0;
        double xy = 0;
        for (int i = 0; i < len; i++) {
            x += a.component(i);
            y += b.component(i);
            xx += x * x;
            yy += y * y;
            xy += x * y;
        }

        return (len * xy - x * y) / (sqrt((len * xx - pow(x, 2)) * (len * yy - pow(y, 2))));
    }
}
