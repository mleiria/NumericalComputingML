/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.mleiria.machinelearning.statistics;

import static java.lang.Math.abs;
import java.util.List;

/**
 *
 * @author manuel
 */
public class ManhattanDistance implements DistanceMetric<List<Double[]>> {

    @Override
    public double getRelation(List<Double[]> data) {
        double res = 0;
        res = data.stream()
                .map(d -> abs(d[0] - d[1]))
                .reduce(res, (accumulator, _item) -> accumulator + _item);
        return res;
    }

}
