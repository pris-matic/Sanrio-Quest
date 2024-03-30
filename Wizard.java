import java.awt.Graphics2D;

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

public class Wizard extends CharacterType{

    public Wizard(CharacterManager cm){
        hp = 125;
        atk = 7;
        def = 5;

        atkCooldown = 0.5;
        skillCooldown = 4;
        specialCooldown = 10;

        this.cm = cm;
    }

    @Override
    public String showCharacterType() {
        return "Wizard";
    }

    @Override
    public void drawWeapon(Graphics2D g2d) {
        
    }

    @Override
    public void changeRotation(double yPos, double xPos){
        
    }
 
}
