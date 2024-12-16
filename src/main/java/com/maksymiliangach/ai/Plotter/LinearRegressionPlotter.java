package com.maksymiliangach.ai.Plotter;

import com.maksymiliangach.ai.Regression.LinearRegression.LinearRegression;
import org.knowm.xchart.*;
import org.knowm.xchart.style.markers.SeriesMarkers;

public class LinearRegressionPlotter extends Plotter{

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
        double[] x = new double[model.getInputs().length];
        for (int i = 0; i < x.length; i++) {
            x[i] = model.getInputs()[i][0];
        }
        double[] y = model.getOutputs();
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
        double[] x = new double[model.getInputs().length];
        for (int i = 0; i < x.length; i++) {
            x[i] = model.getInputs()[i][0];
        }

        double minX = java.util.Arrays.stream(x).min().orElse(0);
        double maxX = java.util.Arrays.stream(x).max().orElse(1);

        double minY = minX * model.getWeights()[0] + model.getBias();
        double maxY = maxX * model.getWeights()[0] + model.getBias();

        double[] lineX = {minX, maxX};
        double[] lineY = {minY, maxY};

        if (chart.getSeriesMap().containsKey("Regression Line")) {
            chart.updateXYSeries("Regression Line", lineX, lineY, null)
                 .setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line)
                 .setMarker(SeriesMarkers.NONE)
                 .setLineColor(lineColor);
        } else {
            chart.addSeries("Regression Line", lineX, lineY)
                 .setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line)
                 .setMarker(SeriesMarkers.NONE)
                 .setLineColor(lineColor);
        }

        chartWrapper.repaintChart();
    }

}
