package iaf;

import ann.NeuralLayer;

public class SwishFunction implements IActivationFunction {
    NeuralLayer layer = null;
    double d = 0;
    public SwishFunction(double d){
        this.d = d;
    }
    public double output(double x) {
        return x * (1 / (1 + Math.exp(-x)));
    }

    public double derivative(double x) {
        return ((1 + Math.exp(-x)) + x * Math.exp(-x)) / Math.pow(1 + Math.exp(-x), 2);
    }

    @Override
    public double calculate(double x) {
        return output(x);
    }

    @Override
    public void setLayer(NeuralLayer layer) {
        this.layer = layer;
    }
}