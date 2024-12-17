package com.maksymiliangach.ai.DataManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.function.Function;

public class JDataFrame{
    private String regex = ",";
    // TODO: create fields for custom delimiter and regex
    // Pay attention to static scope!
    //private String delimiter = "\n";
    private String fileName;
    private Scanner sc;
    private int width;
    private int height;
    private List<Column> data;
    private JDataFrame(){
        data = new ArrayList<>();
        this.width = 0;
        this.height = 0;
    }

    public static JDataFrame loadCSV(String filename) throws IOException {
        JDataFrame dataFrame = new JDataFrame();
        dataFrame.fileName = filename;
        dataFrame.sc = new Scanner(new File(filename));
        dataFrame.sc.useDelimiter("\n");
        dataFrame.create();
        return dataFrame;
    }

    private void create(){
        createColumns();
        createRows();
        //getColumnContent();
    }

    private void createColumns(){
        String head = sc.next();
        String[] headSplit = head.split(regex);
        this.width = headSplit.length;
        String[] columnNames = new String[this.width];
        System.arraycopy(headSplit, 0, columnNames, 0, headSplit.length);
        for(String s : columnNames) {
            data.add(new Column(s));
        }
    }

    private void createRows() {
        //TODO: detect datatype
        while(sc.hasNext()){
            String[] row = sc.next().split(",");
            for (int x = 0 ; x < width ; x++) {
                data.get(x).add(Double.parseDouble(row[x]));
            }
            height++;
        }
    }

    public Double[] getRow(int index) {
        Double[] row = new Double[width];
        for (int i = 0; i < width; i++) {
            row[i] = data.get(i).getRow(index);
        }
        return row;
    }

    public void drop(int... indices) {
        int shiftCounter = 0;
        for (int index : indices) {
            data.remove(index - shiftCounter);
            shiftCounter++;
            width--;
        }
    }

    public double[] getColumn(int index) {
        return data.get(index).toArray();
    }

    public double[][] getColumns(int... indices) {
        double[][] columns = new double[indices.length][height];
        for (int i = 0; i < indices.length; i++) {
            columns[i] = this.getColumn(indices[i]);
        }
        return columns;
    }

    public static double[][] toTraining(double[]... cols) {
        int numFeatures = cols.length;
        int numSamples = cols[0].length;
        double[][] trainingData = new double[numFeatures][numSamples];
        for (int i = 0; i < cols.length; i++) {
            trainingData[i] = cols[i];
        }
        return trainingData;
    }

    //TODO: this is very weird...
    @FunctionalInterface
    public interface ColumnFormula {
        double apply(double... values);
    }

    //TODO: create clear documentation on how this function works
    public void addCustomColumn(String name, ColumnFormula formula, double[]... columns) {
        double[] newColumnData = new double[height];
        for (int i = 0; i < height; i++) {
            double[] values = new double[columns.length];
            for (int j = 0; j < columns.length; j++) {
                values[j] = columns[j][i];
            }
            newColumnData[i] = formula.apply(values);
        }
        Column newColumn = new Column(name);
        for (double value : newColumnData) {
            newColumn.add(value);
        }
        data.add(newColumn);
        width++;
    }

    private String buildHead(boolean printIndices, int n) {

        StringBuilder headString = new StringBuilder();

        headString.append("\n");
        //TODO: decide if this functionality should be removed...
        String[] nameSplit = fileName.split("/");
        headString.append(nameSplit[nameSplit.length - 1]);
        headString.append("\n");

        for (Column col : this.data){
            headString.append(col.getName());
            if(printIndices) {
                headString.append("[").append(this.data.indexOf(col)).append("]");
            }
            headString.append(" ");
        }

        headString.append("\n");

        for (int x = 0 ; x < n ; x++){
            for(Column col : this.data){
                headString.append(col.getRow(x));
                headString.append(" ");
            }
            headString.append("\n");
        }
        headString.append("\n");

        return headString.toString();
    }

    //TODO: smash or pass ?
    public String head() { return buildHead(false, 5); }
    public String head(boolean printIndices) { return buildHead(printIndices, 5); }
    public String head(int n) { return buildHead(false, n); }
    public String head(boolean printIndices, int n) { return buildHead(printIndices, n); }
}