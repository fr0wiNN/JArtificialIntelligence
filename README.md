# JArtificialIntelligence Library
Welcome to JArtificialIntelligence library! This library provides tools for 
data management, model training, and visualisation, making it easier to build and evaluate machine learning models in Java.

## JDataFrame Class
The [JDataFrame](./src/main/java/com/maksymiliangach/ai/DataManager/JDataFrame.java) class is designed to handle data in tabular form, similar to data frames in other data science libraries. 
It provides methods for loading data from CSV files, manipulating columns and rows, and preparing data for model training. 
It also allows for easy feature engineering by allowing to create new columns that are combinations of already existing ones!

### Example Usage

```java
package com.maksymiliangach.ai.DataManager;

import java.io.IOException;

public class JDataFrameExample {
    public static void main(String[] args) throws IOException {
        // Load the data from a CSV file
        JDataFrame df = JDataFrame.loadCSV("src/main/java/com/maksymiliangach/ai/DataManager/real_estate_dataset.csv");

        // Visualize the data
        System.out.println("Generic Dataset:");
        System.out.println(df.head());

        // Drop useless columns
        df.drop(0, 4, 5, 6, 7, 9, 10);

        // Get squareFeet and garageSize columns
        double[] squareFeet = df.getColumn(0);
        double[] garageSize = df.getColumn(3);

        // Get numOfBedrooms and numOfBathrooms in one array
        // numOfRooms[0] = numOfBedrooms column
        // numOfRooms[1] = numOfBathrooms column
        double[][] numOfRooms = df.getColumns(1, 2);

        // Drop pulled columns
        df.drop(0, 1, 2, 3);

        System.out.println("Dataset after .drop():");
        System.out.println(df.head());

        // Feature Engineering examples:

        // Use quick lambda expression to create new custom "Total_Size" column, and assign which column should be used in this formula
        df.addCustomColumn("Total_Size", v -> v[0] + v[1], squareFeet, garageSize );

        // Use one of generic JFormulas functions
        df.addCustomColumn("Total_Rooms", JFormulas::add, numOfRooms[0], numOfRooms[1]);

        double[] totalSize = df.getColumn(1);
        double[] totalRooms = df.getColumn(2);

        df.drop(1, 2);

        // Or create a custom one...
        df.addCustomColumn("MSE_of_Total_Size_and_Total_Rooms", JDataFrameExample::customFormula, totalSize, totalRooms);

        System.out.println("Customized Dataset:");
        System.out.println(df.head());

        // Split customized dataset to training(80%) and testing(20%) with pre-shuffled data
        JDataFrame[] split = JDataSplitter.split(df, 0.8, true);
        JDataFrame training = split[0];
        JDataFrame testing = split[1];

        // Print the first 15 rows of the data frame with assigned column indices
        System.out.println("New Training Dataset:");
        System.out.println(training.head(true, 15));
        System.out.println("New Testing Dataset:");
        System.out.println(testing.head(false, 15));
    }

    public static double customFormula(double[] f) {
        return Math.sqrt( Math.pow(f[0], 2) - Math.pow(f[1], 2) );
    }
}

```

## Model Types
### Regression 
The library will support various regression models. 
Below is an example of how to use the [LinearRegression](./src/main/java/com/maksymiliangach/ai/Regression/LinearRegression/LinearRegression.java) model.
#### Linear Regression
The [LinearRegression](./src/main/java/com/maksymiliangach/ai/Regression/LinearRegression/LinearRegression.java) class provides a simple linear regression model. Here's how to use it without a plotter:
```java
import com.maksymiliangach.ai.DataManager.JDataFrame;
import com.maksymiliangach.ai.DataManager.JDataSplitter;
import com.maksymiliangach.ai.Regression.LinearRegression.LinearRegression;
import java.io.IOException;

public class LinearRegressionExample {
    public static void main(String[] args) throws IOException {
        // Load and prepare data
        JDataFrame df = JDataFrame.loadCSV("src/main/java/com/maksymiliangach/ai/DataManager/real_estate_dataset.csv");
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
        model.setLogging(true);
        model.train(trainingInputData, trainingOutputData);

        // Print model summary and validate
        System.out.println(model.summary());
        System.out.printf("Total Loss for Training Data: %f\n", model.validate(trainingInputData, trainingOutputData));
        System.out.printf("Total Loss for Testing Data: %f\n", model.validate(testingInputData, testingOutputData));

        // Predict value for real life scenario:
        double[] houseFeatures = new double[]{93.59, 26.0, 4.63};
        double housePriceEstimate = model.predict(houseFeatures);
        System.out.printf("Estimated Price for house with Square_Feet[%.2f], Garage_Size[%.2f] and Location_Score[%.2f] is: %.2f\n", houseFeatures[0], houseFeatures[1], houseFeatures[2], housePriceEstimate);
    }
}
```

#### Non-Linear Regression
*[TO BE IMPLEMENTED...]*

### Classification
*[TO BE IMPLEMENTED...]*

## Plotter Types
### Regression Plotter
#### Linear Regression Plotter
The [LinearRegressionPlotter](./src/main/java/com/maksymiliangach/ai/Plotter/LinearRegressionPlotter.java) class provides tools for visualizing the training process and the training data.
Here's how to assign a plotter to the model correctly:

```java
import com.maksymiliangach.ai.DataManager.JDataFrame;
import com.maksymiliangach.ai.DataManager.JDataSplitter;
import com.maksymiliangach.ai.Regression.LinearRegression.LinearRegression;
import com.maksymiliangach.ai.Plotter.LinearRegressionPlotter;
import java.io.IOException;

public class LinearRegressionPlotterExample {
    public static void main(String[] args) throws IOException {
        // Load and prepare data
        JDataFrame df = JDataFrame.loadCSV("src/main/java/com/maksymiliangach/ai/DataManager/real_estate_dataset.csv");
        df.drop(0, 2, 3, 4, 5, 6, 7, 10);
        JDataFrame[] splitDataFrames = JDataSplitter.split(df, 0.8, true);
        JDataFrame trainingDF = splitDataFrames[0];
        JDataFrame testingDF = splitDataFrames[1];

        double[][] trainingInputData = trainingDF.getColumns(0, 1, 2);
        double[] trainingOutputData = trainingDF.getColumn(3);
        double[][] testingInputData = testingDF.getColumns(0, 1, 2);
        double[] testingOutputData = testingDF.getColumn(3);

        // Define and train the model
        LinearRegression model = new LinearRegression(0.00000210, 30_000_000);

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

```

## Acknowledgements
This project uses XChart plotting library for visualizations. You can find more information about the library [here](https://github.com/knowm/XChart).

## Learning and Development
This is my first approach to building a library, and I am still learning. I appreciate any feedback and suggestions for improvement.
Feel free to reach out to me at [gach.maksymilian@gmail.com](mailto:gach.maksymilian@gmail.com). 