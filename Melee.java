import java.awt.*;
import java.io.*;
import java.awt.event.*;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.imageio.ImageIO;
import javax.swing.Timer;

/**
The Melee class is a subclass of the <code> CharacterType </code>
abstract class. It is the type of character the player uses
inside the game.

@author Anthony B. Deocadiz Jr. (232166)
@author Ramona Miekaela S. Laciste (233403)
@version March 25, 2024
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

public class Melee extends CharacterType {

    private Timer swordTimer;

    /**
     * Constructor for the Melee class.
     * @param cm The CharacterManager object.
     */
    public Melee(CharacterManager cm){
        
        maxHp = 200;
        hp = 200;
        atk = 5;
        def = 10;

        this.cm = cm;

        attacking = false;
        getImages();

        img = idle;

        alive = true;

    }

     /**
     * Returns the character type.
     * @return A string representing the character type.
     */
    @Override
    public String showCharacterType() {
        return "Melee";
    }

    /**
     * Loads images for the Melee character.
     */
    @Override
    public void getImages(){

        try {

            idle = ImageIO.read(getClass().getResourceAsStream("/Sprites/MeleeSprites/idle_melee.png"));
            front1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/MeleeSprites/front1_melee.png"));
            front2 = ImageIO.read(getClass().getResourceAsStream("/Sprites/MeleeSprites/front2_melee.png"));
            back1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/MeleeSprites/back1_melee.png"));
            back2 = ImageIO.read(getClass().getResourceAsStream("/Sprites/MeleeSprites/back2_melee.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/MeleeSprites/left1_melee.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/Sprites/MeleeSprites/left2_melee.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/MeleeSprites/right1_melee.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/Sprites/MeleeSprites/right2_melee.png"));
            weaponImg = ImageIO.read(getClass().getResourceAsStream("/Sprites/Weapons/sword.png"));

            imageList[0] = idle;
            imageList[1] = front1;
            imageList[2] = front2;
            imageList[3] = left1;
            imageList[4] = left2;
            imageList[5] = right1;
            imageList[6] = right2;
            imageList[7] = back1;
            imageList[8] = back2;

        } catch (IOException e) {
            System.out.println("IOException in CharacterType.getImages()");
        }
    }

    /**
     * Draws the character's weapon.
     * @param g2d The graphics context.
     */
    @Override
    public void drawWeapon(Graphics2D g2d) {
  
        g2d.rotate(rotation + (Math.toRadians(-90)),(cm.getX()+(cm.getWidth()/2)),cm.getY()+(cm.getHeight()*2/3));
        g2d.drawImage(weaponImg, (int) (cm.getX()+(cm.getWidth()/2)-12), (int) (cm.getY()+(cm.getHeight()*2/3)), 25, 95, null);

    }

    @Override
    public void drawAttacks(Graphics2D g2d){
        // unused due to having no projectiles for the given class
    }

    /**
     * Changes the rotation of the character.
     * @param yPos The y-coordinate of the target position.
     * @param xPos The x-coordinate of the target position.
     */
    @Override
    public void changeRotation(double yPos, double xPos){
        
        double dy = yPos - (cm.getY()+(cm.getHeight()*2/3));
        double dx = xPos - (cm.getX()+(cm.getWidth()/2));
        rotation = Math.atan2(dy,dx);

    }

     /**
     * Initiates an attack action.
     */
    @Override
    public void attack(){
        
        if (alive){
            attacking = true;
            attackMovement();
        }
        
    }

     /**
     * Retrieves the list of projectiles.
     * @return Always returns null as there are no projectiles for Melee class.
     */
    @Override
    public CopyOnWriteArrayList<Projectiles> getProjectiles(){
        return null;
    }

    /**
     * Initiates the movement of the character's attack.
     */
    @Override
    public void attackMovement(){

        if (swordTimer == null || !swordTimer.isRunning()){

            ActionListener moveSword = new ActionListener() {

                double angle = Math.toRadians(0);
                double tempRotation = rotation;
                boolean swingRight = true;
                
                @Override
                public void actionPerformed(ActionEvent ae) {
                    
                    if (swingRight){
                        if (Math.abs(angle) < Math.toRadians(75)){
                            rotation += Math.toRadians(3);
                            angle += Math.toRadians(3);
                            
                        } else {
                            swingRight = false;
                        }
                    } else {
                        if (angle > 0){
                            rotation -= Math.toRadians(3);
                            angle -= Math.toRadians(3);
                        } else {
                            rotation = tempRotation;
                            swordTimer.stop();
                            attacking = false;
                        }
                    }
                }
            };
            swordTimer = new Timer(15, moveSword);
            swordTimer.start();
        }

    }

    /**
        Checks whether the player is currently attacking with their weapon.
        @param enemy is the enemy being checked if the player's weapon is colliding with them
    **/
    @Override
    public void weaponCollidingWithEnemy(Enemy enemy) {

        double initialX = (cm.getX()+(cm.getWidth()/2)-12);
        double width = 25;
        double swordPositionX = initialX + (Math.cos(rotation) * width);
        
        double initialY = (cm.getY()+(cm.getHeight()*2/3));
        double height = 95;
        double swordPositionY = initialY + (Math.sin(rotation) * height);



        if (attacking){

            boolean colliding = false;

            colliding = !(swordPositionX + width <= enemy.getX()
            || swordPositionX >= enemy.getX() + enemy.getWidth()
            || swordPositionY + height <= enemy.getY()
            || swordPositionY >= enemy.getY() + enemy.getHeight());

            if (colliding){
                enemy.getEnemyType().takeDamage(atk);
            }
            
        }
    }

}
