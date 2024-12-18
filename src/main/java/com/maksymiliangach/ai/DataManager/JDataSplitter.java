package com.maksymiliangach.ai.DataManager;

public class JDataSplitter {

    // JDataFrame[0] - training data
    // JDataFrame[1] - testing data
    public static JDataFrame[] split(JDataFrame df, double trainP, boolean shuffle) {
        if (shuffle) {df.shuffle();}

        int trainSize = (int) (df.getHeight() * trainP); // Get training df height of trainPercent input
        int testSize = df.getHeight() - trainSize;

        JDataFrame dfTrainCopy = df.copy();
        JDataFrame dfTestCopy = df.copy();
        dfTestCopy.removeRow(0);
        System.out.println(dfTrainCopy.getColumn(0).length);

        for (int i = 0; i < testSize; i++) {
            dfTrainCopy.removeRow(trainSize);
        }

        for (int i = 0; i < trainSize; i++) {
            dfTestCopy.removeRow(0);
        }

        return new JDataFrame[]{dfTrainCopy, dfTestCopy};
    }
}
