import code.TextPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;

public class MainGame implements ActionListener {
    JFrame frame = new JFrame("Memory Card Game");
    JButton buttonInfo = new JButton(new ImageIcon("src/infobutton.png"));
    JButton goBack = new JButton(new ImageIcon("src/backButton.png"));
    JButton level1 = new JButton(new ImageIcon("src/level1.png"));
    JButton level2 = new JButton(new ImageIcon("src/level2.png"));
    JButton level3 = new JButton(new ImageIcon("src/level3.png"));
    JButton playButton = new JButton(new ImageIcon("src/playbutton.png"));
    JPanel startScreen = new JPanel();
    JPanel instructionScreen = new JPanel(new BorderLayout());
    JLabel backGround = new JLabel(new ImageIcon("src/background.png"));
    Boolean[] levels = new Boolean[]{false, false, false, false};


    public MainGame() {
        // Setup frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setLayout(new BorderLayout());

        // Setup button

        buttonInfo.setBorder(BorderFactory.createEmptyBorder());
        buttonInfo.setBounds(875, 25, 100, 100);
        buttonInfo.addActionListener(this);

        goBack.setBorder(BorderFactory.createEmptyBorder());

        playButton.setBorder(BorderFactory.createEmptyBorder());
        playButton.setBounds(405, 505, 192, 42);

        level1.setBorder(BorderFactory.createEmptyBorder());
        level1.addActionListener(this);

        level2.setBorder(BorderFactory.createEmptyBorder());
        level2.addActionListener(this);

        level3.setBorder(BorderFactory.createEmptyBorder());


        playButton.addActionListener(this);
        startScreen.add(backGround);

        frame.add(buttonInfo);
        frame.add(level1);
        frame.add(level2);
        frame.add(level3);

        frame.add(playButton);
        // Setup start screen
        frame.add(startScreen, BorderLayout.CENTER);

        // Make frame visible
        level1.setVisible(false);
        level2.setVisible(false);
        level3.setVisible(false);
        frame.setVisible(true);

    }


    public void actionPerformed(ActionEvent click) {
        Object source = click.getSource();
        if (source == buttonInfo) {
            showInstructions();
        } else if (source == goBack) {
            returnToMainMenu();
        } else if (source == playButton) {
            boolean completed = true;
            for(int i = 0; i < levels.length; i++){
                if(!levels[i]){
                    completed = false;
                }
            }
            if(completed){
                congratulations();
            } else {
                levelMenu();
            }

        }

    }


   private void showInstructions() {

        // Instructions panel
        instructionScreen.setBounds(200, 0, 600,600);
        JLabel instructions= new JLabel(new ImageIcon("src/instructions.png"));
        goBack.addActionListener(this);

        instructionScreen.add(instructions);
        goBack.setBounds(200, 530, 102, 54);
        frame.add(goBack);
        frame.add(instructionScreen);
        instructionScreen.revalidate();


        // Replace start screen with instruction screen
        startScreen.removeAll();
        startScreen.add(instructionScreen);
        buttonInfo.setVisible(false);
        playButton.setVisible(false);
        startScreen.revalidate();    // Important to call

    }

    private void levelMenu(){
        startScreen.removeAll();
        backGround = new JLabel(new ImageIcon("src/levelBackground.png"));
        backGround.setLayout(new BorderLayout());

        level1.setBounds(250, 220, 150, 159);
        level1.addActionListener(this);
        level2.setBounds(445, 342, 150, 160);
        level2.addActionListener(this);
        level3.setBounds(630, 495, 150, 157);
        level3.addActionListener(this);

        level1.setVisible(true);
        level2.setVisible(true);
        level3.setVisible(true);

        startScreen.add(backGround);
        Stars();
        playButton.setVisible(false);
        buttonInfo.setVisible(false);
    }

    private void playLevel(){

    }

    private void returnToMainMenu() {
        instructionScreen.removeAll();
        instructionScreen.revalidate();
        instructionScreen.repaint();

        // Reset to start screen
        startScreen.removeAll();
        goBack.setVisible(false);
        buttonInfo.setVisible(true);
        playButton.setVisible(true);
        startScreen.add(backGround);
        startScreen.revalidate();
        startScreen.repaint();
    }

    private void drawStar(boolean value, int position) {
        JLabel star = new JLabel(new ImageIcon(value ? "src/fullStar.png" : "src/emptyStar.png"));
        int x = 540 + 100 * position; // Adjust this based on your layout
        int y = 5; // Keep y consistent for now
        star.setBounds(x, y, 60, 60);
        backGround.add(star);
    }


    private void Stars(){
        for(int i = 0; i < levels.length; i++){
            drawStar(levels[i], i);
        }
    }

    private void congratulations(){
        JFrame congratFrame = new JFrame("Congratulations!");
        JPanel endScreen = new JPanel(new BorderLayout());
        congratFrame.setSize(498, 500);
        JLabel congrats  = new JLabel(new ImageIcon("src/congratulations.gif"));
        endScreen.add(congrats);
        congratFrame.add(endScreen, BorderLayout.CENTER);
        congratFrame.setVisible(true);
    }
    public static void main(String[] args) {
        new MainGame(); // Ensure thread safety
    }
}
