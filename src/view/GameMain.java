package view;//************************************************************
//
//  BlackJackDemo.Java        Authors: Lewis, Chase, Coleman
//
//  Provides the driver for a graphical blackjack game
//
//************************************************************

import javax.swing.*;
import java.awt.*;

public class GameMain {

    public static void main(String[] args) {
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(new JLabel("North East"), BorderLayout.CENTER);
        northPanel.add(new JLabel("North West"), BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(new JLabel("South East"), BorderLayout.WEST);
        southPanel.add(new JLabel("South West"), BorderLayout.WEST);


        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(northPanel, BorderLayout.NORTH);
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        JFrame frame = new JFrame("BorderLayout Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }
}