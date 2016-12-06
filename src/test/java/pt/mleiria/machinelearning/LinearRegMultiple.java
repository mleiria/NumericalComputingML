package pt.mleiria.machinelearning;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pt.mleiria.machinelearning.matrixAlgebra.Matrix;
import pt.mleiria.machinelearning.matrixAlgebra.Vector;
import pt.mleiria.machinelearning.preprocess.FeatNormMeanStdev;
import pt.mleiria.machinelearning.preprocess.FeatureNormalization;
import pt.mleiria.machinelearning.regression.GradientDescent;

/**
 *
 * @author manuel
 */
public class LinearRegMultiple {

    private static final String path = "/home/manuel/tools/adalineProcesses/mlearning/dataFiles/";
    private final static String dataFile = path + "housePrice.txt";
    private final static String costHistory = path +"JGDM.txt";

    public static void main(String[] args) {
        try {
            double[][] rawData = loadFileToComponents(dataFile, ",");
            Matrix matrix = new Matrix(rawData);
            //We can check the data loaded
            System.out.println(matrix.toString());
            //split the matrix in x (features) and y (target value)
            Matrix[] dMatrix = matrix.split(1);
            //Let's see:
            Matrix x = dMatrix[0];
            Vector y = dMatrix[1].getColumn(0);
            System.out.println(x.toString());
            System.out.println(y.toString());
            //Feature normalization
            final FeatureNormalization ftn = new FeatNormMeanStdev();
            final Matrix xNorm = ftn.normalize(x);
            //Check it
            System.out.println(xNorm.toString());
            //alpha value
            double alpha = 0.01;
            //Num max iter
            int numIter = 400;
            //desired precision
            double precision = 0.00001;
            GradientDescent gd = new GradientDescent(xNorm, y, alpha);
            gd.setMaximumIterations(numIter);
            gd.setDesiredPrecision(precision);
            gd.evaluate();
            saveArrayToFile(costHistory, gd.getCostHistory());
            
            double x1 = 140.0;
            double x2 = 3;
            double yPrev = gd.getTheta().component(0) + 
                    ((x1 - ((FeatNormMeanStdev)ftn).getMean()[0])/
                    ((FeatNormMeanStdev)ftn).getStdev()[0]) * gd.getTheta().component(1) +
                    ((x2 - ((FeatNormMeanStdev)ftn).getMean()[1])/
                    ((FeatNormMeanStdev)ftn).getStdev()[1]) * gd.getTheta().component(2);
            System.out.println("Previsao:" + yPrev);

        } catch (Exception ex) {
            Logger.getLogger(LinearRegMultiple.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * load a file in csv style into a matrix, 1.34,2.34,0.3 13.45,0.2231,1.33
     *
     * @param pathToFile
     * @param separator
     * @return
     * @throws java.io.FileNotFoundException
     */
    public static double[][] loadFileToComponents(final String pathToFile, final String separator) throws FileNotFoundException, IOException {
        final List<String[]> rows;
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            rows = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                rows.add(line.split(separator));
            }
        }
        double[][] data = new double[rows.size()][];
        for (int i = 0; i < rows.size(); i++) {
            final String[] rowArr = rows.get(i);
            final int colSize = rowArr.length;
            data[i] = new double[colSize];
            for (int j = 0; j < colSize; j++) {
                data[i][j] = Double.parseDouble(rowArr[j]);
            }
        }
        return data;
    }
    
    /**
     * 
     * @param fname
     * @param a
     * @param featureName
     * @param featureType 
     */
    public static void saveArrayToFile(final String fname, final double[] a) {
        try {
            final File file = new File(fname);
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            try (BufferedWriter bw = new BufferedWriter(fw)) {
                for (int i = 0; i < a.length; i++) {
                    bw.write(i + "," + a[i] + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
