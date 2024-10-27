package code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayingField implements CardFlipListener {
    private List<CardComponent> userPickedCards = new ArrayList<>();
    private int totalPairs; // Total pairs of cards
    private int matchedPairs = 0; // Number of matched pairs
    private MainGame mainGame; // Reference to MainGame
    private Level level; // Current level

    // Method to generate cards based on the level
    private static List<CardComponent> cardGenerator(Level level) {
        List<CardComponent> cards = new ArrayList<>();
        Path folderPath = Paths.get("src/resources/images/cards/cardfaces/" + level.toString().toLowerCase());

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(folderPath)) {
            for (Path entry : stream) {
                CardComponent card = new CardComponent(entry.getFileName().toString(), level);
                cards.add(card);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cards;
    }

    // Game logic for flipping cards
    private void GameLogic() {
        if (userPickedCards.size() == 2) {
            CardComponent firstPickedCard = userPickedCards.get(0);
            CardComponent secondPickedCard = userPickedCards.get(1);

            if (firstPickedCard.getName().equals(secondPickedCard.getName())) {
                firstPickedCard.setCardForeverRevealed();
                secondPickedCard.setCardForeverRevealed();
                matchedPairs++; // Increment matched pairs

                // Check if all pairs are matched
                if (matchedPairs == totalPairs) {
                    notifyGameCompletion(); // Notify MainGame that the game is complete
                }
            } else {
                Timer timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        firstPickedCard.flipDownCard();
                        secondPickedCard.flipDownCard();
                    }
                });
                timer.setRepeats(false); // Ensure the timer only runs once
                timer.start();
            }
            userPickedCards.clear();
        }
    }

    // Constructor to initialize PlayingField with MainGame reference
    public PlayingField(MainGame mainGame, Level level) {
        this.mainGame = mainGame;
        this.level = level; // Set current level
        List<CardComponent> cards = cardGenerator(level);
        for (CardComponent card : cards) {
            card.setCardFlipListener(this);
        }

        Collections.shuffle(cards);


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


        for (CardComponent card : cards) {
            MyPanel.add(card);
        }


        frame.getContentPane().add(MyPanel, "Center"); // Paste MyPanel into JFrame

        frame.setSize(1000, 800);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        totalPairs = cards.size() / 2; // Calculate total pairs
    }
    // Notify MainGame when the level is completed
    private void notifyGameCompletion() {
        mainGame.levelCompleted(level); // Notify MainGame about level completion
    }

    // Handle card flips
    public void onCardFlip(CardComponent card) {
        userPickedCards.add(card);
        GameLogic();
    }
}
