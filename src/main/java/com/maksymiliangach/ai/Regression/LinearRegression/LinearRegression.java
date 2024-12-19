package com.maksymiliangach.ai.Regression.LinearRegression;

import java.io.*;
import java.util.Arrays;

import com.maksymiliangach.ai.Logger.JLogger;
import com.maksymiliangach.ai.Plotter.LinearRegressionPlotter;
import com.maksymiliangach.ai.Regression.RegressionModel;

public class LinearRegression implements RegressionModel {
    private String modelName = "LinearRegression Model";
    private final double learningRate;
    private final int epochs;
    private double[] weights;
    private double bias;
    private double[][] inputs; // [i][j]: i feature contains j samples
    private double[] outputs; // [i]: i contains true value for given features
    private int numSamples;
    private int numFeatures;
    private LinearRegressionPlotter plotter;
    private JLogger logger;

    public LinearRegression(double learningRate, int epochs) {
        this.learningRate = learningRate;
        this.epochs = epochs;
        this.logger = JLogger.DEFAULT_LOGGER;
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

    }

    @Override
    public double computeTotalLoss(double[] yTrue, double[] yPredicted) {
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

            if (epoch % 10_000 == 0) {
                logger.log(computeTotalLoss(outputs, predictions), epoch, epochs);
                if (plotter != null) { plotter.update(epoch, computeTotalLoss(outputs, predictions)); }
            }
        }
    }

    @Override
    public double validate(double[][] testInputs, double[] testOutputs) {
        double[] predictions = new double[testInputs[0].length];

        // For each sample
        for (int s = 0; s < testInputs[0].length; s++) {
            double[] sampleValues = new double[testInputs.length];
            for (int i = 0; i < testInputs.length; i++) {
                sampleValues[i] = testInputs[i][s];
            }
            predictions[s] = predict(sampleValues);
        }
        return computeTotalLoss(testOutputs, predictions);
    }

    @Override
    public double predict(double[] inputs) {
        double prediction = bias;
        for (int i = 0; i < inputs.length; i++) {
            prediction += weights[i] * inputs[i];
        }
        return prediction;
    }

    @Override
    public String summary() {
        StringBuilder sb = new StringBuilder();
        sb.append("========================\n");
        sb.append(modelName).append("\n");
        sb.append("Type: Linear Regression with ").append(weights.length).append(" input feature(s)\n");
        sb.append("Epochs: ").append(epochs).append("\n");
        sb.append("Learning Rate: ").append(learningRate).append("\n");
        sb.append("Weights: ").append(Arrays.toString(weights)).append("\n");
        sb.append("Bias: ").append(bias).append("\n");
        sb.append("Total Loss: ").append(computeTotalLoss(outputs, forward(inputs))).append("\n");
        return sb.toString();
    }

    public double[][] getInputs() {
        return inputs;
    }

    public double[] getOutputs() {
        return outputs;
    }

    public void setLogger(JLogger logger) { this.logger = logger; }

    public void setModelName(String modelName) { this.modelName = modelName; }

    public boolean save(String name) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(name))) {
            this.plotter = null;
            oos.writeObject(this);
            return true;
        } catch (IOException io) {
            return false;
        }
    }

    public static LinearRegression load(String name) throws IOException{
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(name))) {
            return (LinearRegression) ois.readObject();
        } catch (ClassNotFoundException cnf) {
            System.err.println("Error while converting Object to Model class");
            return new LinearRegression(-1,-1);
        }
    }
}
