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
		List<YahooFinancials> l = IOUtils.processInputFile(new FileLoader().getFileResource("input/APPLE.csv"));
		for(YahooFinancials yf : l){
			log.info(yf.getDate() + ":" + yf.getAdjClose());
		}
		Assert.assertEquals(21, l.size());
	}

}
