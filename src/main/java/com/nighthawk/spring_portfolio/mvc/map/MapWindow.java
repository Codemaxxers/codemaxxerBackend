package com.nighthawk.spring_portfolio.mvc.map;

import javax.swing.JFrame;

public class MapWindow {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Map Render");

        MapRender gamePanel = new MapRender();//error
        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
    }
}
