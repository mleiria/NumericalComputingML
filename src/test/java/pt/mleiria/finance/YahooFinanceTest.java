/**
 * 
 */
package pt.mleiria.finance;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import junit.framework.Assert;
import junit.framework.TestCase;
import pt.mleiria.numericalAnalysis.utils.IOUtils;
import pt.mleiria.numericalAnalysis.utils.YahooFinancials;
import pt.mleiria.utils.FileLoader;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;
import yahoofinance.quotes.fx.FxQuote;
import yahoofinance.quotes.fx.FxSymbols;

/**
 * @author manuel
 *
 */
public class YahooFinanceTest extends TestCase{
	private static final Logger log = Logger.getLogger("mlearningLog");
	
	public void testSingleQuote(){
		Stock stock = YahooFinance.get("AAPL");
		 
		BigDecimal price = stock.getQuote().getPrice();
		BigDecimal change = stock.getQuote().getChangeInPercent();
		BigDecimal peg = stock.getStats().getPeg();
		BigDecimal dividend = stock.getDividend().getAnnualYieldPercent();
		 
		stock.print();
		Assert.assertEquals("AAPL", stock.getSymbol());
	}
	
	public void testMultipleStocks(){
		String[] symbols = new String[] {"INTC", "BABA", "TSLA", "AIR.PA", "YHOO"};
		Map<String, Stock> stocks = YahooFinance.get(symbols); // single request
		Stock intel = stocks.get("INTC");
		Stock airbus = stocks.get("AIR.PA");
		
		intel.print();
		airbus.print();
		Assert.assertEquals(5, stocks.size());
	}
	
	public void testFXQuote(){
		FxQuote usdeur = YahooFinance.getFx(FxSymbols.USDEUR);
		FxQuote usdgbp = YahooFinance.getFx("USDGBP=X");
		log.info(usdeur);
		log.info(usdgbp);
		Assert.assertEquals("USDEUR=X", usdeur.getSymbol());
	}
	
	public void testSingleStockHistorical(){
		Stock tesla = YahooFinance.get("TSLA", true);
		log.info(tesla.getHistory());
		Assert.assertEquals("TSLA", tesla.getSymbol());
	}
	
	public void testHistoricalWithDate(){
		Calendar from = Calendar.getInstance();
		Calendar to = Calendar.getInstance();
		from.add(Calendar.YEAR, -5); // from 5 years ago
		 
		Stock google = YahooFinance.get("GOOG", from, to, Interval.WEEKLY);
		google.print();
		Assert.assertEquals("GOOG", google.getSymbol());
		
	}
	public void testMultiple(){
		String[] symbols = new String[] {"INTC", "BABA", "TSLA", "AIR.PA", "YHOO"};
		// Can also be done with explicit from, to and Interval parameters
		Map<String, Stock> stocks = YahooFinance.get(symbols, true);
		Stock intel = stocks.get("INTC");
		Stock airbus = stocks.get("AIR.PA");
		Assert.assertEquals(true, stocks.size() > 0);
	}
	
	public void testLoadYahooObject(){
		Calendar from = Calendar.getInstance();
		Calendar to = Calendar.getInstance();
		from.add(Calendar.YEAR, -5); // from 5 years ago
		 
		Stock google = YahooFinance.get("GOOG", from, to, Interval.WEEKLY);
		google.print();
		
		List<HistoricalQuote> l = google.getHistory();
		for(HistoricalQuote hq : l){
			log.info("Date:"+ hq.getDate().getTime() + ": ADj.Close:"+ hq.getAdjClose());
		}
		Assert.assertEquals(true, l.size() > 0);
	}
	
	public void testYahooFinance(){
		List<YahooFinancials> l = IOUtils.processInputFile(new FileLoader().getFileResource("input/APPLE.csv"));
		for(YahooFinancials yf : l){
			log.info(yf.getDate() + ":" + yf.getAdjClose());
		}
		Assert.assertEquals(true, l.size() > 0);
	}
}
