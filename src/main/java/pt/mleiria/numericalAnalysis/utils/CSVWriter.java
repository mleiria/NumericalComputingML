/**
 *
 */
package pt.mleiria.numericalAnalysis.utils;

import java.io.BufferedWriter;
import java.io.IOException;
import static java.lang.String.valueOf;
import static java.lang.String.valueOf;
import java.nio.file.Files;
import static java.nio.file.Files.newBufferedWriter;
import static java.nio.file.Files.newBufferedWriter;
import java.nio.file.Paths;
import static java.nio.file.Paths.get;
import java.util.ArrayList;
import java.util.List;

import pt.mleiria.machinelearning.matrixAlgebra.Matrix;
import pt.mleiria.machinelearning.matrixAlgebra.Vector;

/**
 * @author manuel
 *
 */
public class CSVWriter {

    final String csvFile;

    /**
     *
     * @param csvFile
     */
    public CSVWriter(final String csvFile) {
        this.csvFile = csvFile;
    }

    /**
     *
     */
    public CSVWriter() {
        this("/media/manuel/workspace/data/csvWFile.csv");
    }

    /**
     *
     * @param content
     * @throws IOException
     */
    public void writeCSVFile(List<String[]> content) throws IOException {

        try (BufferedWriter writer = newBufferedWriter(get(csvFile))) {
            for (String[] str : content) {
                for (String l : str) {
                    writer.write(l + ",");
                }
            }
        }
    }

    /**
     *
     * @param m
     * @param y
     * @throws IOException
     */
    public void writeCSVFile(final Matrix m, final Vector y) throws IOException {
        //final List<String[]> content = new ArrayList<String[]>();
        final int cols = m.columns();
        final int rows = m.rows();
        try (BufferedWriter writer = newBufferedWriter(get(csvFile))) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    writer.write(valueOf(m.component(i, j) + ","));
                }
                writer.write(valueOf(y.component(i)));
                writer.write("\n");
            }
        }
    }
}
