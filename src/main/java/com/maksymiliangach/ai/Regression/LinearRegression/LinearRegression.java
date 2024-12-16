package com.maksymiliangach.ai.Regression.LinearRegression;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.*;

import com.maksymiliangach.ai.Model;
import com.maksymiliangach.ai.ModelLogger;
import com.maksymiliangach.ai.Plotter.LinearRegressionPlotter;
import com.maksymiliangach.ai.Regression.RegressionModel;

public class LinearRegression implements RegressionModel, ModelLogger {
    private double learningRate;
    private int epochs;
    private double[] weights;
    private double bias;
    private double[][] inputs; // [i][j]: i feature contains j samples
    private double[] outputs; // [i]: i contains true value for given features
    private int numSamples;
    private int numFeatures;
    private LinearRegressionPlotter plotter;
    private boolean logging;

    public LinearRegression(double learningRate, int epochs) {
        this.learningRate = learningRate;
        this.epochs = epochs;
    }

    @Override
    public double[] forward(double[][] inputs) {
        double[] predictions = new double[numSamples];

        for (int i = 0; i < numSamples; i++) {
            double prediction = bias;
            for (int j = 0 ; j < numFeatures ; j++) {
                prediction += weights[j] * inputs[j][i];
            }
            predictions[i] = prediction;
        }

        return predictions;
    }

    @Override
    public void backward(double[][] inputs, double[] outputs, double[] predictions) {
        // Gradient initialization
        double[] weightGradients = new double[numFeatures];
        for (int w = 0 ; w < numFeatures ; w++) { weightGradients[w] = 0;}
        double biasGradient = 0;

        // Compute gradients
        for (int s = 0; s < numSamples; s++) {
            double error = predictions[s] - outputs[s];

            // Update gradients for each feature
            for (int f = 0; f < numFeatures; f++) {
                weightGradients[f] += 2 * error * inputs[f][s];
            }

            // Update bias gradient
            biasGradient -= 2 * error;
        }

        // Normalize gradients
        for (int f = 0; f < numFeatures; f++) {
            weightGradients[f] /= numSamples;
        }
        biasGradient /= numSamples;

        // Update weights and bias using gradient descent
        for (int f = 0; f < numFeatures; f++) {
            weights[f] -= learningRate * weightGradients[f];
        }
        bias += learningRate * biasGradient;

        //TODO: have a logging variable for gradient diagnostics
        if(logging && false){
            StringBuilder sb = new StringBuilder();
            sb.append("> Weights Gradients: ").append(Arrays.toString(weightGradients)).append("\n");
            sb.append("> Bias Gradient: ").append(biasGradient).append("\n");
            System.out.print(sb.toString());
        }
    }

    @Override
    public double computeLoss(double[] yTrue, double[] yPredicted) {
        double loss = 0;
        for (int i = 0; i < yTrue.length; i++) {
            double error = yPredicted[i] - yTrue[i];
            loss += (error * error);
        }
        return loss/yTrue.length;
    }

    @Override
    public double[] getWeights() {
        return weights;
    }

    @Override
    public double getBias() {return bias;}

    public void setPlotter(LinearRegressionPlotter plotter) {
        //TODO: if(numFeatures == plotter.numFeatures) - make sure that the plotter supports 3D rendering
        this.plotter = plotter;
    }

    private void setUpTraining() {
        this.numSamples = inputs[0].length;
        this.numFeatures = inputs.length;

        // Weights initialization
        this.weights = new double[numFeatures];
        for (int w = 0 ; w < numFeatures ; w++) { this.weights[w] = Math.random() * 0.01;}
        this.bias = 0;

        if (plotter!=null) plotter.init(numFeatures);
    }

    @Override
    public void train(double[][] inputs, double[] outputs) {
        this.inputs = inputs;
        this.outputs = outputs;

        setUpTraining();

        for(int epoch = 0 ; epoch < epochs ; epoch++) {

            // Forward pass
            double[] predictions = forward(inputs);

            // Backward pass
            backward(inputs, outputs, predictions);

            // TODO: make logging more clever
            if (logging && epoch % 100_000 == 0) {
                System.out.printf("Epoch %d: Loss = %f\n", epoch, computeLoss(outputs, predictions));
                if (plotter != null) { plotter.update(); }
            }
        }
    }

    @Override
    public String summary() {
        StringBuilder sb = new StringBuilder();
        sb.append("========================\n");
        sb.append("Linear Regression Model:\n");
        sb.append("Epochs: ").append(epochs).append("\n");
        sb.append("Learning Rate: ").append(learningRate).append("\n");
        sb.append("Weights: ").append(Arrays.toString(weights)).append("\n");
        sb.append("Bias: ").append(bias).append("\n");
        sb.append("Total Loss: ").append(computeLoss(outputs, forward(inputs))).append("\n");
        return sb.toString();
    }

    public double[][] getInputs() {
        return inputs;
    }

    public double[] getOutputs() {
        return outputs;
    }

    @Override
    public void setLogging(boolean logging) {
        this.logging = logging;
    }

    @Override
    public boolean isLogging() {
        return logging;
    }

}
