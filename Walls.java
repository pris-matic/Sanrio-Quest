import java.awt.*;
import java.awt.geom.*;

/**
The Walls Class creates walls for the game. Collision is checked
between the <code>CharacterManager</code> objects, and projectiles
if there is any.

@author Anthony B. Deocadiz Jr. (232166)
@author Ramona Miekaela S. Laciste (233403)
@version March 30, 2024
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

public class Walls implements BackgroundManager {
    
    private double x,y, width, height;
    private Color color;

    /**
        Instantiates a wall object. Collision will be checked in other
        classes.
        @param x
        @param y
        @param width
        @param height
    **/
    public Walls(double x, double y, double width, double height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        color = new Color(135,211,130);
    }
    
    /**
     * Draws the wall on the game screen.
     * @param g2d The Graphics2D object used for drawing.
     */
    @Override
    public void draw (Graphics2D g2d){
        
        g2d.setColor(color);
        Rectangle2D.Double wall = new Rectangle2D.Double(x,y,width,height);
        g2d.fill(wall);

    }

    /**
     * Gets the x-coordinate of the wall.
     * @return The x-coordinate of the wall.
     */
    public double getX(){
        return x;
    }
    
    /**
     * Gets the y-coordinate of the wall.
     * @return The y-coordinate of the wall.
     */
    public double getY(){
        return y;
    }

    /**
     * Gets the width of the wall.
     * @return The width of the wall.
     */
    public double getWidth(){
        return width;
    }

    /**
     * Gets the height of the wall.
     * @return The height of the wall.
     */
    public double getHeight(){
        return height;
    }

}
