package com.maksymiliangach.ai;

public interface ModelLogger {
    void setLogging(boolean e);
    boolean isLogging();

    //        //TODO: have a logging variable for gradient diagnostics
    //        if(logging){
    //            StringBuilder sb = new StringBuilder();
    //            sb.append("> Weights Gradients: ").append(Arrays.toString(weightGradients)).append("\n");
    //            sb.append("> Bias Gradient: ").append(biasGradient).append("\n");
    //            System.out.print(sb.toString());
    //        }
}
