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
        Double[] X1 = df.getColumn(1);
        Double[] X2 = df.getColumn(9);
        Double[] Y = df.getColumn(11);
        //TODO: make JDataFrame return double[] variables not Double[] wrapper class
        double[] x1Temp = java.util.Arrays.stream(X1).mapToDouble(Double::doubleValue).toArray();
        double[] x2Temp = java.util.Arrays.stream(X2).mapToDouble(Double::doubleValue).toArray();
        double[] y = java.util.Arrays.stream(Y).mapToDouble(Double::doubleValue).toArray();
        double[][] x = new double[1][x1Temp.length];
        for(int i = 0 ; i < x1Temp.length ; i++) { x[0][i] = x1Temp[i]; }//x[1][i] = x2Temp[i];}

        LinearRegression model = new LinearRegression(0.01, 200);
        //LinearRegressionPlotter plotter = new LinearRegressionPlotter(model);
        //model.setPlotter(plotter);
        model.setLogging(true);
        model.train(x, y);
        //model.test(x, y);
    }
}
