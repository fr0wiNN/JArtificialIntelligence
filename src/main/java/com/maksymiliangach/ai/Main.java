package com.maksymiliangach.ai;

import java.io.IOException;

import com.maksymiliangach.ai.DataManager.JDataFrame;
import com.maksymiliangach.ai.Regression.*;
import com.maksymiliangach.ai.Plotter.LinearRegressionPlotter;
import com.maksymiliangach.ai.Regression.LinearRegression.LinearRegression;

public class Main {
    public static void main(String[] args) throws IOException{
        // Define JDataFrame
        JDataFrame df = JDataFrame.loadCSV("src/main/java/com/maksymiliangach/ai/DataManager/real_estate_dataset.csv");
        // TODO: make method `getColumns(arg 1, ..., arg n)` which returns double[i][j], where i is a feature and j is sample of feature i

        // Get JDataFrame column values
        Double[] X1 = df.getColumn(1);
        Double[] X2 = df.getColumn(9);
        Double[] Y = df.getColumn(11);
        //TODO: make JDataFrame return double[] variables not Double[] wrapper class

        // Convert Double[] to double[] and create temporary data for input construction
        double[] x1Temp = java.util.Arrays.stream(X1).mapToDouble(Double::doubleValue).toArray();
        double[] x2Temp = java.util.Arrays.stream(X2).mapToDouble(Double::doubleValue).toArray();
        double[] y = java.util.Arrays.stream(Y).mapToDouble(Double::doubleValue).toArray();

        // Convert input columns to training input 2D array
        double[][] x = new double[1][x1Temp.length];
        for(int i = 0 ; i < x1Temp.length ; i++) { x[0][i] = x1Temp[i]; }
        //for(int i = 0 ; i < x1Temp.length ; i++) { x[0][i] = x1Temp[i]; x[1][i] = x2Temp[i];}

        // Define LinearRegression model
        // TODO: create builder class for LinearRegression model creation (set hyper parameters: lr, modelName or load from config)
        LinearRegression model = new LinearRegression(0.0000170, 1_000_000);
        model.setLogging(true);

        // Define LinearRegressionPlotter for data visualisation
        LinearRegressionPlotter plotter = new LinearRegressionPlotter(model);
        plotter.setDisplayDataPlot(true)
               .setDisplayLossPlot(true)
               .setDataLabels("Real Estate Linear Regression Visualization", "Square Feet", "Price")
               .setLossLabels("Total Loss Visualisation", "Epoch", "Loss Function Value");

        // Set plotter
        model.setPlotter(plotter);

        // Train model
        model.train(x, y);

        // TODO: save model as model.lr or create a config file
        // TODO: load model from model.lr config file

        // Print summary
        System.out.println(model.summary());
    }
}
