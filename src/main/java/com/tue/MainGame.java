package main.java.com.tue;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MainGame implements ActionListener {
    private Clip clip;
    private JFrame frame;
    private JButton buttonInfo;
    private JButton goBack;
    private JButton level1;
    private JButton level2;
    private JButton level3;
    private JButton playButton;
    private JPanel startScreen;
    private JPanel instructionScreen;
    private JLabel backGround;
    public static Boolean[] levels;

    public MainGame() {
        initializeGame();
        setupAudio();
        setupUI();
    }

    private void initializeGame() {
        levels = new Boolean[]{false, false, false};
        frame = new JFrame("Memory Card Game");
        startScreen = new JPanel();
        instructionScreen = new JPanel(new BorderLayout());
        backGround = new JLabel(new ImageIcon("src/main/resources/images/backgrounds/title_screen.png"));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
    }

    private void setupAudio() {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("src/main/resources/audio/winning_sound.wav"));
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    private void setupUI() {
        buttonInfo = createButton("src/main/resources/images/buttonicons/game_info.png", this, 875, 25, 100, 100);
        goBack = createButton("src/main/resources/images/buttonicons/back_button.png", this, 20, 670, 174, 95);
        playButton = createButton("src/main/resources/images/buttonicons/play.png", this, 405, 505, 192, 42);
        level1 = createButton("src/main/resources/images/buttonicons/level_1.png", this, 250, 220, 150, 160);
        level2 = createButton("src/main/resources/images/buttonicons/level_2.png", this, 445, 342, 150, 160);
        level3 = createButton("src/main/resources/images/buttonicons/level_3.png", this, 630, 495, 150, 160);

        startScreen.add(backGround);
        frame.add(buttonInfo);
        frame.add(level1);
        frame.add(level2);
        frame.add(level3);
        frame.add(playButton);
        frame.add(startScreen, BorderLayout.CENTER);

        level1.setVisible(false);
        level2.setVisible(false);
        level3.setVisible(false);

        frame.setVisible(true);
    }

    private JButton createButton(String iconPath, ActionListener listener, int x, int y, int width, int height) {
        JButton button = new JButton(new ImageIcon(iconPath));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setBounds(x, y, width, height);
        button.addActionListener(listener);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent click) {
        Object source = click.getSource();
        if (source == buttonInfo) {
            showInstructions();
        } else if (source == goBack) {
            returnToMainMenu();
        } else if (source == playButton) {
            levelMenu();
        } else if (source == level1) {
            playLevel(Level.PLANTS);
        } else if (source == level2) {
            playLevel(Level.ANIMALS);
        } else if (source == level3) {
            playLevel(Level.RELIEF);
        }
    }

    private void playLevel(Level level) {
        new PlayingField(this, level); // Pass reference to MainGame
    }

    public void levelCompleted(Level level) {
        if (level == Level.PLANTS) {
            levels[0] = true; // Mark level 1 as completed
        } else if (level == Level.ANIMALS) {
            levels[1] = true;
        } else if (level == Level.RELIEF){
            levels[2] = true;
        }

        levelMenu(); // Update star display
        if (allLevelsPassed()) {
            congratulations();
        }
    }

    private boolean allLevelsPassed() {
        for (Boolean levelPassed : levels) {
            if (!levelPassed) {
                return false; // At least one level is not passed
            }
        }
        return true; // All levels are passed
    }

    private void showInstructions() {
        JLabel instructions = new JLabel(new ImageIcon("src/main/resources/images/misc/game_instructions.png"));
        instructionScreen.add(instructions);
        instructionScreen.setBounds(0, 0, 1000, 800);
        goBack.setVisible(true);
        frame.add(goBack);
        frame.add(instructionScreen);

        startScreen.removeAll();
        startScreen.add(instructionScreen);
        buttonInfo.setVisible(false);
        playButton.setVisible(false);
        startScreen.revalidate();
        startScreen.repaint();
    }

    private void levelMenu() {
        startScreen.removeAll();
        backGround = new JLabel(new ImageIcon("src/main/resources/images/backgrounds/main_background.png"));
        startScreen.add(backGround);

        level1.setVisible(true);
        level2.setVisible(true);
        level3.setVisible(true);

        displayStars();

        playButton.setVisible(false);
        buttonInfo.setVisible(false);

        level1.revalidate();
        level1.repaint();
        level2.revalidate();
        level2.repaint();
        level3.revalidate();
        level3.repaint();
        frame.revalidate();
        frame.repaint();
    }

    private void returnToMainMenu() {
        instructionScreen.removeAll();
        instructionScreen.revalidate();
        instructionScreen.repaint();

        startScreen.removeAll();
        goBack.setVisible(false);
        buttonInfo.setVisible(true);
        playButton.setVisible(true);
        startScreen.add(backGround);
        startScreen.revalidate();
        startScreen.repaint();
    }

    private void displayStars() {
        backGround.removeAll(); // Clear previous stars
        for (int i = 0; i < levels.length; i++) {
            drawStar(levels[i], i);
        }
        backGround.revalidate();
        backGround.repaint();
    }

    private void drawStar(boolean filled, int position) {
        JLabel star = new JLabel(new ImageIcon(filled ? "src/main/resources/images/buttonicons/full_star.png" : "src/main/resources/images/buttonicons/empty_star.png"));
        star.setBounds(540 + 100 * position, 5, 60, 60);
        backGround.add(star);
    }

    private void congratulations() {
        JFrame congratFrame = new JFrame("Congratulations!");
        JPanel endScreen = new JPanel(new BorderLayout());
        congratFrame.setSize(500, 500);
        congratFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        congratFrame.setLocationRelativeTo(null);
        JLabel congrats = new JLabel(new ImageIcon("src/main/resources/images/misc/congratulations.gif"));
        endScreen.add(congrats);
        congratFrame.add(endScreen, BorderLayout.CENTER);

        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }
        congratFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGame::new); // Ensures thread safety
    }
}
