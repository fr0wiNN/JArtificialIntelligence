package examples;

import java.io.IOException;
import com.maksymiliangach.ai.DataManager.JDataFrame;
import com.maksymiliangach.ai.DataManager.JDataSplitter;
import com.maksymiliangach.ai.Logger.JLogger;
import com.maksymiliangach.ai.Regression.LinearRegression.LinearRegression;

public class LinearRegressionExample {
    public static void main(String[] args) throws IOException {
        // Load and prepare data
        JDataFrame df = JDataFrame.loadCSV("datasets/real_estate_dataset.csv");
        df.drop(0, 2, 3, 4, 5, 6, 7, 10);
        JDataFrame[] splitDataFrames = JDataSplitter.split(df, 0.8, true);
        JDataFrame trainingDF = splitDataFrames[0];
        JDataFrame testingDF = splitDataFrames[1];

        double[][] trainingInputData = trainingDF.getColumns(0, 1, 2);
        double[] trainingOutputData = trainingDF.getColumn(3);
        double[][] testingInputData = testingDF.getColumns(0, 1, 2);
        double[] testingOutputData = testingDF.getColumn(3);

        // Define and train the model
        // Set epochs to 30_000_000 to ensure model converges
        LinearRegression model = new LinearRegression(0.00000210, 30_000_000);

        // Log model training progress
        model.setLogger(JLogger.SIMPLE_LOGGER);

        // Train linear regression model
        model.train(trainingInputData, trainingOutputData);

        // Print model summary and validate
        System.out.println(model.summary());
        System.out.printf("Total Loss for Training Data: %f\n", model.validate(trainingInputData, trainingOutputData));
        System.out.printf("Total Loss for Testing Data: %f\n", model.validate(testingInputData, testingOutputData));

        // Predict value for real life scenario:
        // I want to estimate the price of a house with:
        // Square_Feet = 93.59
        // Garage_Size = 26.0
        // Location_Score = 4.63
        double[] houseFeatures = {93.59, 26.0, 4.63};
        double housePriceEstimate = model.predict(houseFeatures);
        System.out.printf("Estimated Price for house with Square_Feet[%.2f], Garage_Size[%.2f] and Location_Score[%.2f] is: %.2f\n", houseFeatures[0], houseFeatures[1], houseFeatures[2], housePriceEstimate);
    }
}
