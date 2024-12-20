package com.maksymiliangach.ai.DataManager;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        // Load the data from a remote CSV file
        // To set up csv file for remote access:
        // 1. Navigate to directory with selected data set
        // 2. Get IP using `hostname -I`, first IP is the desired one
        // 3. Set up python http using: `python3 -m http.server`
        JDataFrame df = JDataFrame.loadCSV("http://192.168.15.51:8000/real_estate_dataset.csv");
        //JDataFrame df = JDataFrame.loadCSV("datasets/real_estate_dataset.csv");

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
        //TODO: change name from Main
        df.addCustomColumn("MSE_of_Total_Size_and_Total_Rooms", Main::customFormula, totalSize, totalRooms);

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
