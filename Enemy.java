import java.awt.*;
import java.util.ArrayList;

/**
    The Enemy Class is responsible for moving the enemies throughout
    the game. It is the opponent of the <Code>Player</code> class.

    <p></p>
    Automated movement, and automated firing is done by this class.

    @author Anthony B. Deocadiz Jr. (232166)
    @author Ramona Miekaela S. Laciste (233403)
    @version May 01, 2024
**/

/*
    We have not discussed the Java language code in our program
    with anyone other than my instructor or the teaching assistants
    assigned to this course.

    We have not used Java language code obtained from another student,
    or any other unauthorized source, either modified or unmodified.

    If any Java language code or documentation used in our program
    was obtained from another source, such as a textbook or website,
    that has been clearly noted with a proper citation in the comments
    of our program.
*/

public class Enemy extends CharacterManager {

    private int frameLock;

    /**
        Constructs an enemy object at the specified location.
        @param enemyType is the type of enemy to be generated
        @param xPos is the x-coordinate of the enemy
        @param yPos is the y-coordinate of the enemy
        @see EnemyType
    **/
    public Enemy(String enemyType, int xPos, int yPos){
        
        if (enemyType.equalsIgnoreCase("ghost")){
            et = new Ghost(this);
        } else if (enemyType.equalsIgnoreCase("WingedCreature")){
            et = new WingedCreature(this);
        }

        x = xPos;
        y = yPos;

        up = false;
        down = false;
        left = false;
        right = false;

        invincible = false;
        invincibleCooldown = 0;

        width = getEnemyType().getWidth();
        height = getEnemyType().getHeight();
        
        speed = 2;
        frameLock = 0;
        
    }

    /**
        Draws the specified character to the canvas
        @param g2d is the Graphics2D object that will draw the instance.
    **/
    @Override
    public void drawCharacter(Graphics2D g2d) {
        
        g2d.drawImage(et.useImage(), (int) x,(int) y, width, height, null);
        g2d.setColor(Color.BLACK);
        g2d.drawString(et.showEnemyType(), (int) x, (int)y);
        
    }

    /**
        Generates a random value that determines where the enemy will move next.
        It only moves, and therefore does not account where the nearest player is.
        <p></p> It also calls a random value for the rotation of the object, which is used
        to determine where it will fire.
        @see EnemyType#setRotation(double)
    **/
    public void moveAutomatically(){

        int i = (int) (Math.random() * 4);
        
        frameLock ++;

        if (frameLock == 120){

            double randomRotation = (Math.random() * 360);
            getEnemyType().setRotation(Math.toRadians(randomRotation));

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

            getEnemyType().displayImage();
        }

    }

    /**
        Determines whether the enemy was hit by a projectile. Since this class
        is inside the server, the x and y variables of the player's projectiles are checked.
        @param projectilesX is the x value of the projectile
        @param projectilesY is the y value of the projectile
    **/
    public boolean isCollidingWithBullet(ArrayList<Double> projectilesX, ArrayList<Double> projectilesY){
    
        boolean colliding = false;

        for (int i = 0; i < projectilesX.size(); i ++){
            
            colliding = !(projectilesX.get(i) + 20 <= this.x ||
            projectilesX.get(i) >= this.x + this.width ||
            projectilesY.get(i) + 20 <= this.y ||
            projectilesY.get(i) >= this.y + this.height);

            if (colliding){
                break;
            }   
        }
        return colliding;

    }

    /**
        Determines whether the enemy is colliding with a player. Since this class
        is inside the server, the x and y variables of the player is checked.
        @param playerX is the player's x position.
        @param playerY is the player's y position.
    **/
    public boolean enemyCollidingWithPlayer(double playerX, double playerY){
       
        boolean collision = false;

            collision = !(playerX + 60 <= this.x||
            playerX>= this.x + this.width ||
            playerY + 90 <= this.y||
            playerY>= this.y + this.height);

        return collision;

    }

}
