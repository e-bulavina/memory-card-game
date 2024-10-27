package main.java.com.tue;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MainGame implements ActionListener {
    private PlayingField playingField;

    private Clip clip; // For audio playback
    private JFrame frame; // Main game window
    private JButton buttonInfo; // Button to show game instructions
    private JButton goBack; // Button to return to the previous menu
    private JButton level1; // Button for Level 1
    private JButton level2; // Button for Level 2
    private JButton level3; // Button for Level 3
    private JButton playButton; // Button to start the game
    private JPanel startScreen; // Panel for the main menu
    private JPanel instructionScreen; // Panel for instructions
    private JLabel backGround; // Background image
    public static Boolean[] levels; // Array to track completed levels

    public MainGame() {
        initializeGame(); // Set up game elements
        setupAudio(); // Load audio resources
        setupUI(); // Create user interface
    }

    private void initializeGame() {
        levels = new Boolean[]{false, false, false}; // Initialize level completion states
        frame = new JFrame("Memory Card Game"); // Set up main game window
        startScreen = new JPanel(); // Create main menu panel
        instructionScreen = new JPanel(new BorderLayout()); // Create instruction panel
        backGround = new JLabel(new ImageIcon("src/main/resources/images/backgrounds/title_screen.png")); // Load background image

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit on close
        frame.setSize(1000, 800); // Set window size
        frame.setLocationRelativeTo(null); // Center the window
        frame.setLayout(new BorderLayout()); // Use border layout
    }

    private void setupAudio() {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("src/main/resources/audio/winning_sound.wav")); // Load winning sound
            clip = AudioSystem.getClip(); // Get audio clip
            clip.open(audioStream); // Open audio stream
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace(); // Handle exceptions
        }
    }

    private void setupUI() {
        // Create buttons with icons
        buttonInfo = createButton("src/main/resources/images/buttonicons/game_info.png", this, 875, 25, 100, 100);
        goBack = createButton("src/main/resources/images/buttonicons/back_button.png", this, 20, 670, 174, 95);
        playButton = createButton("src/main/resources/images/buttonicons/play.png", this, 405, 505, 192, 42);
        level1 = createButton("src/main/resources/images/buttonicons/level_1.png", this, 250, 220, 150, 160);
        level2 = createButton("src/main/resources/images/buttonicons/level_2.png", this, 445, 342, 150, 160);
        level3 = createButton("src/main/resources/images/buttonicons/level_3.png", this, 630, 495, 150, 160);

        // Add components to the start screen
        startScreen.add(backGround);
        frame.add(buttonInfo);
        frame.add(level1);
        frame.add(level2);
        frame.add(level3);
        frame.add(playButton);
        frame.add(startScreen, BorderLayout.CENTER);

        // Hide level buttons initially
        level1.setVisible(false);
        level2.setVisible(false);
        level3.setVisible(false);

        frame.setVisible(true); // Make the frame visible
    }

    private JButton createButton(String iconPath, ActionListener listener, int x, int y, int width, int height) {
        JButton button = new JButton(new ImageIcon(iconPath)); // Create button with icon
        button.setBorder(BorderFactory.createEmptyBorder()); // Remove button border
        button.setBounds(x, y, width, height); // Set button position and size
        button.addActionListener(listener); // Add action listener
        return button; // Return created button
    }

    @Override
    public void actionPerformed(ActionEvent click) {
        Object source = click.getSource(); // Get the source of the action
        if (source == buttonInfo) {
            showInstructions(); // Show instructions
        } else if (source == goBack) {
            returnToMainMenu(); // Return to main menu
        } else if (source == playButton) {
            levelMenu(); // Show level selection
        } else if (source == level1) {
            playLevel(Level.PLANTS); // Start Level 1
        } else if (source == level2) {
            playLevel(Level.ANIMALS); // Start Level 2
        } else if (source == level3) {
            playLevel(Level.RELIEF); // Start Level 3
        }
    }

    private void playLevel(Level level) {
        playingField = new PlayingField(this, level); // Start a new playing field for the selected level
    }

    public void levelCompleted(Level level) {
        playingField.close();
        playingField = null;
        // Mark the completed level
        if (level == Level.PLANTS) {
            levels[0] = true; // Level 1 completed
        } else if (level == Level.ANIMALS) {
            levels[1] = true; // Level 2 completed
        } else if (level == Level.RELIEF) {
            levels[2] = true; // Level 3 completed
        }

        levelMenu(); // Update level menu display
        if (allLevelsPassed()) {
            congratulations(); // Show congratulatory message
        }
    }

    private boolean allLevelsPassed() {
        // Check if all levels have been completed
        for (Boolean levelPassed : levels) {
            if (!levelPassed) {
                return false; // At least one level is not completed
            }
        }
        return true; // All levels completed
    }

    private void showInstructions() {
        JLabel instructions = new JLabel(new ImageIcon("src/main/resources/images/misc/game_instructions.png")); // Load instructions image
        instructionScreen.add(instructions);
        instructionScreen.setBounds(0, 0, 1000, 800);
        goBack.setVisible(true); // Show the go back button
        frame.add(goBack);
        frame.add(instructionScreen); // Add instruction screen to frame

        startScreen.removeAll(); // Clear start screen
        startScreen.add(instructionScreen); // Add instruction screen to start screen
        buttonInfo.setVisible(false); // Hide info button
        playButton.setVisible(false); // Hide play button
        startScreen.revalidate(); // Refresh the start screen
        startScreen.repaint(); // Repaint the start screen
    }

    private void levelMenu() {
        startScreen.removeAll(); // Clear previous components
        backGround = new JLabel(new ImageIcon("src/main/resources/images/backgrounds/main_background.png")); // Load level menu background
        startScreen.add(backGround); // Add background to start screen

        // Show level buttons
        level1.setVisible(true);
        level2.setVisible(true);
        level3.setVisible(true);

        displayStars(); // Update star display

        playButton.setVisible(false); // Hide play button
        buttonInfo.setVisible(false); // Hide info button

        // Refresh buttons
        level1.revalidate();
        level1.repaint();
        level2.revalidate();
        level2.repaint();
        level3.revalidate();
        level3.repaint();
        frame.revalidate(); // Refresh frame
        frame.repaint(); // Repaint frame
    }

    private void returnToMainMenu() {
        instructionScreen.removeAll(); // Clear instruction screen
        instructionScreen.revalidate(); // Refresh instruction screen
        instructionScreen.repaint();

        startScreen.removeAll(); // Clear start screen
        goBack.setVisible(false); // Hide go back button
        buttonInfo.setVisible(true); // Show info button
        playButton.setVisible(true); // Show play button
        startScreen.add(backGround); // Add background to start screen
        startScreen.revalidate(); // Refresh start screen
        startScreen.repaint(); // Repaint start screen
    }

    private void displayStars() {
        backGround.removeAll(); // Clear previous stars
        for (int i = 0; i < levels.length; i++) {
            drawStar(levels[i], i); // Draw stars based on level completion
        }
        backGround.revalidate(); // Refresh background
        backGround.repaint(); // Repaint background
    }

    private void drawStar(boolean filled, int position) {
        JLabel star = new JLabel(new ImageIcon(filled ? "src/main/resources/images/buttonicons/full_star.png" : "src/main/resources/images/buttonicons/empty_star.png")); // Load star icon
        star.setBounds(540 + 100 * position, 5, 60, 60); // Set star position
        backGround.add(star); // Add star to background

    }

    private void congratulations() {
        JFrame congratFrame = new JFrame("Congratulations!"); // Create congratulatory window
        JPanel endScreen = new JPanel(new BorderLayout()); // Panel for end screen
        congratFrame.setSize(500, 500); // Set window size
        congratFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close on exit
        congratFrame.setLocationRelativeTo(null); // Center window
        JLabel congrats = new JLabel(new ImageIcon("src/main/resources/images/misc/congratulations.gif")); // Load congratulations animation
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
