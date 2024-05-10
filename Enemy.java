import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Enemy extends CharacterManager {

    private int frameLock;

    public Enemy(String enemyType, int xPos, int yPos){
        
        if (enemyType.equalsIgnoreCase("slime")){
            ct = new Slime(this);
        } else if (enemyType.equalsIgnoreCase("fairy")){
            ct = new Fairy(this);
        }

        x = xPos;
        y = yPos;

        up = false;
        down = false;
        left = false;
        right = false;

        width = getCharacterType().getWidth();
        height = getCharacterType().getHeight();
        
        speed = 2;
        frameLock = 0;
        
    }

    @Override
    public void drawCharacter(Graphics2D g2d) {
        
        Rectangle2D.Double enemySprite = new Rectangle2D.Double(x, y, width, height);
        g2d.setColor(Color.RED);
        g2d.fill(enemySprite);
        g2d.setColor(Color.BLACK);
        g2d.drawString(ct.showCharacterType(), (int) x, (int)y);
        
    }

    public void moveAutomatically(){

        Random r = new Random();
        int i = r.nextInt(4);
        frameLock ++;

        if (frameLock == 60){

            double randomYVal = (Math.random() * 2 - 1) * Math.PI;
            double randomXVal = (Math.random() * 2 - 1) * Math.PI;  
            getCharacterType().changeRotation(randomYVal, randomXVal);

            switch (i) {
            
                case 0: // moves up
                    if (down){
                        moveCharacter("down", false);
                    }
                    moveCharacter("up", true);
                    break;
                case 1: // moves down
                    if (up){
                        moveCharacter("up", false);
                    }
                    moveCharacter("down", true);
                    break;
                case 2: // moves left
                    if (right){
                        moveCharacter("right", false);
                    }
                    moveCharacter("left", true);
                    break;
                case 3: // moves right
                    if (left){
                        moveCharacter("left", false);
                    }
                    moveCharacter("right", true);
                    break;
            }
            frameLock = 0;
        }

    }

}
