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
import iaf.*;
import impl.Action;
import impl.Genome;
import impl.Settings;
import jdk.internal.util.xml.impl.Input;
import log.Log;
import optimizer.Adam;
import optimizer.Optimizer;

import java.io.Serializable;
import java.util.ArrayList;


public class NeuralNet implements Serializable {
    private Optimizer optimizer = new Adam();
    ArrayList<NeuralLayer> dense = new ArrayList<>();
    public Genome owner = null;

    public void setGenome(Genome genome)
    {
        this.owner = genome;
    }

    public void setOptimizer(Optimizer optimizer)
    {
        this.optimizer = optimizer;
    }

    public InputLayer getInputLayer()
    {
        return (InputLayer)this.dense.get(0);
    }

    public OutputLayer getOutputLayer()
    {
        return (OutputLayer)this.dense.get(this.dense.size()-1);
    }

    public void addLayer(NeuralLayer layer)
    {
        if( layer == null ){
            throw new IllegalStateException("null layer cannot be added");
        }
        if( dense.isEmpty() ){
            if( layer != null ) {
                layer.previousLayer = null;
            }
        }
        if( dense.size() == 1 ){
            layer.nextLayer = null;
        }
        if( dense.size() >= 1 ){
            layer.previousLayer = dense.get(dense.size()-1);
        }

        dense.add(layer);
    }

    public void init() {
        NeuralLayer next = null;
        //ArrayList<Integer> iafIndex = new ArrayList();

        // This maps random activation functions to each neurons. Next we need to map them exactly.

        for(int i = 0;i < dense.size(); i++ ) {
            if( i + 1 < dense.size() ){
                next = dense.get(i);
            }
            NeuralLayer nl = dense.get(i);
            nl.nextLayer = next;
            nl.init(Math.random());
        }
    }

    public NeuralNet()
    {

    }

    public void display()
    {
        try {
            boolean b = true;
            if(b) {
                NeuralLayer input = dense.get(0);
                NeuralLayer output = dense.get(dense.size()-1);
                System.out.println("NEURAL NETWORK:");
                System.out.println("NUMBER OF LAYERS:" + dense.size());
                System.out.println("INPUT LAYER:" + input.name + ":" + input.neuron.length);
                for (int k = 0; k < input.neuron.length; k++) {
                    System.out.println("INPUT LAYER:" + input.name + ":" + k + ":" + input.neuron[k].getActivationFunction().getClass().getSimpleName());
                }

                for(int i = 1;i < dense.size() -1; i++ ) {
                    NeuralLayer nl = dense.get(i);
                    System.out.println("HIDDEN LAYER:" + nl.name + ":" + i + ":" + nl.neuron.length);
                    for (int j = 0; j < nl.neuron.length; j++) {
                        System.out.println("HIDDEN LAYER:" + nl.name + ":" + j + ":" + nl.neuron[j].getActivationFunction().getClass().getSimpleName() + ":W:" + nl.neuron[j].weight.size() + ":I:" + nl.neuron[j].getInputs().size() );
                    }
                }
                System.out.println("OUTPUT LAYER:" + output.name + ":" + output.neuron.length);
                for (int m = 0; m < output.neuron.length; m++) {
                    System.out.println("OUTPUT LAYER:" + output.name + ":" + output.neuron[m].getActivationFunction().getClass().getSimpleName());
                }
                if( optimizer != null ) {
                    System.out.println("OPTIMIZER:" + optimizer.getClass().getSimpleName() + ":");
                }

            }
        } catch(Exception ex) {
            Log.info(ex);
            ex.printStackTrace();
        }
    }


    public Optimizer getOptimizer() {
        return optimizer;
    }
}