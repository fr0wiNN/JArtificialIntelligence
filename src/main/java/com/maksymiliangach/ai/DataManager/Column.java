package com.maksymiliangach.ai.DataManager;

import java.util.ArrayList;
import java.util.List;

public class Column {
    private String name;
    private List<Double> data;

    // Public constructor
    public Column(String name) {
        this.name = name;
        this.data = new ArrayList<>();
    }

    // Copy constructor
    private Column(Column original) {
        this.name = original.name;
        this.data = new ArrayList<>(original.data);
    }

    // Public methods
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void add(double value) {
        this.data.add(value);
    }

    public double getRow(int i) {
        return this.data.get(i);
    }

    public void removeRow(int i) {
        this.data.remove(i);
    }

    public int size() {
        return this.data.size();
    }

    public double[] toArray() {
        return data.stream().mapToDouble(Double::doubleValue).toArray();
    }

    public Column copy() {
        return new Column(this);
    }
}
