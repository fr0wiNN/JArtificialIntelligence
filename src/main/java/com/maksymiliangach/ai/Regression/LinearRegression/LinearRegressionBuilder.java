package com.maksymiliangach.ai.Regression.LinearRegression;

import com.maksymiliangach.ai.Logger.JLogger;
import com.maksymiliangach.ai.Plotter.LinearRegressionPlotter;

public class LinearRegressionBuilder {
    private String modelName = "linear_regression_default_builder_model";
    private double learningRate = 0.00000001;
    private int epochs = 1_000_000;
    private LinearRegressionPlotter plotter;
    private JLogger logger = JLogger.DEFAULT_LOGGER;


    public LinearRegressionBuilder setModelName(String modelName) {
        this.modelName = modelName;
        return this;
    }

    public LinearRegressionBuilder setLearningRate(double learningRate) {
        this.learningRate = learningRate;
        return this;
    }

    public LinearRegressionBuilder setEpochs(int epochs) {
        this.epochs = epochs;
        return this;
    }

    public LinearRegressionBuilder setLogger(JLogger logger) {
        this.logger = logger;
        return this;
    }

    //TODO: make setPlotter method fully functional
    // For now user can create plotter object only when model is referenced in plotter's constructor
    // ... and then model can have plotter assigned by LinearRegression.setPlotter(plotter).
    // Fix that by allowing to assign plotter while building the model
    public LinearRegressionBuilder setPlotter(LinearRegressionPlotter plotter) {
        this.plotter = plotter;
        return this;
    }

    public LinearRegression build() {
        LinearRegression model = new LinearRegression(this.learningRate, this.epochs);
        model.setModelName(this.modelName);
        model.setLogger(this.logger);
        if(plotter != null) { model.setPlotter(this.plotter); }

        return model;
    }
}
