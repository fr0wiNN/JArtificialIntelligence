package com.maksymiliangach.ai;

import java.awt.*;
import java.io.IOException;
import java.util.Random;

import com.maksymiliangach.ai.DataManager.JDataFrame;
import com.maksymiliangach.ai.LinearRegression.*;
import com.maksymiliangach.ai.Plotter.LinearRegressionPlotter;
import com.maksymiliangach.ai.Plotter.Plotter;

public class Main {
    public static void main(String[] args) throws IOException{
        JDataFrame df = JDataFrame.loadCSV("src/main/java/com/maksymiliangach/ai/DataManager/real_estate_dataset.csv");
        Double[] x = df.getColumn(1);
        Double[] y = df.getColumn(11);

        LinearRegression model = new LinearRegression();
        LinearRegressionPlotter plotter = new LinearRegressionPlotter(model);
        model.setPlotter(plotter);

        model.train(x, y, 0.00001, 10_000_000);

    }
}
