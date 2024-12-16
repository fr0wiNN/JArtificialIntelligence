package com.maksymiliangach.ai.LinearRegression;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFrame;

import com.maksymiliangach.ai.Model;
import com.maksymiliangach.ai.Plotter.LinearRegressionPlotter;

public class LinearRegression {
    private Double weight;
    private Double bias;
    private Double[] x;
    private Double[] y;
    private LinearRegressionPlotter plotter;

    public LinearRegression() {
        this.weight = 1.0;
        this.bias = 0.0;
    }

    public void train(Double[] x, Double[] y, Double learningRate, int epochs) {
        this.x = x;
        this.y = y;
        int n = x.length;

        if(plotter != null){plotter.init();}

        for (int epoch = 0; epoch < epochs; epoch++){
            double weightGradient = 0.0;
            double biasGradient = 0.0;
            double loss = 0.0;

            for (int i = 0; i < n; i++){
                double prediction = predict(x[i]);
                double error = prediction - y[i];

                loss += (error * error);
                // Updating gradients
                weightGradient += 2 * error * x[i];
                biasGradient += 2 * error;
            }

            // Normalizing loss
            loss /= n;

            weight -= learningRate * (weightGradient / n);
            bias -= learningRate * (biasGradient / n);

            if (epoch % 100 == 0) {
                System.out.printf("Epoch %d: Loss = %.4f%n, Weight Gradient = %.4f, Bias Gradient = %.4f%n\n", epoch, loss, weightGradient, biasGradient);
                if(plotter != null) {plotter.update();}
            }
        }
    }

    public double predict(double input) {
        return (weight * input) + bias;
    }

    public double getWeight(){ return weight;}
    public double getBias(){ return bias;}

    public Double[] getX(){ return x;}
    public Double[] getY(){ return y;}
    public void setPlotter(LinearRegressionPlotter plotter){this.plotter = plotter;}
}
