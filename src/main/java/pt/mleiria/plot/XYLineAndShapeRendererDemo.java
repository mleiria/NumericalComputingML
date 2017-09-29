package pt.mleiria.plot;

import org.jfree.chart.ChartFactory;
import static org.jfree.chart.ChartFactory.createXYLineChart;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import static org.jfree.chart.plot.PlotOrientation.VERTICAL;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import static org.jfree.ui.RefineryUtilities.centerFrameOnScreen;

public class XYLineAndShapeRendererDemo extends ApplicationFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs the demo application.
     *
     * @param title the frame title.
     */
    public XYLineAndShapeRendererDemo(final String title) {

        super(title);
        XYDataset dataset = createSampleDataset();
        JFreeChart chart = createXYLineChart(title, "X", "Y", dataset, VERTICAL, true,
                false, false);
        XYPlot plot = (XYPlot) chart.getPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, true);
        renderer.setSeriesShapesVisible(0, false);

        renderer.setSeriesLinesVisible(1, false);
        renderer.setSeriesShapesVisible(1, true);

        plot.setRenderer(renderer);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 300));
        setContentPane(chartPanel);

    }

    /**
     * Creates a sample dataset.
     *
     * @return A dataset.
     */
    private XYDataset createSampleDataset() {
        XYSeries series1 = new XYSeries("Series 1");
        series1.add(1.0, 3.3);
        series1.add(2.0, 4.4);
        series1.add(3.0, 1.7);
        XYSeries series2 = new XYSeries("Series 2");
        series2.add(1.0, 7.3);
        series2.add(2.0, 6.8);
        series2.add(3.0, 9.6);
        series2.add(4.0, 5.6);
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        return dataset;
    }

    // ****************************************************************************
    // * JFREECHART DEVELOPER GUIDE *
    // * The JFreeChart Developer Guide, written by David Gilbert, is available
    // *
    // * to purchase from Object Refinery Limited: *
    // * *
    // * http://www.object-refinery.com/jfreechart/guide.html *
    // * *
    // * Sales are used to provide funding for the JFreeChart project - please *
    // * support us so that we can continue developing free software. *
    // ****************************************************************************
    /**
     * Starting point for the demonstration application.
     *
     * @param args ignored.
     */
    public static void main(final String[] args) {

        final XYLineAndShapeRendererDemo demo = new XYLineAndShapeRendererDemo("XYLineAndShapeRenderer Demo");
        demo.pack();
        centerFrameOnScreen(demo);
        demo.setVisible(true);

    }
}
