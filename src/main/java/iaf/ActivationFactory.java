package iaf;

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
import ann.NeuralLayer;

import java.lang.reflect.Field;

public class ActivationFactory {
        public static IActivationFunction create(int index, double input, NeuralLayer layer) {
            Field[] fl = ActivationFunction.class.getDeclaredFields();
            Field f = fl[index];
            ActivationFunction af = ActivationFunction.valueOf(f.getName());

            switch (af) {
                case SIGMOID:
                    Sigmoid sigmoid = new Sigmoid(input);
                    sigmoid.setLayer(layer);
                    return sigmoid;
                case SWISH:
                    SwishFunction sf = new SwishFunction(input);
                    sf.setLayer(layer);
                    return sf;
                case RELU:
                    ReluFunction rl = new ReluFunction(input);
                    rl.setLayer(layer);
                    return rl;
                case LEAKY_RELU:
                    LeakyReluFunction lrl = new LeakyReluFunction(input);
                    lrl.setLayer(layer);
                    return lrl;
                case GAUSSIAN:
                    GaussianFunction gau = new GaussianFunction(input);
                    gau.setLayer(layer);
                    return gau;
                case SOFTMAX:
                    SoftmaxFunction smx = new SoftmaxFunction(input);
                    smx.setLayer(layer);
                    return smx;
                default:
                    ReluFunction smax = new ReluFunction(input);
                    smax.setLayer(layer);
                    return smax;
            }
        }


        public static IActivationFunction create(int gene, NeuralLayer layer) {
            double input = gene;
            IActivationFunction iaf = create(gene, input, layer);
            return iaf;
        }
    }