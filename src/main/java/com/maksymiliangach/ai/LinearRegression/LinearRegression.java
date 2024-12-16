package com.maksymiliangach.ai.LinearRegression;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFrame;

import com.maksymiliangach.ai.Model;

public class LinearRegression {
    private Double weight;
    private Double bias;
    private Double[] x;
    private Double[] y;

    public LinearRegression() {
        this.weight = 1.0;
        this.bias = 0.0;
    }

    public void train(Double[] x, Double[] y, Double learningRate, int epochs) {
        this.x = x;
        this.y = y;
        int n = x.length;
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
            }
        }
    }

    public double predict(double input) {
        return (weight * input) + bias;
    }

    public double getWeight(){ return weight;}
    public double getBias(){ return bias;}

    
    public void save(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(weight + "\n");
            writer.write(bias + "\n");
        }
    }

    public static LinearRegression load(String filename) throws IOException{
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            double weight = Double.parseDouble(reader.readLine());
            double bias = Double.parseDouble(reader.readLine());

            LinearRegression model = new LinearRegression();
            model.weight = weight;
            model.bias = bias;
            return model;
        }
    }

    public Double[] getX(){ return x;}
    public Double[] getY(){ return y;}
}
