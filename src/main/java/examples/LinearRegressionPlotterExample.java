package examples;

import java.io.IOException;
import com.maksymiliangach.ai.DataManager.JDataFrame;
import com.maksymiliangach.ai.DataManager.JDataSplitter;
import com.maksymiliangach.ai.Regression.LinearRegression.LinearRegression;
import com.maksymiliangach.ai.Plotter.LinearRegressionPlotter;

public class LinearRegressionPlotterExample {
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
        LinearRegression model = new LinearRegression(0.00000210, 1_000_000);

        // Define and set the plotter
        LinearRegressionPlotter plotter = new LinearRegressionPlotter(model);
        plotter.setDisplayDataPlot(true)
                .setDisplayLossPlot(true)
                .setDataLabels("Linear Regression Visualization", "X", "Price")
                .setLossLabels("Total Loss Visualisation", "Epoch", "Total Loss Value");

        model.setPlotter(plotter);
        model.train(trainingInputData, trainingOutputData);

        // Print model summary and validate
        System.out.println(model.summary());
        System.out.printf("Total Loss for Training Data: %f\n", model.validate(trainingInputData, trainingOutputData));
        System.out.printf("Total Loss for Testing Data: %f\n", model.validate(testingInputData, testingOutputData));
    }
}
