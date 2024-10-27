package code;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MainGame implements ActionListener {
    private Clip clip; // Audio clip for sounds
    private JFrame frame; // Main application window
    private JButton buttonInfo, goBack, level1, level2, level3, playButton; // UI buttons
    private JPanel startScreen, instructionScreen; // Panels for UI
    private JLabel backGround; // Background image
    public static Boolean[] levels; // Completed levels tracking

    public MainGame() {
        initializeGame(); // Initialize game settings
        setupAudio(); // Set up audio
        setupUI(); // Set up the user interface
    }

    private void initializeGame() {
        levels = new Boolean[]{false, false, false}; // Levels not completed
        frame = new JFrame("Memory Card Game"); // Main window
        startScreen = new JPanel(); // Start screen panel
        instructionScreen = new JPanel(new BorderLayout()); // Instructions panel
        backGround = new JLabel(new ImageIcon("src/resources/images/background.png")); // Background image

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
    }

    private void setupAudio() {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("src/resources/audio/winning_sound.wav"));
            clip = AudioSystem.getClip(); // Create audio clip
            clip.open(audioStream); // Open audio stream
        } catch (Exception e) {
            e.printStackTrace(); // Handle audio errors
        }
    }

    private void setupUI() {
        buttonInfo = createButton("src/resources/images/infobutton.png", this, 875, 25, 100, 100);
        goBack = createButton("src/resources/images/backButton.png", this, 20, 670, 174, 95);
        playButton = createButton("src/resources/images/playbutton.png", this, 405, 505, 192, 42);
        level1 = createButton("src/resources/images/level1.png", this, 250, 220, 150, 159);
        level2 = createButton("src/resources/images/level2.png", this, 445, 342, 150, 160);
        level3 = createButton("src/resources/images/level3.png", this, 630, 495, 150, 157);

        startScreen.add(backGround);
        frame.add(buttonInfo);
        frame.add(level1);
        frame.add(level2);
        frame.add(level3);
        frame.add(playButton);
        frame.add(startScreen, BorderLayout.CENTER);

        // Initially hide level buttons
        level1.setVisible(false);
        level2.setVisible(false);
        level3.setVisible(false);

        frame.setVisible(true); // Show the window
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
            showInstructions(); // Show instructions
        } else if (source == goBack) {
            returnToMainMenu(); // Return to main menu
        } else if (source == playButton) {
            levelMenu(); // Show level selection
        } else if (source == level1) {
            playLevel(Level.PLANTS); // Start level 1
        } else if (source == level2) {
            playLevel(Level.ANIMALS); // Start level 2
        } else if (source == level3) {
            playLevel(Level.ANIMALS); // Start level 3
        }
    }

    private void playLevel(Level level) {
        new PlayingField(this, level); // Launch specified level
    }

    public void levelCompleted(Level level) {
        if (level == Level.PLANTS) {
            levels[0] = true; // Mark level 1 as completed
        } else if (level == Level.ANIMALS) {
            levels[1] = levels[1] ? true : true; // Mark level 2 or 3
            levels[2] = true;
        }

        levelMenu(); // Update star display
        if (allLevelsPassed()) {
            congratulations(); // Show congratulations if all levels completed
        }
    }

    private boolean allLevelsPassed() {
        for (Boolean levelPassed : levels) {
            if (!levelPassed) return false; // At least one level not passed
        }
        return true; // All levels passed
    }

    private void showInstructions() {
        JLabel instructions = new JLabel(new ImageIcon("src/resources/images/instructions.png"));
        instructionScreen.add(instructions);
        instructionScreen.setBounds(0, 0, 1000, 800);
        goBack.setVisible(true);
        frame.add(goBack);
        frame.add(instructionScreen);

        startScreen.removeAll(); // Clear start screen
        startScreen.add(instructionScreen);
        buttonInfo.setVisible(false); // Hide info button
        playButton.setVisible(false); // Hide play button
        startScreen.revalidate(); // Update UI
        startScreen.repaint();
    }

    private void levelMenu() {
        startScreen.removeAll(); // Clear previous components
        backGround = new JLabel(new ImageIcon("src/resources/images/levelBackground.png"));
        startScreen.add(backGround);

        level1.setVisible(true); // Show level buttons
        level2.setVisible(true);
        level3.setVisible(true);

        displayStars(); // Display stars for completed levels

        playButton.setVisible(false); // Hide play button
        buttonInfo.setVisible(false); // Hide info button

        // Revalidate and repaint to update UI
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
        instructionScreen.removeAll(); // Clear instruction screen
        instructionScreen.revalidate();
        instructionScreen.repaint();

        startScreen.removeAll(); // Clear start screen
        goBack.setVisible(false); // Hide go back button
        buttonInfo.setVisible(true); // Show info button
        playButton.setVisible(true); // Show play button
        startScreen.add(backGround); // Add background to start screen
        startScreen.revalidate();
        startScreen.repaint();
    }

    private void displayStars() {
        backGround.removeAll(); // Clear previous stars
        for (int i = 0; i < levels.length; i++) {
            drawStar(levels[i], i); // Draw star for each level
        }
        backGround.revalidate(); // Update background
        backGround.repaint();
    }

    private void drawStar(boolean filled, int position) {
        // Draw a star based on completion status
        JLabel star = new JLabel(new ImageIcon(filled ? "src/resources/images/fullStar.png" : "src/resources/images/emptyStar.png"));
        star.setBounds(540 + 100 * position, 5, 60, 60);
        backGround.add(star); // Add star to background
    }

    private void congratulations() {
        JFrame congratFrame = new JFrame("Congratulations!"); // Congratulations window
        JPanel endScreen = new JPanel(new BorderLayout());
        congratFrame.setSize(500, 500);
        congratFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        congratFrame.setLocationRelativeTo(null);
        JLabel congrats = new JLabel(new ImageIcon("src/resources/images/congratulations.gif"));
        endScreen.add(congrats);
        congratFrame.add(endScreen, BorderLayout.CENTER);

        if (clip != null) {
            clip.setFramePosition(0); // Reset audio clip
            clip.start(); // Play audio
        }
        congratFrame.setVisible(true); // Show congratulations window
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGame::new); // Launch game
    }
}
