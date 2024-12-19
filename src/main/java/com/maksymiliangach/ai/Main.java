package com.maksymiliangach.ai;

import java.io.IOException;

import com.maksymiliangach.ai.DataManager.JDataFrame;
import com.maksymiliangach.ai.DataManager.JDataSplitter;
import com.maksymiliangach.ai.Logger.JLogger;
import com.maksymiliangach.ai.Plotter.LinearRegressionPlotter;
import com.maksymiliangach.ai.Regression.LinearRegression.LinearRegression;
import com.maksymiliangach.ai.Regression.LinearRegression.LinearRegressionBuilder;

public class Main {
    public static void main(String[] args) throws IOException{
        // Define JDataFrame
        JDataFrame df = JDataFrame.loadCSV("datasets/real_estate_dataset.csv");

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

        LinearRegression model = new LinearRegressionBuilder().setModelName("Real Estate Price Model")
                                                              .setLearningRate(0.00000110)
                                                              .setEpochs(30_000_000)
                                                              .setLogger(JLogger.PROGRESS_BAR)
                                                              .build();

        LinearRegressionPlotter plotter = new LinearRegressionPlotter(model).setDisplayDataPlot(true).setDisplayLossPlot(true);
        model.setPlotter(plotter);

        model.train(trainingInputData, trainingOutputData);

        System.out.println(model.summary());

        // TODO: create builder class for LinearRegression model creation (set hyper parameters: lr, modelName or load from config)
        String modelName = "model.lr";
        if (model.save(modelName)) {
            System.out.println("Successfully saved: " + modelName);
        } else {
            System.err.println("Error while saving: " + modelName);
        }

        LinearRegression loadedModel = LinearRegression.load(modelName);
        loadedModel.setModelName("Real Estate Price Loaded Model");

        System.out.println(loadedModel.summary());

        System.out.printf("Total Loss for Training Data: %f\n", loadedModel.validate(trainingInputData, trainingOutputData));
        System.out.printf("Total Loss for Testing Data: %f\n", loadedModel.validate(testingInputData, testingOutputData));
    }

}
