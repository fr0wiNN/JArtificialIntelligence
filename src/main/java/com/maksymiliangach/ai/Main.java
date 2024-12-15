package com.maksymiliangach.ai;

import java.io.IOException;
import java.util.Random;

import com.maksymiliangach.ai.LinearRegression.*;

public class Main {
    public static void main(String[] args) throws IOException{
        double[] x = new double[200];
        double[] y = new double[200];
        Random rand = new Random(420);

        double slope = -2.5; // Adjust for your regression line
        double intercept = 25.0;

        for (int i = 0; i < x.length; i++) {
            x[i] = i + 1; // x-values from 1 to 20
            y[i] = slope * x[i] + intercept + (rand.nextDouble() * 100 - 2.5); // Add random noise
        }

        // Print dataset for inspection
        System.out.println("Generated Dataset:");
        for (int i = 0; i < x.length; i++) {
            System.out.printf("x: %.2f, y: %.2f%n", x[i], y[i]);
        }

        LinearRegression model = new LinearRegression();
        model.train(x, y, 0.00001, 100000);
        int testInput = 6;
        double prediction = model.predict(testInput);
        System.out.println("Prediction for x = " + testInput + ": y = " + prediction);
        model.plot();
    }
}
