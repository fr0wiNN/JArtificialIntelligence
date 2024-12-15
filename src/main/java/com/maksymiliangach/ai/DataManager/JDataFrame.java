package com.maksymiliangach.ai.DataManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JDataFrame{
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
        String[] headSplit = head.split(",");
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

    public Double[] getColumn(int index) {
        return data.get(index).toArray();
    }

    public String head(){

        StringBuilder headString = new StringBuilder();

        headString.append("\n");

        for (Column col : this.data){
            headString.append(col.getName());
            headString.append(" ");
        }

        headString.append("\n");

        for (int x = 0 ; x < 5 ; x++){
            for(Column col : this.data){
                headString.append(col.getRow(1));
                headString.append(" ");
            }
            headString.append("\n");
        }
        headString.append("\n");

        return headString.toString();
    }
}