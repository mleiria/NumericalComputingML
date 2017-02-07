package pt.mleiria.utils;

import java.util.List;

import org.apache.log4j.Logger;

import junit.framework.Assert;
import junit.framework.TestCase;
import pt.mleiria.numericalAnalysis.utils.IOUtils;
import pt.mleiria.numericalAnalysis.utils.YahooFinancials;

public class YahooFinanceTest extends TestCase{
	
	private static final Logger log = Logger.getLogger("mlearningLog");
	
	public void testYahooFinance(){
		final String path ="/media/manuel/workspace/NumericalComputingML/src/test/resources/input/";
		final String fileName = "APPLE.csv";
		//ClassLoader classLoader = getClass().getClassLoader();
		//String f = classLoader.getResource(fileName).getFile();
		List<YahooFinancials> l = IOUtils.processInputFile(path + fileName);
		
		Assert.assertEquals(21, l.size());
	}

}
