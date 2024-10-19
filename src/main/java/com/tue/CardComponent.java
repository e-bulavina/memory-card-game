package main.java.com.tue;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

class CardComponent extends JComponent {
    private final Image frontImage;
    private final Image backImage;
    private Clip clip;
    private boolean isFlipped = false;


    public CardComponent(String fileName) {


        // Front image
        ImageIcon frontIcon = new ImageIcon("src/com/java/main/resources/images/frontImages/" + fileName);
        frontImage = frontIcon.getImage();

        // Back image
        ImageIcon backIcon = new ImageIcon("src/com/java/main/resources/images/backIcon.png");
        backImage = backIcon.getImage();

        //Sound
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("src/com/java/main/resources/audio/flipSound.wav"));
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }


        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                isFlipped = !isFlipped;
                repaint();


                if (clip != null) {
                    clip.setFramePosition(0);
                    clip.start();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        Image currentImage = isFlipped ? frontImage : backImage;
        if (currentImage != null) {
            g.drawImage(currentImage, 0, 0, 300, 300, this);
        }
    }

}
