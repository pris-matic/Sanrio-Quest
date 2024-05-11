import java.awt.*;

/**
    The Enemy Class is responsible for moving the enemies throughout
    the game.

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

        if (frameLock == 60){

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
        }

    }

    /**
        Determines whether the enemy was hit by a projectile.
        @param player is the player to be called, along with its projectiles, if there is any.
    **/
    public void isCollidingWithBullet(Player player){
    
        if (player.getCharacterType().getProjectiles() != null){
            for (CharacterType.Projectiles p : player.getCharacterType().getProjectiles()){
                if (p.isCollidingWith(this)){
    
                    p.setActive();
                    p.setProjectileX(-5000);
                    p.setProjectileY(-5000);
                    this.getEnemyType().takeDamage(player.getCharacterType().getAttack());
                    break;
                }
            }
        }
    }

}
