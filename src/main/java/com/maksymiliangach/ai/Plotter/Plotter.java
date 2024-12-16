package com.maksymiliangach.ai.Plotter;

import javax.swing.*;

public class Plotter extends JFrame {
    public Plotter(String title, int width, int height){
        super("Hello");
        setSize(width, height);
        createWindow();
    }

    private void createWindow(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }


}
