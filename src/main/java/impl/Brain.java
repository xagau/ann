package impl; /** Copyright (c) 2019-2022 placeh.io,
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
import ann.*;
import iaf.SoftmaxFunction;
import log.Log;
import optimizer.Adam;

import java.io.Serializable;
import java.util.ArrayList;


public class Brain implements Serializable {

    Genome genome = null;
    public NeuralNet ann = null;

    public Brain(Genome genome, NeuralNet net) {
        ann = net;
        this.genome = genome;
    }

    public void calculate(ArrayList<Double> input)
    {
        try {
            ann.getInputLayer().calculate(input);
            OutputLayer ol = ann.getOutputLayer();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public void backprop()
    {
        try {
            NeuralLayer outputLayer = ann.getOutputLayer();
            outputLayer.backprop();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public synchronized double getOutput() {
        Log.info(hashCode() + " entity not set");
        return -1;
    }

    public static void main(String args[]){
        Genome g = GenomeFactory.generate(1000);

        HiddenLayer dense = new HiddenLayer(Settings.MAX_NEURONS, "dense");
        HiddenLayer dropout = new HiddenLayer(2, "dropout");
        HiddenLayer hidden = new HiddenLayer(Settings.MAX_NEURONS, "hidden");

        OutputLayer output = null;
        SoftmaxFunction softmax = new SoftmaxFunction(Action.values().length);
        try {
            output = new OutputLayer(1, softmax, "output");
            softmax.setLayer(output);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        InputLayer input = null;
        try {
            input = new InputLayer(128, "input");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        NeuralNet net = new NeuralNet();
        net.addLayer(input);
        net.addLayer(dense);
        net.addLayer(dropout);
        net.addLayer(hidden);
        net.addLayer(output);
        net.setOptimizer(new Adam());
        net.init();
        net.display();
        Brain b = new Brain(g, net);
        ArrayList<Double> arr = new ArrayList<>();
        arr.add(1.0);
        arr.add(0.1);
        arr.add(4.6);
        arr.add(20.0);
        b.calculate(arr);
        Log.info("" +b.getOutput());

    }
}

