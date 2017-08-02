/**
 * 
 */
package pt.mleiria.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pt.mleiria.machinelearning.matrixAlgebra.Matrix;
import pt.mleiria.numericalAnalysis.utils.IOUtils;

/**
 * @author manuel
 *
 */
public class FileLoader {

    private final File file;

    public FileLoader(final String fileName) {
	this.file = getFileResource(fileName);
    }

    /**
     * 
     * @param fileName
     * @return
     */
    private File getFileResource(final String fileName) {
	ClassLoader classLoader = getClass().getClassLoader();
	File file = new File(classLoader.getResource(fileName).getFile());
	return file;
    }

    /**
     * 
     * @param fileName
     * @return
     */
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

    /**
     * 
     * @param fileName
     * @param ignoreFirstRow
     * @param separator
     * @return
     */
    public Matrix getFileCsvToMatrix(boolean ignoreFirstRow, final String separator) {
	int numRows = 0;
	int numCols = 0;
	try {
	    Scanner scanner = new Scanner(file);
	    while (scanner.hasNextLine()) {
		if (ignoreFirstRow) {
		    String[] line = scanner.nextLine().split(separator);
		    numCols = line.length;
		    ignoreFirstRow = false;

		} else {
		    scanner.nextLine();
		    numRows++;
		}
	    }
	    scanner.close();
	    scanner = new Scanner(file);
	    final double[][] components = new double[numRows][numCols];
	    int currentRow = 0;
	    while (scanner.hasNextLine()) {
		if (!ignoreFirstRow) {
		    ignoreFirstRow = true;
		    scanner.nextLine();
		} else {
		    String[] line = scanner.nextLine().split(",");
		    for (int i = 0; i < line.length; i++) {
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

    /**
     * 
     * @param csvFile
     * @param csvSplitBy
     * @return
     */
    public List<String[]> readCSVFile(final String csvSplitBy) {
	BufferedReader br = null;
	String line = "";
	List<String[]> res = new ArrayList<>();

	try {
	    br = new BufferedReader(new FileReader(file));
	    while ((line = br.readLine()) != null) {
		String[] row = line.split(csvSplitBy);
		res.add(row);
	    }
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    if (br != null) {
		try {
		    br.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	}
	return res;
    }

    /**
     * 
     * @param csvFile
     * @param csvSplitBy
     * @param numColsToRead
     * @return
     */
    public List<String[]> readCSVFile(final String csvSplitBy, final int numColsToRead) {
	BufferedReader br = null;
	String line = "";
	List<String[]> res = new ArrayList<>();

	try {
	    br = new BufferedReader(new FileReader(file));
	    while ((line = br.readLine()) != null) {
		final String[] row = line.split(csvSplitBy);
		final String[] subRow = new String[numColsToRead];
		System.arraycopy(row, 0, subRow, 0, numColsToRead);
		res.add(subRow);
	    }
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    if (br != null) {
		try {
		    br.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	}
	return res;
    }

    public static void main(String[] args) {
	final FileLoader fl = new FileLoader("input/100MetrosOlymp.csv");
	final List<String[]> lst = fl.readCSVFile(",", 2);
	final Matrix m = IOUtils.loadMatrix(lst);
	System.out.println(m.toString());
    }
}
