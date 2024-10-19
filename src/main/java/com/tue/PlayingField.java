package main.java.com.tue;

import javax.swing.*;
import javax.swing.JFrame;
import java.awt.*;

public class PlayingField {
    public PlayingField() {


        CardComponent card1 = new CardComponent("anteater.png");

        CardComponent card2 = new CardComponent("fox.png");

        CardComponent card3 = new CardComponent("hippo.png");

        CardComponent card4 = new CardComponent("horse.png");

        CardComponent card5 = new CardComponent("lizard.png");

        CardComponent card6 = new CardComponent("penguin.png");

        CardComponent card7 = new CardComponent("anteaterText.png");

        CardComponent card8 = new CardComponent("foxText.png");

        CardComponent card9 = new CardComponent("hippoText.png");

        CardComponent card10 = new CardComponent("horseText.png");

        CardComponent card11 = new CardComponent("lizardText.png");

        CardComponent card12 = new CardComponent("penguinText.png");


        JFrame frame = new JFrame("GridLayout");

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        JPanel panel5 = new JPanel();

        panel1.setBackground(Color.white);
        panel2.setBackground(Color.white);
        panel3.setBackground(Color.white);
        panel4.setBackground(Color.white);
        panel5.setBackground(Color.white);

        panel1.setPreferredSize(new Dimension(50, 50));
        panel2.setPreferredSize(new Dimension(50, 50));
        panel3.setPreferredSize(new Dimension(50, 50));
        panel4.setPreferredSize(new Dimension(50, 50));
        panel5.setPreferredSize(new Dimension(50, 50));

        frame.add(panel1, BorderLayout.NORTH);
        frame.add(panel2, BorderLayout.WEST);
        frame.add(panel3, BorderLayout.EAST);
        frame.add(panel4, BorderLayout.SOUTH);
        frame.add(panel5, BorderLayout.CENTER);

        JPanel MyPanel = new JPanel();

        MyPanel.setLayout(new GridLayout(3, 4));  // 4x3 Grid
        MyPanel.setBackground(Color.white);


        MyPanel.add(card2);
        MyPanel.add(card11);
        MyPanel.add(card4);
        MyPanel.add(card9);
        MyPanel.add(card12);
        MyPanel.add(card5);
        MyPanel.add(card3);
        MyPanel.add(card6);
        MyPanel.add(card7);
        MyPanel.add(card1);
        MyPanel.add(card8);
        MyPanel.add(card10);


        frame.getContentPane().add(MyPanel, "Center"); // Paste MyPanel into JFrame

        frame.setSize(1400, 1200);
        frame.setVisible(true);
    }


    public static void main(String[] args) {
        new PlayingField();
    }
}
