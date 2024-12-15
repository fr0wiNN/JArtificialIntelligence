package com.maksymiliangach.ai;

import java.io.IOException;
import java.util.Random;

import com.maksymiliangach.ai.DataManager.JDataFrame;
import com.maksymiliangach.ai.LinearRegression.*;

public class Main {
    public static void main(String[] args) throws IOException{
        JDataFrame df = JDataFrame.loadCSV("src/main/java/com/maksymiliangach/ai/DataManager/real_estate_dataset.csv");
        Double[] x = df.getColumn(1);
        Double[] y = df.getColumn(11);

        LinearRegression model = new LinearRegression();
        model.train(x, y, 0.00001, 10000000);
        int testInput = 188;
        double prediction = model.predict(testInput);
        System.out.println("Prediction for x = " + testInput + ": y = " + prediction);
        model.plot("House Size vs Price", "House Size", "Price");
    }
}
