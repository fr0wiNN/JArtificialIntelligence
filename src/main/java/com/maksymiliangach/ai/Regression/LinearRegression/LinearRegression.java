package com.maksymiliangach.ai.Regression.LinearRegression;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.JFrame;

import com.maksymiliangach.ai.Model;
import com.maksymiliangach.ai.Plotter.LinearRegressionPlotter;
import com.maksymiliangach.ai.Regression.RegressionModel;

public class LinearRegression implements RegressionModel {
    private double learningRate;
    private int epochs;
    private double[] weights;
    private double bias;
    private double[][] inputs; // [i][j]: i feature contains j samples
    private double[] outputs; // [i]: i contains true value for given features
    private int numSamples;
    private int numFeatures;
    private LinearRegressionPlotter plotter;

    public LinearRegression(double learningRate, int epochs) {
        this.learningRate = learningRate;
        this.epochs = epochs;
    }

    public void test(double[][] inputs, double[] outputs){

        this.inputs = inputs;
        this.outputs = outputs;

        setUpTraining();
        System.out.printf("Number of samples: %d\nNumber of features: %d" , numSamples, numFeatures);
    }

    @Override
    public double[] forward(double[][] inputs) {
        double[] predictions = new double[numSamples];

        for (int i = 0; i < numFeatures; i++) {
            double prediction = bias;
            for (int j = 0 ; j < numSamples ; j++) {
                prediction += weights[i] * inputs[i][j];
            }
            predictions[i] = prediction;
        }

        return predictions;
    }

    @Override
    public void backward(double[][] inputs, double[] outputs, double[] predictions) {
        // Gradient initialization
        double[] weightGradients = new double[numFeatures];
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
        bias -= learningRate * biasGradient;
    }

    @Override
    public long computeLoss(double[] yTrue, double[] yPredicted) {
        long loss = 0;
        for (int i = 0; i < yTrue.length; i++) {
            double error = yPredicted[i] - yTrue[i];
            loss += (long) (error * error);
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
        if(numFeatures == 1) { this.plotter = plotter; }
    }

    private void setUpTraining() {
        this.numSamples = inputs[0].length;
        this.numFeatures = inputs.length;
        this.weights = new double[numFeatures];
        for(double w : weights) { w = 1; }
        this.bias = 0;
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
            if (epoch % 1 == 0) {
                System.out.printf("Epoch %d: Loss = %d\n", epoch, computeLoss(outputs, predictions));
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
        return sb.toString();
    }

    public double[][] getInputs() {
        return inputs;
    }

    public double[] getOutputs() {
        return outputs;
    }
}
