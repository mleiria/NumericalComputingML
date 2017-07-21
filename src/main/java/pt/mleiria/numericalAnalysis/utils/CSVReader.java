package pt.mleiria.numericalAnalysis.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pt.mleiria.machinelearning.regression.LinearRegression;

public class CSVReader {
	
	private final String csvFile;
	
	public CSVReader(final String csvFile) {
		this.csvFile = csvFile;
	}
	
	public CSVReader() {
		this("/media/manuel/workspace/BookWrite/csvRFile.csv");
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
			br = new BufferedReader(new FileReader(csvFile));
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
	
		
	public static void main(String[] args) {
		
		CSVReader reader = new CSVReader();
		List<String[]> data = reader.readCSVFile(",");
		//System.out.println(ViewUtils.showArrayPyFormat(data,2));
		LinearRegression lrM = new LinearRegression();
		LinearRegression lrF = new LinearRegression();
		for(String[] row : data){
			if(row[2].equals("x"))
				continue;
			lrM.add(Double.parseDouble(row[0]), Double.parseDouble(row[2]));
			lrF.add(Double.parseDouble(row[0]), Double.parseDouble(row[3]));
			//System.out.println(row[0] + ":" + row[3]);
		}
		System.out.println("M: " + "t = " + lrM.getIntercept() + " + " + lrM.getSlope() + "x");
		System.out.println("F: " + "t = "  + lrF.getIntercept() + " + " + lrF.getSlope() + "x");
		System.out.println("M: " + lrM.value(1989));
		System.out.println("F: " + lrF.value(1989));
		System.out.println(lrM.value(1989) + lrF.value(1989));
		
	}
}

