/**
 * 
 */
package pt.mleiria.machinelearning;

import org.apache.log4j.Logger;

import junit.framework.Assert;
import junit.framework.TestCase;
import pt.mleiria.machinelearning.statistics.ColumnStatistics;
import pt.mleiria.numericalAnalysis.utils.ViewUtils;
import pt.mleiria.utils.FileLoader;

/**
 * @author manuel
 *
 */
public class ColumnStatisticsTest extends TestCase {

	private static final Logger log = Logger.getLogger(ColumnStatisticsTest.class);
	private String[] data;
	private double[] autoCorrData;
	
	@Override
    protected void setUp() throws Exception {
        super.setUp();
        FileLoader fl = new FileLoader();
        String str = fl.getFileToString("input/autocorrelation.txt");
        data = str.split("\n");
        autoCorrData = new double[data.length];
        for(int i = 0; i < data.length; i++){
        	autoCorrData[i] = Double.parseDouble(data[i].trim());	
        }
	}
	/**
	 * 
	 */
	public void testAutoCorrelation(){
		
		final ColumnStatistics cs = new ColumnStatistics(autoCorrData);
		final double[] res = cs.getAutoCorrelation();
		Assert.assertEquals(0.9951151116927492, res[0]);
		Assert.assertEquals(-0.30089907355577905, res[1]);
		Assert.assertEquals(-0.7396255766087496, res[2]);
	}
}
