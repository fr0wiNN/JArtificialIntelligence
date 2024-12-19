package com.maksymiliangach.ai.Regression;

import com.maksymiliangach.ai.Model;

import java.io.Serializable;

public interface RegressionModel extends Model {
    void train(double[][] inputs, double[] outputs);
    double validate(double[][] testInputs, double[] testOutputs);
    double predict(double[] inputs);
    double[] forward(double[][] inputs);
    void backward(double[][] inputs, double[] outputs, double[] predictions);
    double computeTotalLoss(double[] yTrue, double[] yPredicted);
    double[] getWeights();
    double getBias();
}
