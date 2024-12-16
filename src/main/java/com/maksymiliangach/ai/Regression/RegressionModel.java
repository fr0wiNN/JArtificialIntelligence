package com.maksymiliangach.ai.Regression;

import com.maksymiliangach.ai.Model;

import java.io.Serializable;

public interface RegressionModel extends Model {
    double[] forward(double[][] inputs);
    void backward(double[][] inputs, double[] outputs, double[] predictions);
    long computeLoss(double[] yTrue, double[] yPredicted);
    double[] getWeights();
    double getBias();
}
