package pt.mleiria.plot;

import java.text.DecimalFormat;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import pt.mleiria.machinelearning.function.evaluation.PolynomialFunction;
import pt.mleiria.machinelearning.matrixAlgebra.Vector;

public class ScatterFactory extends ApplicationFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 2123893750066554508L;

    private final Vector x;
    private final Vector y;
    private String xLabel = " x label";
    private String yLabel = "y label";
    private String chartName = "Dados Reais";
    private final String title;

    //private XYDataset dataset;
    private XYSeriesCollection datasetCollection;
    private XYLineAndShapeRenderer renderer;

    /**
     * 
     * @param title
     * @param x
     *            the original xAxis points
     * @param y
     *            the original data. This will be plotted as points
     */
    public ScatterFactory(final String title, final Vector x, final Vector y) {
	super(title);
	this.title = title;
	this.x = x;
	this.y = y;
	init();
    }

    /**
     * 
     */
    private void init() {
	final XYSeries scatterPoints = new XYSeries(chartName);
	for (int i = 0; i < x.dimension(); i++) {
	    scatterPoints.add(x.component(i), y.component(i));
	}
	datasetCollection = new XYSeriesCollection();
	datasetCollection.addSeries(scatterPoints);
	renderer = new XYLineAndShapeRenderer();
	renderer.setSeriesLinesVisible(0, false);
	renderer.setSeriesShapesVisible(0, true);
    }
    /**
     * 
     * @param polyList
     */
    public void addSeries(final List<PolynomialFunction> polyList, final List<String> plotNames) {
	int cnt = 1;
	for (PolynomialFunction pf : polyList) {
	    XYSeries linePoints = new XYSeries(plotNames.get(cnt-1));
	    for (int i = 0; i < x.dimension(); i++) {
		double xValue = x.component(i);
		linePoints.add(xValue, pf.value(xValue));
	    }
	    datasetCollection.addSeries(linePoints);
	    renderer.setSeriesLinesVisible(cnt, true);
	    renderer.setSeriesShapesVisible(cnt, false);
	    cnt++;
	}
    }
    /**
     * 
     */
    public void buildPlot() {
	//dataset = datasetCollection;
	JFreeChart chart = ChartFactory.createXYLineChart(title, xLabel, yLabel, datasetCollection, PlotOrientation.VERTICAL,
		true, false, false);
	XYPlot plot = (XYPlot) chart.getPlot();
	plot.setRenderer(renderer);
	NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
	//rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	((NumberAxis)plot.getDomainAxis()).setNumberFormatOverride(new DecimalFormat("0"));
	rangeAxis.setAutoRangeIncludesZero(false);
	
	final ChartPanel chartPanel = new ChartPanel(chart);
	chartPanel.setPreferredSize(new java.awt.Dimension(500, 300));
	setContentPane(chartPanel);
    }

    /**
     * 
     * @param xLabel
     */
    public void setxLabel(String xLabel) {
	this.xLabel = xLabel;
    }

    /**
     * 
     * @param yLabel
     */
    public void setyLabel(String yLabel) {
	this.yLabel = yLabel;
    }

    /**
     * 
     * @param chartName
     */
    public void setChartName(String chartName) {
	this.chartName = chartName;
    }
}
