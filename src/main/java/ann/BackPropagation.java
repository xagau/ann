package ann;
/*
 * Our back propagation algorithm written in Java
 */


import java.util.ArrayList;
import java.util.Arrays;

public class BackPropagation {

    // this is a test data entry that we made up.
    public static void main(String[] args) {
        ArrayList<double[]> inputs = new ArrayList<double[]>();
        ArrayList<double[]> outputs = new ArrayList<double[]>();
        double[] one_input = {0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0};
        inputs.add(one_input);
        double[] one_output = {0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0};
        outputs.add(one_output);

        double[] two_input = {0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0};
        inputs.add(two_input);
        double[] two_output = {0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0};
        outputs.add(two_output);

        double[] three_input = {0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0};
        inputs.add(three_input);
        double[] three_output = {0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0};
        outputs.add(three_output);

        double[] four_input = {0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0};
        inputs.add(four_input);
        double[] four_output = {0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0};
        outputs.add(four_output);

        double learningRate = 0.1;
        double maxIterations = 100000;
        double errorThreshold = 0.00001;

        double[][] weights = backPropagate(inputs, outputs, learningRate, maxIterations, errorThreshold);
        System.out.println(Arrays.toString(weights));

        // Test the training data
        double[] test_input = {0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0};
        double[] result;

        result = test(test_input, weights, 0.5);
        System.out.println("Results:" + Arrays.toString(result));

    }
    // back propagate algorithm
    // returns an array of weight values
    public static double[][] backPropagate(ArrayList<double[]> inputs, ArrayList<double[]> outputs, double learningRate, double maxIterations, double errorThreshold) {
        double[][] weights = new double[inputs.size()][outputs.size()];

        // Initialize the weights to random numbers
        for(int i = 0; i < inputs.size(); i++) {
            int sz = inputs.get(i).length;
            System.out.println("sz:" + sz);
            weights[i] = new double[sz];
            for(int j = 0; j < outputs.size(); j++) {
                weights[i][j] = Math.random();
            }
        }
        System.out.println("back prop weights: " + weights.length + " ");

        int iteration = 0;
        double error = 1.0;
        while(iteration < maxIterations && error > errorThreshold) {
            error = 0.0;
            for(int i = 0; i < inputs.size(); i++) {
                double[] input = inputs.get(i);
                double[] output = outputs.get(i);

                double[] predicted = test(input, weights, 0.5);
                // calculate the error for this input
                for(int j = 0; j < predicted.length; j++) {
                    double outputError = Math.pow(output[j] - predicted[j], 2);
                    error += outputError;
                    // calculate the weight change for this input
                    double weightChange = learningRate * input[j] * (output[j] - predicted[j]);
                    weights[j][i] += weightChange;

                }
            }

            System.out.println("Error:" + error + " iteration " + iteration);

            iteration++;
        }

        // return the weights
        return weights;
    }

    // test a set of input values with the weights and calculate an output array
    public static double[] test(double[] inputs, double[][] weights, double threshold) {
        double[] outputs = new double[weights.length];

        System.out.println(weights.length);
        System.out.println(weights[0].length);
        System.out.println(outputs.length);
        System.out.println(inputs.length);

        for(int i = 0; i < weights.length; i++) {
            double weightedSum = 0.0;
            for(int j = 0; j < inputs.length; j++) {
                double input = inputs[j];
                double weight = weights[i][j];
                weightedSum += input * weight;
            }
            outputs[i] = thresholdFunction(weightedSum);
        }

        return outputs;
    }

    // threshold function that returns either 1 or 0
    public static double thresholdFunction(double x) {
        if(x > 0) {
            return 1.0;
        }
        else {
            return 0.0;
        }
    }

}