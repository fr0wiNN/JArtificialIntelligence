package com.maksymiliangach.ai.Plotter;

import com.maksymiliangach.ai.LinearRegression.LinearRegression;
import org.knowm.xchart.*;

import javax.swing.*;

public class LinearRegressionPlotter {

    private XYChart chart;
    public LinearRegressionPlotter(LinearRegression lr) {
        //super("Linear Regression", 800, 600);
        chart = createChart(lr);
        new SwingWrapper(chart).displayChart();
    }

    private XYChart createChart(LinearRegression lr){
        double[] x = java.util.Arrays.stream(lr.getX()).mapToDouble(Double::doubleValue).toArray();
        double[] y = java.util.Arrays.stream(lr.getY()).mapToDouble(Double::doubleValue).toArray();
        XYChart xyChart = new XYChartBuilder()
                .width(800)
                .height(600)
                .title("Linear Regression")
                .xAxisTitle("X")
                .yAxisTitle("Y")
                .build();

        xyChart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);

        xyChart.addSeries("Points", x, y);
        //
        return xyChart;
    }

}
