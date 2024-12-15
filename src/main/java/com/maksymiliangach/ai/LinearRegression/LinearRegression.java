package com.maksymiliangach.ai.LinearRegression;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.maksymiliangach.ai.Model;

public class LinearRegression {
    private Double weight;
    private Double bias;
    private Double[] x;
    private Double[] y;

    public LinearRegression() {
        this.weight = 0.0;
        this.bias = 0.0;
    }

    public void train(Double[] x, Double[] y, Double learningRate, int epochs) {
        this.x = x;
        this.y = y;
        int n = x.length;
        for (int epoch = 0; epoch < epochs; epoch++){
            double weightGradient = 0.0;
            double biasGradient = 0.0;
            double loss = 0.0;

            for (int i = 0; i < n; i++){
                double prediction = predict(x[i]);
                double error = prediction - y[i];

                loss += (error * error);
                weightGradient += 2 * error * x[i];
                biasGradient += 2 * error;
            }

            loss /= n;

            weight -= (learningRate * weightGradient) / n;
            bias -= (learningRate * biasGradient) / n;

            if (epoch % 100 == 0) {
                System.out.printf("Epoch %d: Loss = %.4f%n", epoch, loss);
            }
        }
    }

    public double predict(double input) {
        return (weight * input) + bias;
    }

    public double getWeight(){ return weight;}
    public double getBias(){ return bias;}

    
    public void save(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(weight + "\n");
            writer.write(bias + "\n");
        }
    }

    public static LinearRegression load(String filename) throws IOException{
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            double weight = Double.parseDouble(reader.readLine());
            double bias = Double.parseDouble(reader.readLine());

            LinearRegression model = new LinearRegression();
            model.weight = weight;
            model.bias = bias;
            return model;
        }
    }

    //TODO move those functions away to different class - embrace abstraction, praise API simplicity
    public void plot(String title, String xAxisLabel, String yAxisLabel){
        JFrame f = new JFrame();
        f.setSize(800, 600);

        // Create scatter plot dataset
        XYDataset scatterDataset = createDataset();

        // Create chart
        JFreeChart chart = ChartFactory.createScatterPlot(title, xAxisLabel, yAxisLabel, scatterDataset);
        XYPlot plot = chart.getXYPlot();

        // Create regression line dataset
        XYSeriesCollection regressionDataset = new XYSeriesCollection();
        XYSeries regressionLine = new XYSeries("Regression Line");

        // Calculate two points for the line
        double minX = x[0];
        double maxX = x[0];
        for (double value : x) {
            if (value < minX) minX = value;
            if (value > maxX) maxX = value;
        }
        regressionLine.add(minX, weight * minX + bias);
        regressionLine.add(maxX, weight * maxX + bias);

        regressionDataset.addSeries(regressionLine);

        // Add regression line dataset to plot
        plot.setDataset(1, regressionDataset);
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, true); // Show lines for regression
        renderer.setSeriesShapesVisible(0, false); // Hide shapes for regression
        plot.setRenderer(1, renderer);

        // Display chart
        ChartPanel panel = new ChartPanel(chart);
        f.add(panel);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    
    public XYDataset createDataset() {
        XYSeriesCollection ds = new XYSeriesCollection();
        XYSeries s = new XYSeries("Data");
        for (int i = 0; i < x.length; i++){
            s.add(x[i], y[i]);
        }
        ds.addSeries(s);
        return ds;
    }

}
