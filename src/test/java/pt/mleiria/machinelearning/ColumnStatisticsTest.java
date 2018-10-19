/**
 *
 */
package pt.mleiria.machinelearning;

import static java.lang.Double.parseDouble;
import static java.lang.Math.sqrt;
import junit.framework.TestCase;
import org.apache.log4j.Logger;
import static org.apache.log4j.Logger.getLogger;
import pt.mleiria.machinelearning.matrixAlgebra.Vector;
import pt.mleiria.machinelearning.statistics.ColumnStatistics;
import static pt.mleiria.numericalAnalysis.utils.Utils.formataNumero;
import pt.mleiria.utils.FileLoader;

/**
 * @author manuel
 *
 */
public class ColumnStatisticsTest extends TestCase {

    private static final Logger LOG = getLogger("mlearningLog");
    private String[] data;
    private double[] autoCorrData;
    
    final double[]  components = new double[]{64630, 11735, 14216, 99233, 14470, 4978, 73429, 38120, 51135, 67060};
    ColumnStatistics cs;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        FileLoader fl = new FileLoader("input/autocorrelation.txt");
        String str = fl.getFileToString("input/autocorrelation.txt");
        data = str.split("\n");
        autoCorrData = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            autoCorrData[i] = parseDouble(data[i].trim());
        }
         cs = new ColumnStatistics(components);
    }

    /**
     *
     */
    public void testAutoCorrelation() {

        final ColumnStatistics cs = new ColumnStatistics(autoCorrData);
        final double[] res = cs.getAutoCorrelation();
        assertEquals(0.9951151116927492, res[0]);
        assertEquals(-0.30089907355577905, res[1]);
        assertEquals(-0.7396255766087496, res[2]);
    }
    /**
     * 
     */
    public void testAverage(){
        assertEquals(43900.6, cs.getAverage());
    }
    /**
     * 
     */
    public void testMedian(){
        assertEquals(44627.5, cs.getMedian());
    }
    
    public void testStandardDeviation(){
        final double populationStandardDeviation = sqrt(cs.getUnnormalizedVariance()/components.length);
        assertEquals("30466,9", formataNumero(populationStandardDeviation, "##0.0"));
    }
    
    
}
