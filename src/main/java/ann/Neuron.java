package ann;

/** Copyright (c) 2019-2022 placeh.io,
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * @author xagau
 * @email seanbeecroft@gmail.com
 *
 */

import iaf.IActivationFunction;
import iaf.InputFactory;
import iaf.WeightFactory;
import log.Log;

import java.util.ArrayList;
import java.util.Random;

public class Neuron {
    public ArrayList<Double> weight = new ArrayList<>();
    private ArrayList<Double> inputs = new ArrayList<>();
    private ArrayList<Double> outputs = new ArrayList<>();
    private Double output = 0d;
    private Double outputBeforeActivation = 0d;
    private int numberOfInputs = 0;
    public Double bias = 0d; // get from gene? OR TD(0)?

    private IActivationFunction activationFunction;

    private NeuralLayer layer = null;
    public long fired = -1;

    Random rand = new Random();

    public Neuron(NeuralLayer layer, int numberOfInputs, IActivationFunction iaf, Double bias, NeuralLayer parent) {

        this.bias = bias;
        this.layer = layer;
        if(this.bias.isNaN() || this.bias.isInfinite()){
            this.bias = rand.nextGaussian();
        }
        if( parent != null ) {
            numberOfInputs = parent.neuron.length;
            Log.info("PN:" + numberOfInputs + ":MN:" + layer.neuron.length);
        } else {
            if( !layer.name.equals("input") ) {
                Log.info("Parent layer is null for " + layer.name );
            }
        }

        this.numberOfInputs = numberOfInputs;
        weight = WeightFactory.generate(0,1, numberOfInputs);
        activationFunction = iaf;
    }

    public ArrayList<Double> getInputs()
    {
        return inputs;
    }

    public void setActivationFunction(IActivationFunction iaf) {
        activationFunction = iaf;
    }

    public IActivationFunction getActivationFunction(){
        return activationFunction;
    }

    public void init(boolean random, ArrayList<Double> weights, NeuralLayer layer) {
        Random rand = new Random();
        int len = layer.neuron.length;
        this.weight = weights;
        if( random ) {
            for (int i = 0; i < this.weight.size(); i++) {
                double newWeight = rand.nextGaussian() * Character.MAX_VALUE;
                this.weight.set(i, newWeight);
            }
        } else {
            for (int i = 0; i < this.weight.size(); i++) {
                double newWeight = rand.nextGaussian()*Character.MAX_VALUE;
                newWeight = (double)weights.get(i);
                this.weight.set(i, newWeight);
            }
        }
    }

    public void calculate() {
        fired = System.nanoTime();
        outputBeforeActivation = 0.0;

        int numberOfInputs = -1;
        if( layer.previousLayer != null ) {
            numberOfInputs = layer.previousLayer.neuron.length;
        }
        if (numberOfInputs > 0) {
            if (inputs != null && weight != null) {
                System.out.println("inputs:" + inputs.size() + " number of inputs " + numberOfInputs + " weights " + weight.size());
                for (int i = 0; i < inputs.size(); i++) {
                    double v = inputs.get(i);
                    outputBeforeActivation += v * weight.get(i);
                }
            }
        }
        output = activationFunction.calculate(outputBeforeActivation);
    }

    public void setInputs(ArrayList<Double> input) {
        this.inputs = input;
    }

    public Double getOutput() {
        if( output.isNaN() || output.isInfinite() ){
            return bias;
        }
        return output;
    }

}
