package ann;

/**
 * Copyright (c) 2019-2022 placeh.io,
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * <p>
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
 */

import iaf.*;
import log.Log;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class NeuralLayer implements Serializable {

    public String name;
    public Neuron[] neuron = null;
    public NeuralLayer previousLayer;
    public NeuralLayer nextLayer;

    protected int numberOfNeurons;
    boolean neuralLayerDebug = false;

    public NeuralLayer(int numberOfNeurons, String name) {
        this.name = name;
        this.numberOfNeurons = numberOfNeurons;
        neuron = new Neuron[numberOfNeurons];

    }

    public void init(double bias) {

        for (int i = 0; i < neuron.length; i++) {
            int previousLayerOutputs = 0;
            if (previousLayer != null) {
                previousLayerOutputs = previousLayer.numberOfNeurons;
                IActivationFunction iaf = ActivationFactory.create((int) Math.random() % ActivationFunction.values().length, this);
                neuron[i] = new Neuron(this, previousLayerOutputs, iaf, bias, previousLayer);
            } else {
                previousLayerOutputs = 0;
                IActivationFunction iaf = ActivationFactory.create((int) Math.random() % ActivationFunction.values().length, this);
                neuron[i] = new Neuron(this, previousLayerOutputs, iaf, bias, null);
            }
        }

        int j = 0;
        for (int i = 0; i < neuron.length; i++) {
            Neuron n = neuron[i];
            try {
                IActivationFunction af = ActivationFactory.create(i % ActivationFunction.values().length, this);
                n.setActivationFunction(af);
                n.bias = bias;
                ArrayList<Double> weights = WeightFactory.generate(0, 1, neuron.length);
                ArrayList<Double> inputs = InputFactory.generate(0, 1, neuron.length);

                n.init(true, weights, this);
            } catch (Exception iobe) {
                iobe.printStackTrace();
                //Log.info("iafIndex and Neuron count should match");
            }
        }
    }

    public void backprop() {
        if (neuralLayerDebug) {
            Log.info(this.name + " backprop() " + neuron.length);
        }
        NeuralLayer layer = this;
        NeuralLayer input = null;
        Neuron lastFired = null;
        long nanoLastFired = -1;
        while (layer != null) {
            for (int i = 0; i < layer.neuron.length; i++) {
                if (layer.neuron != null && i < layer.neuron.length) {
                    Neuron n = layer.neuron[i];
                    long lf = n.fired;
                    if (lf > nanoLastFired) {
                        nanoLastFired = lf;
                        lastFired = n;
                    }
                }
            }
            if (lastFired != null) {
                for (int j = 0; j < lastFired.weight.size(); j++) {
                    double w = lastFired.weight.get(j);
                    double adjustment = 0;//Utility.dtoc(Math.random() * Character.MAX_VALUE); // tmp;
                    if (Math.random() > 0.5) {
                        adjustment = -adjustment;
                    }
                    double wa = 0;//Utility.dtoc(w + adjustment);
                    //System.out.println(wa);
                    lastFired.weight.set(j, wa);
                    // TODO match up genome to sync.
                }
            }

            if (layer.previousLayer == null && layer.name.equals("input")) {
                input = layer;
            }
            layer = layer.previousLayer;
        }

        if (input != null) {
            try {
                //Genome.recode(this.neuralNet.owner, input);
            } catch (Exception ex) {
                Log.info(ex);
            }
        }
    }

    public void calculate(ArrayList<Double> input) {
        if (neuralLayerDebug) {
            Log.info(this.name + " calc() " + neuron.length);
        }
        for (int i = 0; i < neuron.length; i++) {
            Neuron n = neuron[i];

            if (previousLayer != null) {
                if (neuralLayerDebug) {
                    Log.info("input-" + name + ":" + previousLayer.name);
                }
                n.setInputs(input);
            } else {
                n.setInputs(input);
            }
        }
        if (nextLayer != null) {
            if (neuralLayerDebug) {
                Log.info("Compute next Layer:");
            }
            nextLayer.calculate(input);
        }
    }

}


