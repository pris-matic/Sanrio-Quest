import java.awt.Graphics2D;

/**
The CharacterManager abstract class is used to define the 
movement of different characters that will be defined
throughout the game. Both enemies and the Player
rely on this class for their movement mechanics.

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

public abstract class CharacterManager {
    
    protected double x, y; // player's coordinates
    protected int width, height;
    protected boolean up,down,left,right;

    public void moveX(double movement){
        x += movement;
    }

    public void moveY(double movement){
        y += movement;
    }

    public void setX(double position){
        x = position;
    }

    public void setY(double position){
        y = position;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public boolean isMovingUp(){
        return up;
    }

    public boolean isMovingDown(){
        return down;
    }

    public boolean isMovingLeft(){
        return left;
    }

    public boolean isMovingRight(){
        return right;
    }

    //TODO do collision later!
    public boolean isCollidingWithWall(Walls wall){
        return !(wall.getX() + wall.getWidth() <= x ||
        wall.getX() >= x + width ||
        wall.getY() + wall.getHeight() <= y + (height * 0.75) ||
        wall.getY() >= y + height);
    }

    public abstract void drawCharacter(Graphics2D g2d);

}
