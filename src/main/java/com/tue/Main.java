package main.java.com.tue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main implements ActionListener {
    JFrame frame = new JFrame("Memory Card Game");
    JButton buttonInfo = new JButton(new ImageIcon("src/main/resources/images/buttonicons/game_info.png"));
    JButton goBack = new JButton("Main Menu");
    JButton level1 = new JButton(new ImageIcon("src/main/resources/images/buttonicons/level_1.png"));
    JButton level2 = new JButton(new ImageIcon("src/main/resources/images/buttonicons/level_2.png"));
    JButton level3 = new JButton(new ImageIcon("src/main/resources/images/buttonicons/level_3.png"));
    JButton playButton = new JButton(new ImageIcon("src/main/resources/images/buttonicons/play.png"));
    JPanel startScreen = new JPanel();
    JPanel instructionScreen = new JPanel(new BorderLayout());
    JLabel backGround = new JLabel(new ImageIcon("src/main/resources/images/backgrounds/title_screen.png"));
    Boolean[] levels = new Boolean[3];


    public Main() {
        // Setup frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setLayout(new BorderLayout());

        // Setup button
        instructionScreen.setPreferredSize(new Dimension(600, 600));
        buttonInfo.setBorder(BorderFactory.createEmptyBorder());
        buttonInfo.setBounds(875, 25, 100, 100);
        buttonInfo.addActionListener(this);
        goBack.setBorder(BorderFactory.createEmptyBorder());
        goBack.setBounds(675, 25, 50, 50);
        buttonInfo.addActionListener(this);
        playButton.setBorder(BorderFactory.createEmptyBorder());
        playButton.setBounds(405, 505, 192, 42);
        level1.addActionListener(this);
        level2.addActionListener(this);
        level3.addActionListener(this);
        level1.setBorder(BorderFactory.createEmptyBorder());
        level1.setBounds(250, 250, 100, 100);
        playButton.addActionListener(this);
        startScreen.add(backGround);

        frame.add(buttonInfo);
        frame.add(playButton);
        // Setup start screen
        frame.add(startScreen, BorderLayout.CENTER);

        // Make frame visible
        frame.setVisible(true);

    }


    public void actionPerformed(ActionEvent click) {
        Object source = click.getSource();
        if (source == buttonInfo) {
            showInstructions();
        } else if (source == goBack) {
            returnToMainMenu();
        } else if (source == playButton) {
            levelMenu();
        } else if (source == level1) {
            playLevel(Level.ANIMALS);
        } else if (source == level2) {
            playLevel(Level.PLANTS);
        } else if (source == level3) {
            playLevel(Level.RELIEF);
        }

    }

    private void showInstructions() {

        // Instructions panel

        JPanel TextPanel = new JPanel(new BorderLayout());
        JLabel instructions= new JLabel(new ImageIcon("src/main/resources/images/misc/game_instructions.png"));
        goBack.addActionListener(this);

        TextPanel.add(instructions, BorderLayout.NORTH);
        TextPanel.setPreferredSize(new Dimension(600, 600));
        instructionScreen.add(TextPanel, BorderLayout.CENTER);
        instructionScreen.add(goBack, BorderLayout.SOUTH);
        instructionScreen.revalidate();


        // Replace start screen with instruction screen
        startScreen.removeAll();
        startScreen.add(instructionScreen, BorderLayout.CENTER);
        buttonInfo.setVisible(false);
        playButton.setVisible(false);
        startScreen.revalidate();    // Important to call

    }

    private void levelMenu(){
        startScreen.removeAll();
        backGround = new JLabel(new ImageIcon("src/main/resources/images/backgrounds/main_menu.png"));
        startScreen.add(backGround);
        startScreen.add(level1);
        startScreen.add(level2);
        startScreen.add(level3);
        level1.setVisible(true);
        playButton.setVisible(false);
        buttonInfo.setVisible(false);
    }

    private void playLevel(Level level){
        new PlayingField(level);
    }

    private void returnToMainMenu() {
        instructionScreen.removeAll();
        instructionScreen.revalidate();
        instructionScreen.repaint();

        // Reset to start screen
        startScreen.removeAll();
        buttonInfo.setVisible(true);
        playButton.setVisible(true);
        startScreen.add(backGround);
        startScreen.revalidate();
        startScreen.repaint();
    }

    public static void main(String[] args) {
        new Main();
    }
}
