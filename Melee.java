import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.awt.event.*;
import java.util.ArrayList;
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
        hp = 200;
        atk = 4;
        def = 10;

        this.cm = cm;

        attacking = false;
    }

    @Override
    public String showCharacterType() {
        return "Melee";
    }

    @Override
    public BufferedImage getCharacterImages(){
        return null;
    }

    @Override
    public void drawWeapon(Graphics2D g2d) {
        g2d.setColor(Color.GREEN);
        Rectangle2D.Double meleeWep = new Rectangle2D.Double(cm.getX()+(cm.getWidth()/2),cm.getY()+(cm.getHeight()/2),115,25);
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
    
    //TODO what possible projectiles
    @Override
    public ArrayList<Projectiles> getProjectiles(){
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
