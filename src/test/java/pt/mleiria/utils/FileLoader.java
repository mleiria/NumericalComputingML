/**
 * 
 */
package pt.mleiria.utils;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import pt.mleiria.machinelearning.matrixAlgebra.Matrix;

/**
 * @author manuel
 *
 */
public class FileLoader {

	public String getFileToString(String fileName) {

		StringBuilder result = new StringBuilder("");

		// Get file from resources folder
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());

		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				result.append(line).append("\n");
			}

			scanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return result.toString();

	}
	public Matrix getFileCsvToMatrix(String fileName, boolean ignoreFirstRow, final String separator){
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());
		int numRows = 0;
		int numCols = 0;
		Matrix m;
		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				if(ignoreFirstRow){
					String[] line = scanner.nextLine().split(separator);
					numCols = line.length;
					ignoreFirstRow = false;
					
				}else{
					scanner.nextLine();
					numRows++;
				}
			}
			scanner.close();
			scanner = new Scanner(file);
			final double[][] components = new double[numRows][numCols];
			int currentRow = 0;
			while (scanner.hasNextLine()) {
				if(!ignoreFirstRow){
					ignoreFirstRow = true;
					scanner.nextLine();
				}else{
					String[] line = scanner.nextLine().split(",");
					for(int i = 0; i < line.length; i++){
						components[currentRow][i] = Double.parseDouble(line[i]);
					}
					currentRow++;
				}
			}
			scanner.close();
			return new Matrix(components);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
