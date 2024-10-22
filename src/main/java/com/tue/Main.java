import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MainGame implements ActionListener {
    private Clip clip;
    private JFrame frame;
    private JButton buttonInfo, goBack, level1, level2, level3, playButton;
    private JPanel startScreen, instructionScreen;
    private JLabel backGround;
    private Boolean[] levels;

    public MainGame() {
        initializeGame();
        setupAudio();
        setupUI();
    }

    private void initializeGame() {
        levels = new Boolean[]{false, false, false, true};
        frame = new JFrame("Memory Card Game");
        startScreen = new JPanel();
        instructionScreen = new JPanel(new BorderLayout());
        backGround = new JLabel(new ImageIcon("src/background.png"));

        // Set default frame properties
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setLayout(new BorderLayout());
    }


    private void setupAudio() {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("src/winning_sound.wav"));
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    private void setupUI() {
        // Initialize buttons
        buttonInfo = createButton("src/infobutton.png", this, 875, 25, 100, 100);
        goBack = createButton("src/backButton.png", this, 200, 530, 102, 54);
        playButton = createButton("src/playbutton.png", this, 405, 505, 192, 42);
        level1 = createButton("src/level1.png", this, 250, 220, 150, 159);
        level2 = createButton("src/level2.png", this, 445, 342, 150, 160);
        level3 = createButton("src/level3.png", this, 630, 495, 150, 157);

        // Add components to frame
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
            handlePlayButton();
        } 
    }

    private void handlePlayButton() {
        boolean completed = true;
        for (boolean level : levels) {
            if (!level) {
                completed = false;
                break;
            }
        }
        if (completed) {
            congratulations();
        } else {
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
        instructionScreen.setBounds(200, 0, 600, 600);
        JLabel instructions = new JLabel(new ImageIcon("src/instructions.png"));
        instructionScreen.add(instructions);
        goBack.setVisible(true);
        frame.add(goBack);
        frame.add(instructionScreen);

        // Replace start screen with instruction screen
        startScreen.removeAll();
        startScreen.add(instructionScreen);
        buttonInfo.setVisible(false);
        playButton.setVisible(false);
        startScreen.revalidate();
    }

    private void levelMenu() {
        startScreen.removeAll();
        backGround = new JLabel(new ImageIcon("src/levelBackground.png"));
        backGround.setLayout(new BorderLayout());

        level1.setVisible(true);
        level2.setVisible(true);
        level3.setVisible(true);

        displayStars();
        startScreen.add(backGround);
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

        startScreen.removeAll();
        goBack.setVisible(false);
        buttonInfo.setVisible(true);
        playButton.setVisible(true);
        startScreen.add(backGround);
        startScreen.revalidate();
        startScreen.repaint();
    }

    private void displayStars() {
        for (int i = 0; i < levels.length; i++) {
            drawStar(levels[i], i);
        }
    }

    private void drawStar(boolean filled, int position) {
        JLabel star = new JLabel(new ImageIcon(filled ? "src/fullStar.png" : "src/emptyStar.png"));
        star.setBounds(540 + 100 * position, 5, 60, 60);
        backGround.add(star);
    }

    private void congratulations() {
        JFrame congratFrame = new JFrame("Congratulations!");
        JPanel endScreen = new JPanel(new BorderLayout());
        congratFrame.setSize(498, 500);
        JLabel congrats = new JLabel(new ImageIcon("src/congratulations.gif"));
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
