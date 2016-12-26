/**
 * 
 */
package pt.mleiria.machinelearning.classification;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pt.mleiria.machinelearning.matrixAlgebra.Matrix;
import pt.mleiria.machinelearning.preprocess.ConvertToNumericDummy;

/**
 * @author manuel
 *
 */
public class KNN {
	/**
	 * 
	 */
	private final String dataFile;
	/**
	 * 
	 */
	private final double split;
	/**
	 * 
	 */
	private Matrix trainingSet;
	/**
	 * 
	 */
	private Matrix testSet;

	private final ConvertToNumericDummy ctd;

	/**
	 * 
	 * @param dataFile
	 * @param split
	 */
	public KNN(final String dataFile, final double split) {
		this.dataFile = dataFile;
		this.split = split;
		ctd = new ConvertToNumericDummy();
	}

	/**
	 * 
	 * @param dataFile
	 */
	public KNN(final String dataFile) {
		this(dataFile, 0.66);
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
		double[][] components = new double[tmpArray.size()][5];
		for (int i = 0; i < tmpArray.size(); i++) {
			String[] row = tmpArray.get(i);
			for(int j = 0; j < row.length - 1; j++){
				components[i][j] = Double.parseDouble(row[j]);
			}
			components[i][row.length - 1] = ctd.put(row[row.length - 1]);
		}
		
		Matrix m =  new Matrix(components);
		System.out.println(m.toString());
	}

	public static void main(String[] args){
		KNN k = new KNN("/home/manuel/tools/adalineProcesses/mlearning/knn/iris.data");
		k.loadDataSet();
	}
}
