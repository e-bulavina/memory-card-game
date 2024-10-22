package main.java.com.tue;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

class CardComponent extends JComponent {
    private CardFlipListener cardFlipListener;
    private final String name;
    private final CardType cardType;
    private final Image frontImage;
    private final Image backImage;
    private Clip clip;
    private boolean isRevealed = false;
    private boolean isForeverRevealed = false;

    public String getName() {
        return name;
    }

    public CardComponent(String fileName, Level level) {

        // Parse filename
        String name = fileName.split("_")[0];
        String cardType = fileName.split("_")[1].split("[.]", 0)[0];
        this.name = name;
        this.cardType = CardType.valueOf(cardType.toUpperCase());


        // Front image
        ImageIcon frontIcon = new ImageIcon("src/main/resources/images/cards/cardfaces/"+ level.toString().toLowerCase() + "/" + fileName);
        frontImage = frontIcon.getImage();

        // Back image
        ImageIcon backIcon = new ImageIcon("src/main/resources/images/cards/back_design.png");
        backImage = backIcon.getImage();

        //Sound
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("src/main/resources/audio/flip_sfx.wav"));
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }


        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isForeverRevealed) {
                    isRevealed = !isRevealed;
                    repaint();

                    if (clip != null) {
                        clip.setFramePosition(0);
                        clip.start();
                    }

                    if (cardFlipListener != null) {
                        cardFlipListener.onCardFlip(CardComponent.this);
                    }
                }
            }
        });
    }

    public void flipDownCard() {
        isRevealed = false;
        repaint();
    }

    public void setCardForeverRevealed() {
        isForeverRevealed = true;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        Image currentImage = isRevealed ? frontImage : backImage;
        if (currentImage != null) {
            g.drawImage(currentImage, 0, 0, 300, 300, this);
        }
    }

    public void setCardFlipListener(CardFlipListener cardFlipListener) {
        this.cardFlipListener = cardFlipListener;
    }


}
