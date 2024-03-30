import java.awt.*;
import java.awt.geom.*;

/**
The Ranger class is a subclass of the <code> CharacterType </code>
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

public class Ranger extends CharacterType {
    
    public Ranger(CharacterManager cm){
        hp = 75;
        atk = 10;
        def = 3;

        atkCooldown = 0.333;
        skillCooldown = 5;
        specialCooldown = 15;

        this.cm = cm;

    }

    @Override
    public String showCharacterType(){
        return "Ranger";
    }

    @Override
    public void drawWeapon(Graphics2D g2d) {
        
        g2d.setColor(Color.GREEN);
        // Rectangle2D.Double rangerWep = new Rectangle2D.Double(p.getX()-25,p.getY()+p.getHeight()-55,115,35);
        Rectangle2D.Double rangerWep = new Rectangle2D.Double(cm.getX()-25,cm.getY()+cm.getHeight()-55,115,25);
        g2d.rotate(rotation-Math.toRadians(90),cm.getX()+32.5,cm.getY()+cm.getHeight()-55);
        g2d.fill(rangerWep);
        
    }

    @Override
    public void changeRotation(double yPos, double xPos){

        double dy = yPos - (cm.getY()+cm.getHeight()-55);
        double dx = xPos - (cm.getX()+32.5);
        rotation = Math.atan2(dy,dx);

    }

}
