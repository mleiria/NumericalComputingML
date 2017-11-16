/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.machinelearning.statistics;

import static java.lang.Math.abs;
import pt.mleiria.machinelearning.matrixAlgebra.Vector;

/**
 *
 * @author manuel
 */
public class ManhattanDistance implements DistanceMetric<Vector, Vector> {

    @Override
    public double getRelation(final Vector x, final Vector y, final int len) {
        double res = 0;
        for (int i = 0; i < len; i++) {
            res += abs(x.component(i) - y.component(i));
        }
        return res;
    }

}
