/**
 * 
 */
package pt.mleiria.machinelearning.classification;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import pt.mleiria.machinelearning.matrixAlgebra.Matrix;
import pt.mleiria.machinelearning.statistics.ColumnStatistics;

/**
 * @author manuel
 *
 */
public class Bayes {

	protected static final Logger log = Logger.getLogger("mlearningLog");
	/**
	 * 
	 */
	private final String dataFile;
	/**
	 * 
	 */
	private double split;
	/**
	 * 
	 */
	private Matrix trainingSet;
	/**
	 * 
	 */
	private Matrix testSet;
	/**
	 * 
	 */
	private Matrix dataSet;
	/**
	 * 
	 * @param dataFile
	 */
	public Bayes(final String dataFile) {
		this.dataFile = dataFile;
	}
	/**
	 * 
	 * @param dataFile
	 * @param split
	 */
	public Bayes(final String dataFile, final double split) {
		this(dataFile);
		this.split = split;
	}

	public void loadDataSet() {
		String line = "";
		final String cvsSplitBy = ",";
		final List<String[]> tmpArray = new ArrayList<String[]>();

		try (BufferedReader br = new BufferedReader(new FileReader(dataFile))) {

			while ((line = br.readLine()) != null) {
				tmpArray.add(line.split(cvsSplitBy));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		final double[][] components = new double[tmpArray.size()][];
		for (int i = 0; i < tmpArray.size(); i++) {
			final String[] row = tmpArray.get(i);
			components[i] = new double[row.length];
			for (int j = 0; j < row.length; j++) {
				components[i][j] = Double.parseDouble(row[j]);
			}

		}
		dataSet = new Matrix(components);
		if (split != 0) {
			splitDataSet(dataSet);
		}
	}

	/**
	 * 
	 * @param dataSet
	 */
	private void splitDataSet(Matrix dataSet) {
		final Matrix[] m = dataSet.trainTestSplit(split, true);
		trainingSet = m[0];
		testSet = m[1];
		log.info("TrainingSet:\n" + trainingSet.toString());
		log.info("TestSet:\n" + testSet.toString());
	}
	/**
	 * 
	 * @param dataSet
	 * @return
	 */
	public Map<Integer, Matrix> separateByClass(Matrix dataSet){
		final Map<Integer, List<Double[]>> separated = new HashMap<Integer, List<Double[]>>();
		final int rows = dataSet.rows();
		final int cols = dataSet.columns();
		for(int i = 0; i < rows; i++){
			final Integer key = (int) dataSet.component(i, cols - 1);
			final Double[] data = new Double[cols];
			List<Double[]> lst;
			if(separated.containsKey(key)){
				lst = separated.get(key);
			}else{
				lst = new ArrayList<Double[]>();
			}
			for(int j = 0; j < cols; j++){
				data[j] = dataSet.component(i, j);
			}
			lst.add(data);
			separated.put(key,lst);
		}
		final Map<Integer, Matrix> separatedMatrix = new  HashMap<Integer, Matrix>();
		for (Map.Entry<Integer, List<Double[]>> entry : separated.entrySet()) {
			separatedMatrix.put(entry.getKey(), convertToMatrix(entry.getValue()));
		}
		
		return separatedMatrix;
	}
	
	private Matrix convertToMatrix(List<Double[]> lst){
		final int rows = lst.size();
		final int cols = lst.get(0).length;
		final double components[][] = new double[rows][cols];
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				components[i][j] = lst.get(i)[j];
			}
		}
		return new Matrix(components);
	}
	/**
	 * For each column in Matrix calculates avg and stdev.
	 * The structure is already divided by class
	 * 
	 * @param map
	 * @return a Map where the key is the class and the value is
	 * a Matrix(2xn) where Matrix(0,i) is the avg of col i and Matrix(1, i)
	 * is the stdev of col i
	 */
	public Map<Integer, Matrix> getAvgStdev(final Map<Integer, Matrix> map){
		
		Map<Integer, Matrix> summaryByClass = new HashMap<Integer, Matrix>();
		for(final Map.Entry<Integer, Matrix> entry : map.entrySet()){
			final List<Double[]> avgStdev = new ArrayList<Double[]>();
			final Matrix m = entry.getValue();
			double[][] components = new double[2][m.columns() - 1];
			for(int i = 0; i < m.columns() - 1; i++){
				final ColumnStatistics cs = new ColumnStatistics(m.getColumn(i));
				final double avg = cs.getAverage();
				final double stdev = cs.getStandardDeviation();
				components[0][i] = avg;
				components[1][i] = stdev;
			}
			summaryByClass.put(entry.getKey(), new Matrix(components));
		}
		return summaryByClass;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Bayes b = new Bayes("/home/manuel/Tools/adalineProcesses/mlearning/bayes/pima-indians-diabetes.data.csv", 0.67);
		b.loadDataSet();
		Map<Integer, Matrix> tmp = b.separateByClass(b.dataSet);
		
	}

}
