package com.maksymiliangach.ai.Plotter;

import com.maksymiliangach.ai.LinearRegression.LinearRegression;
import org.knowm.xchart.*;
import org.knowm.xchart.style.markers.SeriesMarkers;

import javax.swing.*;
import java.awt.*;

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
        double minX = java.util.Arrays.stream(x).min().orElse(0);
        double maxX = java.util.Arrays.stream(x).max().orElse(1);

        double minY = model.predict(minX);
        double maxY = model.predict(maxX);

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
