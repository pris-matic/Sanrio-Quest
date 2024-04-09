import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
The Player class is the one that will be interacted
by a person. It stores information about their character
such as their movement, and current stats.

@author Anthony B. Deocadiz Jr. (232166)
@author Ramona Miekaela S. Laciste (233403)
@version March 16, 2024
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

public class Player extends CharacterManager{
    
    private String name;
    private CharacterType ct;
    
    public Player(String name, String characterType, int xPos, int yPos){
        
        this.name = name;

        if (characterType.equalsIgnoreCase("ranger")){
            ct = new Ranger(this);
        } else if (characterType.equalsIgnoreCase("melee")){
            ct = new Melee(this);
        } else if (characterType.equalsIgnoreCase("wizard")){
            ct = new Wizard(this);
        }
        
        x = xPos;
        y = yPos;

        up = false;
        down = false;
        left = false;
        right = false;

        height = 100;
        width = 75;
        speed = 5;
        
    }

    @Override
    public void drawCharacter(Graphics2D g2d){
       
        Rectangle2D.Double playerSprite = new Rectangle2D.Double(x, y, width, height);
        g2d.setColor(Color.BLACK);
        // TODO change to drawImage if all images are here
        // g2d.drawImage(ct.getCharacterImages(),(int) x,(int) y,width,height,null);  
        g2d.fill(playerSprite);
        g2d.drawString(name, (int) x, (int)y);
   
    }

    public CharacterType getCharacterType(){
        return ct;
    }

    public String getName(){
        return name;
    }

    public void setName(String n){
        name = n;
    }

    public void trackMovement(String direction, boolean move){
    
        if (direction.equalsIgnoreCase("up")){
            up = move;
        }
        if (direction.equalsIgnoreCase("down")){
            down = move;
        }
        if (direction.equalsIgnoreCase("left")){
            left = move;
        }
        if (direction.equalsIgnoreCase("right")){
            right = move;
        }
    }

}
