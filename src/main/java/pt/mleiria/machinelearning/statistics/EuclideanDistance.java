package pt.mleiria.machinelearning.statistics;

import pt.mleiria.machinelearning.matrixAlgebra.Vector;
/**
 *
 * @author manuel
 */
public class EuclideanDistance implements DistanceMetric<Vector, Vector>{

    @Override
    public double getRelation(final Vector x, final Vector y, final int len) {
        double res = 0.0;
        for(int i = 0; i < len; i++){
            res += Math.pow(x.component(i) - y.component(i), 2);
        }
        return Math.sqrt(res);
    }



}