package com.maksymiliangach.ai.DataManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a column of data with a name and a list of double values.
 * Provides methods to manipulate and retrieve data from the column.
 */
public class Column {
    private String name;
    private final List<Double> data;


    /**
     * Constructs a new Column with the specified name.
     *
     * @param name the name of the column
     */
    public Column(String name) {
        this.name = name;
        this.data = new ArrayList<>();
    }

    /**
     * Constructs a new Column as a copy of the specified column.
     *
     * @param original the column to copy
     */
    private Column(Column original) {
        this.name = original.name;
        this.data = new ArrayList<>(original.data);
    }


    /**
     * Returns the name of the column.
     *
     * @return the name of the column
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the column.
     *
     * @param name the new name of the column
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Adds a value to the column.
     *
     * @param value the value to add
     */
    public void add(double value) {
        this.data.add(value);
    }

    /**
     * Returns the value at the specified row index.
     *
     * @param i the row index
     * @return the value at the specified row index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public double getRow(int i) {
        return this.data.get(i);
    }

    /**
     * Removes the value at the specified row index and shifts subsequent elements.
     *
     * @param i the row index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public void removeRow(int i) {
        this.data.remove(i);
    }

    /**
     * Returns the number of values in the column.
     *
     * @return the number of values in the column
     */
    public int size() {
        return this.data.size();
    }

    /**
     * Returns an array containing all the values in the column.
     *
     * @return an array containing all the values in the column
     */
    public double[] toArray() {
        return data.stream().mapToDouble(Double::doubleValue).toArray();
    }

    /**
     * Returns a copy of the column.
     *
     * @return a copy of the column
     */
    public Column copy() {
        return new Column(this);
    }
}
