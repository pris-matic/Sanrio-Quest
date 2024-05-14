import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

// TODO currently unused, from mieka.
public class LevelSelectionPanel extends JPanel {

    private Image background;
    private Image[] characterSprites;
    private int backgroundX = 0;
    private int spriteIndex = 3; // Start with still sprite
    private boolean isMovingLeft = false;
    private boolean isMovingRight = false;
    private Timer timer;

    public LevelSelectionPanel(String playerType) {
        setFocusable(true);
        requestFocus();

        // Load background image (same for all characters)
        background = new ImageIcon("IMG_3185.PNG").getImage();

        // Load character sprites based on character type
        characterSprites = loadCharacterSprites(playerType);

        // Start timer to switch character sprites
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!isMovingLeft && !isMovingRight) {
                    // Player is not moving, use still sprite
                    spriteIndex = 3;
                } else {
                    // Player is moving, switch between walking sprites
                    spriteIndex = (spriteIndex == 1) ? 2 : 1;
                }
                repaint();
            }
        }, 0, 500); // Switch sprites every 500 milliseconds

        // Set up keyboard event handlers for scrolling background
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_LEFT) {
                    isMovingLeft = true;
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                    isMovingRight = true;
                }
                updateSprite();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_LEFT) {
                    isMovingLeft = false;
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                    isMovingRight = false;
                }
                updateSprite();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // Draw background image
        g2d.drawImage(background, -backgroundX, 0, null);

        // Draw character sprite
        int characterX = getWidth() / 2 - characterSprites[spriteIndex].getWidth(this) / 2;
        int characterY = getHeight() / 2 - characterSprites[spriteIndex].getHeight(this) / 2;
        g2d.drawImage(characterSprites[spriteIndex], characterX, characterY, null);

        g2d.dispose();
    }

    /**
     * Load character sprites based on the character type
     **/
    private Image[] loadCharacterSprites(String playerType) {
        Image[] sprites = new Image[4]; // Including the still sprite
        for (int i = 0; i < 3; i++) {
            String spritePath = getSpritePath(playerType, i);
            sprites[i] = new ImageIcon(spritePath).getImage();
        }
        // Load still sprite
        sprites[3] = new ImageIcon(getSpritePath(playerType, 3)).getImage();
        return sprites;
    }

    /**
     * Get the path of the character sprite based on character type and index
     **/
    private String getSpritePath(String playerType, int index) {
        switch (playerType) {
            case "melee":
                return "sprite_melee_" + index + ".jpg";
            case "ranger":
                return "sprite_ranger_" + index + ".jpg";
            case "wizard":
                return "sprite_wizard_" + index + ".jpg";
            default:
                // Default sprite path
                return "sprite_default.jpg";
        }
    }

    /**
     * Update the character sprite based on movement
     **/
    private void updateSprite() {
        if (!isMovingLeft && !isMovingRight) {
            // Player is not moving, use still sprite
            spriteIndex = 3;
        } else {
            // Player is moving, switch between walking sprites
            spriteIndex = (spriteIndex == 1) ? 2 : 1;
        }
        repaint();
    }
}
