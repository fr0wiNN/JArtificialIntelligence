package com.maksymiliangach.ai.DataManager;

import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        JDataFrame df = JDataFrame.loadCSV("src/main/java/com/maksymiliangach/ai/DataManager/real_estate_dataset.csv");
        System.out.println(df.head(true));
        double[] c1 = df.getColumn(1);
        double[] c2 = df.getColumn(8);
        double[] c3 = df.getColumn(9);
        df.drop(0,2,3,4,5,6,7,10,11);

        //df.addCustomColumn("Total_Size", JFormulas::addAndSquare, c1, c2);
        df.addCustomColumn("Total_Size_x_Location_Score", Main::sizeTimesScore, c1, c2, c3);

        System.out.println(df.head(true));
    }

    private static double sizeTimesScore(double[] x) {
        return (x[0] + x[1]) * x[2];
    }
}
