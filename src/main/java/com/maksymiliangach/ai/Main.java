package com.maksymiliangach.ai;

import java.io.IOException;

import com.maksymiliangach.ai.DataManager.JDataFrame;
import com.maksymiliangach.ai.DataManager.JDataSplitter;
import com.maksymiliangach.ai.Regression.*;
import com.maksymiliangach.ai.Plotter.LinearRegressionPlotter;
import com.maksymiliangach.ai.Regression.LinearRegression.LinearRegression;

public class Main {
    public static void main(String[] args) throws IOException{
        // Define JDataFrame
        JDataFrame df = JDataFrame.loadCSV("src/main/java/com/maksymiliangach/ai/DataManager/real_estate_dataset.csv");

        //System.out.println(df.head(true));

        df.drop(0,2,3,4,5,6,7,10);

        JDataFrame[] slitDataFrames = JDataSplitter.split(df, 0.8, true);
        JDataFrame trainingDF = slitDataFrames[0];
        JDataFrame testingDF = slitDataFrames[1];

        System.out.println(df.head(true));

        // Get JDataFrame column values
        double[][] trainingInputData = trainingDF.getColumns(0, 1, 2);
        double[]   trainingOutputData = trainingDF.getColumn(3);

        double[][] testingInputData = testingDF.getColumns(0, 1, 2);
        double[]   testingOutputData = testingDF.getColumn(3);

        LinearRegression model = new LinearRegression(0.00000210, 1_000_000);

        // model.setLogging(true);

        LinearRegressionPlotter plotter = new LinearRegressionPlotter(model);
        plotter.setDisplayDataPlot(true)
                .setDisplayLossPlot(true)
                .setDataLabels("Linear Regression Visualization", "Flex Score", "Price")
                .setLossLabels("Total Loss Visualisation", "Epoch", "Loss Function Value");

        model.setPlotter(plotter);

        model.train(trainingInputData, trainingOutputData);

        System.out.println(model.summary());

        System.out.printf("Total Loss for Training Data: %f\n", model.validate(trainingInputData, trainingOutputData));
        System.out.printf("Total Loss for Testing Data: %f\n", model.validate(testingInputData, testingOutputData));


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

}
