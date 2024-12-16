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

    @Override
    public double[] forward(double[][] inputs) {
        return new double[0];
    }

    @Override
    public void backward(double[][] inputs, double[] outputs) {

    }

    @Override
    public double computeLoss(double[] yTrue, double[] yPredicted) {
        return 0;
    }

    @Override
    public double[] getWeights() {
        return weights;
    }

    @Override
    public double getBias() {return bias;}

    public void setPlotter(LinearRegressionPlotter plotter) {this.plotter = plotter;}

    private void setUpTraining() {
        this.numSamples = inputs.length;
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

        }
    }

    @Override
    public String summary() {
        StringBuilder sb = new StringBuilder();
        sb.append("Linear Regression Mode:\n");
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
