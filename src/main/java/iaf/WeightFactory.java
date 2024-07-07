package iaf;

import java.util.ArrayList;

public class WeightFactory {
    public static ArrayList<Double> generate(double min, double max, int size)
    {
        ArrayList<Double> list = new ArrayList<>();
        for(int i =0; i < size; i++){
            list.add(Math.random()*max);
        }
        return list;
    }
}
