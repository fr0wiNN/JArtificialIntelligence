package com.maksymiliangach.ai.Plotter;

import com.maksymiliangach.ai.LinearRegression.LinearRegression;
import org.knowm.xchart.*;

import javax.swing.*;

public class LinearRegressionPlotter {

    private XYChart chart;
    private SwingWrapper<XYChart> chartWrapper;
    private LinearRegression model;
    public LinearRegressionPlotter(LinearRegression lr) {
        this.model = lr;
    }

    public void init(){
        chart = createChart();
        chartWrapper = new SwingWrapper<>(chart);
        chartWrapper.displayChart();
    }

    private XYChart createChart(){
        double[] x = java.util.Arrays.stream(model.getX()).mapToDouble(Double::doubleValue).toArray();
        double[] y = java.util.Arrays.stream(model.getY()).mapToDouble(Double::doubleValue).toArray();
        XYChart xyChart = new XYChartBuilder()
                .width(800)
                .height(600)
                .title("Linear Regression")
                .xAxisTitle("X")
                .yAxisTitle("Y")
                .build();

        //xyChart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);

        xyChart.addSeries("Points", x, y).setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
        //
        return xyChart;
    }

    public void update(){
        double[] x = java.util.Arrays.stream(model.getX()).mapToDouble(Double::doubleValue).toArray();
        double[] y = java.util.Arrays.stream(model.getY()).mapToDouble(Double::doubleValue).toArray();

        double[] lineX = x;
        double[] lineY = new double[lineX.length];
        for(int i = 0 ; i < lineX.length; i++) {
            lineY[i] = model.getWeight() * lineX[i] + model.getBias();
        }

        if (chart.getSeriesMap().containsKey("Regression Line")) {
            chart.updateXYSeries("Regression Line", lineX, lineY, null).setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        } else {
            chart.addSeries("Regression Line", lineX, lineY).setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        }

        chartWrapper.repaintChart();
    }

}
