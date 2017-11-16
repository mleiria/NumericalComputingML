package pt.mleiria.numericalAnalysis.utils;

import static java.lang.Math.sin;
import java.util.ArrayList;
import java.util.List;
import org.jfree.ui.RefineryUtilities;
import pt.mleiria.machinelearning.interfaces.OneVarFunction;
import pt.mleiria.machinelearning.matrixAlgebra.Vector;
import pt.mleiria.plot.ScatterFactory;

/**
 *
 * @author manuel
 */
public class SyntheticDataGenerator {
    
    private final Vector x;
    private final Vector y;
    private final int len;
    private final List<Double[]> lst;
    /**
     * 
     * @param init
     * @param end
     * @param step
     * @param func 
     */
    public SyntheticDataGenerator(final int init, final int end, final double step, OneVarFunction<Double> func) {
        lst = new ArrayList<>();
        double a = init;
        while(a <= end){
            lst.add(new Double[]{a, func.value(a)});
            a = a + step;
        }
        this.len = lst.size();
        double[] xx = new double[len];
        double[] yy = new double[len];
        for(int i = 0; i < lst.size(); i++){
            xx[i] = lst.get(i)[0];
            yy[i] = lst.get(i)[1];
        }
        x = new Vector(xx);
        y = new Vector(yy);
    }
    /**
     * 
     * @return 
     */
    public List<Double[]> getLst() {
        return lst;
    }
    /**
     * 
     * @return 
     */
    public Vector getX() {
        return x;
    }
    /**
     * 
     * @return 
     */
    public Vector getY() {
        return y;
    }
    
    
    
    public static void main(String[] args){
        SyntheticDataGenerator sdg = new SyntheticDataGenerator(0, 1, 0.01, x -> sin(x));
        ScatterFactory sf = new ScatterFactory("Sin(x)", sdg.getX(), sdg.getY());
        sf.setxLabel("Ano");
	    sf.setyLabel("Tempo (seg.)");
	    sf.setChartName("Dados reais");
        sf.buildPlot();
        sf.pack();
	    RefineryUtilities.centerFrameOnScreen(sf);
	    sf.setVisible(true);
    }
    
}
