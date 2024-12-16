package com.maksymiliangach.ai;

import java.io.IOException;
import java.io.Serializable;

public interface Model extends Serializable {
    void train(double[][] inputs, double[] outputs);
    default String summary() { return "Generic Model"; }
}
