package com.maksymiliangach.ai.DataManager;

import java.util.ArrayList;
import java.util.List;

public class Column {
    private String name;
    private List<Double> data;

    public Column(String name){
        this.name = name;
        this.data = new ArrayList<>();
    }

    public String getName() { return  name; }
    public void setName(String name) { this.name = name; }
    public void add(double value) { this.data.add(value); }
    public double getRow(int i) { return data.get(i); }
    public void removeRow(int i) {data.remove(i);}
    public double[] toArray() {
        Double[] array = new Double[data.size()];
        for(int x = 0; x<data.size() ; x++){
            array[x] = data.get(x);
        }
        return java.util.Arrays.stream(array).mapToDouble(Double::doubleValue).toArray();
    }

    // Copy constructor
    private Column(Column original){
        this.name = original.name;
        this.data = new ArrayList<>(original.data);
    }

    public Column copy(){
        return new Column(this);
    }

}
