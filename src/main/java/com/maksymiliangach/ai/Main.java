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

        //System.out.println(df.head(true));

        df.drop(0,2,3,4,5,6,7,10);

        //System.out.println(df.head(true));

        // Get JDataFrame column values
        double[] sf = df.getColumn(0);
        double[] gs = df.getColumn(1);
        double[] ls = df.getColumn(2);

        df.addCustomColumn("Flex_Score", Main::socialIndex, sf, gs, ls);

        //df.drop(0,1,2);

        System.out.println(df.head(true));

        // Final calculation:
        // Weights: [932.9325849702775, 728.7543504566551, 4145.063046083078]
        // Bias: 375668.4561292953

        // sf, gs, ls
        // prediction(sf, gs, ls) = 932.93 * sf + 728.75 * gs + 4145.06 * ls + 375668.45
        // prediction(143.63, 48.00, 8.29) ~ 602134.81
        // prediction(143.63, 48.00, 8.29) = 932.93 * 143.63 + 728.75 * 48.00 + 4145.06 * 8.29 + 375668.45 = 579,029.51
        var x = df.getColumns(0, 1, 2);
        var y = df.getColumn(3);

        LinearRegression model = new LinearRegression(0.00000410, 10_000_000);
        model.setLogging(true);

        LinearRegressionPlotter plotter = new LinearRegressionPlotter(model);
        plotter.setDisplayDataPlot(true)
                .setDisplayLossPlot(true)
                .setDataLabels("Linear Regression Visualization", "Flex Score", "Price")
                .setLossLabels("Total Loss Visualisation", "Epoch", "Loss Function Value");

        model.setPlotter(plotter);

        model.train(x,y);

        System.out.println(model.summary());

        // TODO create a method for getting better input array for training - think of good name for such function

        // Convert X1 and X2 columns to training array
        //double[][] x = JDataFrame.toTraining(X1, X2);
        //double[][] x = df.getColumns(1, 9);

        // Define LinearRegression model
        // TODO: create builder class for LinearRegression model creation (set hyper parameters: lr, modelName or load from config)
        //LinearRegression model = new LinearRegression(0.0000170, 1_000_000);
        //model.setLogging(true);

        // Define LinearRegressionPlotter for data visualisation
        //LinearRegressionPlotter plotter = new LinearRegressionPlotter(model);
        //plotter.setDisplayDataPlot(true)
        //       .setDisplayLossPlot(true)
        //       .setDataLabels("Real Estate Linear Regression Visualization", "Square Feet", "Price")
        //       .setLossLabels("Total Loss Visualisation", "Epoch", "Loss Function Value");

        // Set plotter
        //model.setPlotter(plotter);

        // Train model
        //model.train(x, y);

        // TODO: save model as model.lr or create a config file
        // TODO: load model from model.lr config file

        // Print summary
        //System.out.println(model.summary());
    }

    private static double socialIndex(double[] v){
        return ((v[0] + v[1]) * v[2]);
    }
}
