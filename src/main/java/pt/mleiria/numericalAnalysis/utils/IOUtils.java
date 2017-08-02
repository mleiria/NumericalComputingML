package pt.mleiria.numericalAnalysis.utils;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import pt.mleiria.machinelearning.matrixAlgebra.Matrix;

/**
 *
 * @author mleiria
 */
public class IOUtils {

    protected static final Logger log = Logger.getLogger("mlearningLog");
    private static final String COMMA = ",";

    public static String showFileContents(final String pathToFile) throws FileNotFoundException, IOException {
	final File f = new File(pathToFile);
	final FileInputStream fis = new FileInputStream(f);
	FileChannel fchannel = fis.getChannel();
	ByteBuffer bytebuf = ByteBuffer.allocate(1024);
	fchannel.read(bytebuf);
	bytebuf.flip();
	// LOG.info("Contents of file ");
	StringBuilder sb = new StringBuilder();
	while (bytebuf.hasRemaining()) {
	    char c = (char) bytebuf.get();
	    if (c == '\n') {
		sb.append('\n');
	    } else {
		sb.append(c);
	    }

	}
	fis.close();
	log.info(sb.toString());
	return sb.toString();
    }

    /**
     * load a file for neural networks, e.g., 1,0,0 1,0,1 1,1,0
     *
     * @param pathToFile
     * @param separator
     * @return
     * @throws java.io.FileNotFoundException
     */
    public static int[][] loadFileTo2DArray(final String pathToFile, final String separator)
	    throws FileNotFoundException, IOException {

	final List<String[]> rows;
	try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
	    String line;
	    rows = new ArrayList<>();
	    if (null == separator) {
		while ((line = br.readLine()) != null) {
		    rows.add(new String[] { line });
		}
	    } else {
		while ((line = br.readLine()) != null) {
		    rows.add(line.split(separator));
		}
	    }
	}

	int[][] data = new int[rows.size()][];
	for (int i = 0; i < rows.size(); i++) {
	    final String[] sensors = rows.get(i);
	    final int colSize = sensors.length;
	    data[i] = new int[colSize];
	    for (int j = 0; j < colSize; j++) {
		data[i][j] = Integer.parseInt(sensors[j]);
	    }
	}
	return data;
    }

    public static Double[] loadFileToDoubleArray(final String pathToFile) throws FileNotFoundException, IOException {

	final List<Double> rows;
	try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
	    String line;
	    rows = new ArrayList<>();
	    while ((line = br.readLine()) != null) {
		rows.add(Double.valueOf(line));
	    }
	}
	return rows.toArray(new Double[rows.size()]);
    }

    /**
     * load a file into a matrix, 1.34,2.34,0.3 13.45,0.2231,1.33
     *
     * @param pathToFile
     * @param separator
     * @return
     * @throws java.io.FileNotFoundException
     */
    public static double[][] loadFileToComponents(final String pathToFile, final String separator)
	    throws FileNotFoundException, IOException {

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
	    final String[] sensors = rows.get(i);
	    final int colSize = sensors.length;
	    data[i] = new double[colSize];
	    for (int j = 0; j < colSize; j++) {
		data[i][j] = Double.parseDouble(sensors[j]);
	    }
	}
	return data;
    }

    /**
     * load a file for neural networks, e.g., 1,0,1
     *
     * @param pathToFile
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static int[] loadFileToArray(final String pathToFile) throws FileNotFoundException, IOException {

	String[] tmp;
	try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
	    String line;
	    tmp = null;
	    if ((line = br.readLine()) != null) {
		tmp = line.split(",");
	    }
	}
	final int arrSize = tmp.length;
	int[] arr = new int[arrSize];
	for (int i = 0; i < arrSize; i++) {
	    arr[i] = Integer.parseInt(tmp[i]);
	}
	return arr;
    }

    /**
     * 
     * @param fileName
     * @return
     */
    public int[] loadFileToIntArrayFromResource(final String fileName) {
	ClassLoader classLoader = getClass().getClassLoader();
	final File file = new File(classLoader.getResource(fileName).getFile());
	int[] data = null;
	try {
	    int numLines = lineCounter(file);
	    data = new int[numLines];
	    int i = 0;
	    try (Scanner scanner = new Scanner(file)) {
		while (scanner.hasNextLine()) {
		    data[i] = scanner.nextInt();
		    i++;
		}
	    }
	} catch (IOException ex) {
	    Logger.getLogger(IOUtils.class.getName()).log(Level.SEVERE, null, ex);
	}
	return data;
    }

    /**
     * 
     * @param file
     * @return Number of lines in file
     * @throws FileNotFoundException
     */
    private int lineCounter(File file) throws FileNotFoundException {
	int lineCounter = 0;
	try (Scanner scanner = new Scanner(file)) {
	    while (scanner.hasNextLine()) {
		lineCounter++;
		scanner.next();
		// LOG.log(Level.INFO, "Line:Value{0}:{1}", new
		// Object[]{lineCounter, scanner.nextInt()});
	    }
	}
	return lineCounter;
    }

    /**
     *
     * @param hex
     * @return
     */
    public static String fromHexaToString(String hex) {
	final StringBuilder output = new StringBuilder();
	for (int i = 0; i < hex.length(); i += 2) {
	    String str = hex.substring(i, i + 2);
	    output.append((char) Integer.parseInt(str, 16));
	}
	System.out.println(output);
	return output.toString();
    }

    /**
     *
     * @param freqFile
     * @param separator
     * @return
     */
    public Map<Object, Number> loadFreqFromCsv(final String freqFile, final char separator) {

	final Map<Object, Number> freq = new HashMap<>();

	// Get file from resources folder
	ClassLoader classLoader = getClass().getClassLoader();
	File file = new File(classLoader.getResource(freqFile).getFile());
	try {
	    try (Scanner scanner = new Scanner(file)) {
		while (scanner.hasNextLine()) {
		    String line = scanner.nextLine();
		    log.info(line);
		    String[] f = line.split(String.valueOf(separator));
		    freq.put(f[0], Double.parseDouble(f[1]));

		}
	    }
	} catch (IOException ex) {
	    Logger.getLogger(IOUtils.class.getName()).log(Level.SEVERE, null, ex);
	}
	return freq;
    }

    /**
     *
     * @param fname
     * @param a
     */
    public static void saveArrayToFile(final String fname, final double[] a) {
	try {
	    File file = new File(fname);

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

	    System.out.println("Done");

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    /**
     * 
     * @param fname
     * @param lst a List<Double[2]>
     */
    public static void saveToFile(final String fname, final List<Double[]> lst) {
	try {
	    File file = new File(fname);

	    // if file doesnt exists, then create it
	    if (!file.exists()) {
		file.createNewFile();
	    }

	    FileWriter fw = new FileWriter(file.getAbsoluteFile());
	    try (BufferedWriter bw = new BufferedWriter(fw)) {
		
		for(Double[] dA : lst){
		    bw.write(dA[0] + "," + dA[1] + "\n");
		}
	    }

	    System.out.println("Done");

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /**
     * 
     * @param fname
     * @param a
     * @param featureName
     * @param featureType
     */
    public void saveArrayToFile(final String fileName, final double[] a, String featureName, String featureType) {
	try {
	    File file = new File(fileName);

	    // if file doesnt exists, then create it
	    if (!file.exists()) {
		file.createNewFile();
	    }

	    FileWriter fw = new FileWriter(file.getAbsoluteFile());
	    try (BufferedWriter bw = new BufferedWriter(fw)) {
		bw.write("iterNum" + "," + featureName + "\n");
		bw.write("NUMERIC" + "," + featureType + "\n");
		for (int i = 0; i < a.length; i++) {
		    bw.write(i + "," + a[i] + "\n");
		}
	    }

	    System.out.println("Done");

	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Load a file in Yahoo financial format
     * 
     * @param inputFilePath
     * @return
     */
    public static List<YahooFinancials> processInputFile(String inputFilePath) {

	List<YahooFinancials> inputList = new ArrayList<YahooFinancials>();

	try {
	    File inputF = new File(inputFilePath);
	    InputStream inputFS = new FileInputStream(inputF);
	    BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));
	    // skip the header of the csv
	    inputList = br.lines().skip(1).map(mapToItem).collect(Collectors.toList());
	    br.close();

	} catch (IOException e) {
	    e.printStackTrace();
	}
	return inputList;
    }

    /**
     * Load a file in Yahoo financial format
     * 
     * @param inputFilePath
     * @return
     */
    public static List<YahooFinancials> processInputFile(File inputF) {

	List<YahooFinancials> inputList = new ArrayList<YahooFinancials>();

	try {
	    InputStream inputFS = new FileInputStream(inputF);
	    BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));
	    // skip the header of the csv
	    inputList = br.lines().skip(1).map(mapToItem).collect(Collectors.toList());
	    br.close();

	} catch (IOException e) {
	    e.printStackTrace();
	}
	return inputList;
    }

    private static Function<String, YahooFinancials> mapToItem = (line) -> {

	final String[] p = line.split(COMMA);// a CSV has comma separated lines

	final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	Date date = null;
	try {
	    date = df.parse(p[0]);
	} catch (ParseException e) {
	    e.printStackTrace();
	}
	final YahooFinancials item = new YahooFinancials(date, Double.parseDouble(p[1]), Double.parseDouble(p[2]),
		Double.parseDouble(p[3]), Double.parseDouble(p[4]), Double.parseDouble(p[5]), Double.parseDouble(p[6]));

	return item;

    };

    /**
     * 
     * @param lst
     * @return
     */
    public static Matrix loadMatrix(final List<String[]> lst) {
	final int rows = lst.size();
	final int cols = lst.get(0).length;
	final double[][] components = new double[rows][cols];
	for (int i = 0; i < rows; i++) {
	    for (int j = 0; j < cols; j++) {
		components[i][j] = Double.parseDouble(lst.get(i)[j]);
	    }
	}
	return new Matrix(components);
    }

}
