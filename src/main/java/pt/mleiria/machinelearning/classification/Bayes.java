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
import pt.mleiria.numericalAnalysis.utils.ViewUtils;

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
	public Map<Integer, List<Double[]>> separateByClass(Matrix dataSet){
		Map<Integer, List<Double[]>> separated = new HashMap<Integer, List<Double[]>>();
		final int rows = dataSet.rows();
		final int cols = dataSet.columns();
		for(int i = 0; i < rows; i++){
			final Integer key = (int) dataSet.component(i, cols - 1);
			final Double[] data = new Double[cols - 1];
			List<Double[]> lst;
			if(separated.containsKey(key)){
				lst = separated.get(key);
			}else{
				lst = new ArrayList<Double[]>();
			}
			for(int j = 0; j < cols - 1; j++){
				data[j] = dataSet.component(i, j);
			}
			lst.add(data);
			separated.put(key,lst);
		}
		log.info(ViewUtils.showSeparatedByClass(separated));
		return separated;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Bayes b = new Bayes("/home/manuel/tools/adalineProcesses/mlearning/bayes/pima-indians-diabetes.data.csv", 0.67);
		b.loadDataSet();
		Map<Integer, List<Double[]>> tmp = b.separateByClass(b.dataSet);
		
	}

}
