package com.maksymiliangach.ai.Plotter;

import com.maksymiliangach.ai.Regression.LinearRegression.LinearRegression;
import org.knowm.xchart.*;
import org.knowm.xchart.style.markers.SeriesMarkers;
import javax.swing.*;
import java.util.ArrayList;

public class LinearRegressionPlotter extends Plotter{

    private XYChart dataChart;
    private XYChart lossChart;
    private SwingWrapper<XYChart> dataChartWrapper;
    private SwingWrapper<XYChart> lossChartWrapper;
    private boolean displayDataPlot = false;
    private boolean displayLossPlot = false;
    private String[] dataPlotLabels = {"Linear Regression", "X", "Y"};
    private String[] lossPlotLabels = {"Total Loss", "Epoch", "Loss"};

    public LinearRegressionPlotter setDisplayDataPlot(boolean e) {
        displayDataPlot = e; return this;
    }
    public LinearRegressionPlotter setDisplayLossPlot(boolean e) {
        displayLossPlot = e; return this;
    }
    public LinearRegressionPlotter setDataLabels(String title, String xLabel, String yLabel) {
        dataPlotLabels[0] = title;
        dataPlotLabels[1] = xLabel;
        dataPlotLabels[2] = yLabel;
        return this;
    }
    public LinearRegressionPlotter setLossLabels(String title, String xLabel, String yLabel) {
        lossPlotLabels[0] = title;
        lossPlotLabels[1] = xLabel;
        lossPlotLabels[2] = yLabel;
        return this;
    }
    private final LinearRegression model;
    public LinearRegressionPlotter(LinearRegression lr) {
        this.model = lr;
    }

    public void init(int numFeature){
        if (displayDataPlot) {
            dataChart = createDataChart();
            dataChartWrapper = new SwingWrapper<>(dataChart);

            JFrame dataFrame = dataChartWrapper.displayChart();
            dataFrame.setLocation(100, 100);
            dataFrame.setTitle("Data Chart");
            dataFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            dataFrame.setVisible(true);
        }
        if (displayLossPlot) {
            lossChart = createLossChart();
            lossChartWrapper = new SwingWrapper<>(lossChart);

            JFrame lossFrame = lossChartWrapper.displayChart();
            lossFrame.setLocation(1000, 100);
            lossFrame.setTitle("Data Chart");
            lossFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            lossFrame.setVisible(true);
        }
    }

    private XYChart createLossChart(){
        return new XYChartBuilder()
                   .width(800)
                   .height(600)
                   .title(lossPlotLabels[0])
                   .xAxisTitle(lossPlotLabels[1])
                   .yAxisTitle(lossPlotLabels[2])
                   .build();
    }

    private XYChart createDataChart(){
        double[] x = new double[model.getInputs()[0].length];
        for (int i = 0; i < x.length; i++) {
            x[i] = model.getInputs()[0][i];
        }
        double[] y = model.getOutputs();
        XYChart xyChart = new XYChartBuilder()
                .width(800)
                .height(600)
                .title(dataPlotLabels[0])
                .xAxisTitle(dataPlotLabels[1])
                .yAxisTitle(dataPlotLabels[2])
                .build();

        xyChart.addSeries("Points", x, y)
               .setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter)
               .setMarkerColor(super.pointsColor);

        return xyChart;
    }

    public void updateDataChart(){
        double[] x = new double[model.getInputs()[0].length];
        for (int i = 0; i < x.length; i++) {
            x[i] = model.getInputs()[0][i];
        }

        double minX = java.util.Arrays.stream(x).min().orElse(0);
        double maxX = java.util.Arrays.stream(x).max().orElse(1);

        double minY = minX * model.getWeights()[0] + model.getBias();
        double maxY = maxX * model.getWeights()[0] + model.getBias();

        double[] lineX = {minX, maxX};
        double[] lineY = {minY, maxY};

        if (dataChart.getSeriesMap().containsKey("Regression Line")) {
            dataChart.updateXYSeries("Regression Line", lineX, lineY, null)
                     .setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line)
                     .setMarker(SeriesMarkers.NONE)
                     .setLineColor(super.lineColor);
        } else {
            dataChart.addSeries("Regression Line", lineX, lineY)
                     .setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line)
                     .setMarker(SeriesMarkers.NONE)
                     .setLineColor(super.lineColor);
        }

        dataChartWrapper.repaintChart();
    }

    public void updateLossPlot(int epoch, double loss) {
        if (lossChart.getSeriesMap().containsKey("Loss")) {
            storedEpoch.add((double) epoch);
            storedLoss.add(loss);
            lossChart.updateXYSeries("Loss", storedEpoch, storedLoss, null)
                    .setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line)
                    .setMarker(SeriesMarkers.NONE)
                    .setLineColor(super.lineColor);;
        } else {
            storedEpoch = new ArrayList<>();
            storedLoss = new ArrayList<>();
            storedEpoch.add((double) epoch);
            storedLoss.add(loss);
            lossChart.addSeries("Loss", storedEpoch, storedLoss)
                    .setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line)
                    .setMarker(SeriesMarkers.NONE)
                    .setLineColor(super.lineColor);
        }

        lossChartWrapper.repaintChart();
    }

    private ArrayList<Double> storedEpoch;
    private ArrayList<Double> storedLoss;

    public void update(int epoch, double loss) {
        if(displayDataPlot) { updateDataChart(); }
        if(displayLossPlot) { updateLossPlot(epoch, loss); }
    }
}
