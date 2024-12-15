package com.maksymiliangach.ai.DataManager;

import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        JDataFrame df = JDataFrame.loadCSV("src/main/java/com/maksymiliangach/ai/DataManager/real_estate_dataset.csv");
        System.out.println(df.head() + "\n");
        System.out.println(Arrays.stream(df.getRow(10)).toList().toString());
    }
}
