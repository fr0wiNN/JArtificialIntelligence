package com.maksymiliangach.ai;

import java.io.IOException;

import com.maksymiliangach.ai.DataManager.JDataFrame;
import com.maksymiliangach.ai.Regression.*;
import com.maksymiliangach.ai.Plotter.LinearRegressionPlotter;
import com.maksymiliangach.ai.Regression.LinearRegression.LinearRegression;

public class Main {
    public static void main(String[] args) throws IOException{
        JDataFrame df = JDataFrame.loadCSV("src/main/java/com/maksymiliangach/ai/DataManager/real_estate_dataset.csv");
        //TODO: make method `getColumns(arg 1, ..., arg n)` which returns double[i][j], where i is a feature and j is sample of feature i
        Double[] X = df.getColumn(1);
        Double[] Y = df.getColumn(11);
        //TODO: make JDataFrame return double[] variables not Double[] wrapper class
        double[] xTemp = java.util.Arrays.stream(X).mapToDouble(Double::doubleValue).toArray();
        double[] y = java.util.Arrays.stream(Y).mapToDouble(Double::doubleValue).toArray();
        double[][] x = new double[1][xTemp.length];
        for(int i = 0 ; i < xTemp.length ; i++) { x[0][i] = xTemp[i];}


        LinearRegression model = new LinearRegression(0.000001, 10_000_000);
        LinearRegressionPlotter plotter = new LinearRegressionPlotter(model);
        model.setPlotter(plotter);

        model.train(x, y);

    }
}
