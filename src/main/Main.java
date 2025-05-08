package main;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        try {
            System.setOut(new java.io.PrintStream(System.out, true, "UTF-8"));
            // for new commit
        } catch (java.io.UnsupportedEncodingException e) {
            // unlikely on modern JDKs
            e.printStackTrace();
        }

        final boolean runCLI = false;

        if (runCLI) {
            cli.Game.startCLI();
        } else {
            JFrame window = new JFrame();
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setResizable(false);
            window.setTitle("2D Game-OOP Project");

            GamePanel gamePanel = new GamePanel();
            window.add(gamePanel);
            window.pack();

            window.setLocationRelativeTo(null);
            window.setVisible(true);

            gamePanel.setupGame();
            gamePanel.startGameThread();

        }
    }
}
