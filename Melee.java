import java.awt.*;
import java.awt.geom.*;
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

    public Melee(CharacterManager cm){
        
        maxHp = 200;
        hp = 200;
        atk = 4;
        def = 10;

        this.cm = cm;

        attacking = false;
        getImages();

        img = idle;
    }

    @Override
    public String showCharacterType() {
        return "Melee";
    }

    @Override
    public void getImages(){

        try {

            idle = ImageIO.read(getClass().getResourceAsStream("/CharacterSprites/RangerSprites/idle_ranger.png"));
            front1 = ImageIO.read(getClass().getResourceAsStream("/CharacterSprites/RangerSprites/front1_ranger.png"));
            front2 = ImageIO.read(getClass().getResourceAsStream("/CharacterSprites/RangerSprites/front2_ranger.png"));
            back1 = ImageIO.read(getClass().getResourceAsStream("/CharacterSprites/RangerSprites/back1_ranger.png"));
            back2 = ImageIO.read(getClass().getResourceAsStream("/CharacterSprites/RangerSprites/back2_ranger.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/CharacterSprites/RangerSprites/left1_ranger.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/CharacterSprites/RangerSprites/left2_ranger.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/CharacterSprites/RangerSprites/right1_ranger.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/CharacterSprites/RangerSprites/right2_ranger.png"));

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

    @Override
    public void drawWeapon(Graphics2D g2d) {
        g2d.setColor(Color.GREEN);
        Rectangle2D.Double meleeWep = new Rectangle2D.Double(cm.getX()+(cm.getWidth()/2),cm.getY()+(cm.getHeight()/2),95,25);
        g2d.rotate(rotation,cm.getX()+(cm.getWidth()/2),cm.getY()+(cm.getHeight()/2)+12.5);
        g2d.fill(meleeWep);
    }

    @Override
    public void drawAttacks(Graphics2D g2d){

    }

    @Override
    public void changeRotation(double yPos, double xPos){
        
        double dy = yPos - (cm.getY()+(cm.getHeight()/2)+12.5);
        double dx = xPos - (cm.getX()+(cm.getWidth()/2));
        rotation = Math.atan2(dy,dx);

    }

    @Override
    public void attack(){
        
        attacking = true;
        attackMovement();
    }
    
    @Override
    public CopyOnWriteArrayList<Projectiles> getProjectiles(){
        return null;
    }

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

}
