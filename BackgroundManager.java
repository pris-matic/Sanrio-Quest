import java.awt.Graphics2D;

/**
The BackgroundManager interface draws the walls and other
objects that are seen in the background. Its main purpose
is to draw the playing field of the players.

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

public interface BackgroundManager {

    /**
        Draws the object into the canvas
        @param g2d is the Graphics2D object that will draw the object.
    **/
    public void draw(Graphics2D g2d);
    
}
