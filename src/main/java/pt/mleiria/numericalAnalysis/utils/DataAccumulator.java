/**
 *
 */
package pt.mleiria.numericalAnalysis.utils;

import java.util.ArrayList;

/**
 * @author Manuel
 *
 */
public class DataAccumulator {

    private static ArrayList<String[][]> accumulator;

    static {
        accumulator = new ArrayList<>();
    }

    public static void feedAccumulator(String[][] data) {
        accumulator.add(data);
    }

    public static ArrayList<String[][]> getData() {
        return accumulator;
    }

    public static void reset() {
        accumulator = new ArrayList<>();
    }

}
