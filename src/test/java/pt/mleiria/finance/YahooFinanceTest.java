/**
 * 
 */
package pt.mleiria.finance;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;
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
		System.out.println(usdeur);
		System.out.println(usdgbp);
		Assert.assertEquals("USDEUR=X", usdeur.getSymbol());
	}
	
	public void testSingleStockHistorical(){
		Stock tesla = YahooFinance.get("TSLA", true);
		System.out.println(tesla.getHistory());
		Assert.assertEquals("TSLA", tesla.getSymbol());
	}
	
	public void testHisitoricalWithDate(){
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
		Assert.assertEquals(5, stocks.size());
	}
	
	public void testLoadYahooObject(){
		Calendar from = Calendar.getInstance();
		Calendar to = Calendar.getInstance();
		from.add(Calendar.YEAR, -5); // from 5 years ago
		 
		Stock google = YahooFinance.get("GOOG", from, to, Interval.WEEKLY);
		google.print();
		
		List<HistoricalQuote> l = google.getHistory();
		for(HistoricalQuote hq : l){
			System.out.println("ADj.Close:"+ hq.getAdjClose());
			System.out.println("Date:"+ hq.getDate().getTime());
		}
		Assert.assertEquals(10, l.size());
		
		
	}
}
