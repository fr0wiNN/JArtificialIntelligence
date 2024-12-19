package com.maksymiliangach.ai.Logger;

import java.io.Serializable;

public interface JLogger extends Serializable {

    /**
     *
     * @param metaData information about models current state <pre>
     *
     * <br>Input order convention:<br>
     * <br>metaData[0] - current loss</br>
     * <br>metaData[1] - current epoch</br>
     * <br>metaData[2] - total epochs</br>
     * <br>metaData[3] - current weights</br>
     * <br>metaData[4] - current bias</br>
     *
     */
    public default void log(double... metaData){};

    public static final JLogger DEFAULT_LOGGER = new JLogger() {
        @Override
        public void log(double... metaData) {
        }
    };

    public static final JLogger SIMPLE_LOGGER = new JLogger() {
        @Override
        public void log(double... metaData) {
            StringBuilder sb = new StringBuilder();
            sb.append("[ SIMPLE LOG ]\n");
            sb.append("Current Loss: ").append((long) metaData[0]).append("\n");
            sb.append("Current Epoch: ").append((int) metaData[1]).append("\n");

            System.out.println(sb.toString());
        }
    };

    public static final JLogger PROGRESS_BAR = new JLogger() {
        @Override
        public void log(double... metaData) {
            StringBuilder sb = new StringBuilder();

            // Progress percent = current_epoch / total_epochs * 100
            int progress = (int) ((metaData[1] / metaData[2]) * 100);
            sb.append("[ PROGRESS BAR LOG ]: ");
            sb.append("[");
            for(int x = 0 ; x < 100 ; x++){
                if (x < progress)  { sb.append("="); }
                else if (x == progress) { sb.append(">"); }
                else               { sb.append(" "); }
            }
            sb.append("] ");
            sb.append(progress).append("%\n");

            System.out.println(sb.toString());
        }
    };
}
