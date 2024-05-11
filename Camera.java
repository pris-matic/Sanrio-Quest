/**
The Camera class is used to create an effect that
would make the canvas shift based on the <code>Player</code>'s position.
<p></p> It is used inside <code>GameCanvas</code>, and will only affect
the current <code>Player</code>, and the other <code>Player</code> entity will also have 
their own camera.

@author Anthony B. Deocadiz Jr. (232166)
@author Ramona Miekaela S. Laciste (233403)
@version May 5, 2024
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

public class Camera {
    
    private double x,y;
    private Player p;
    
    /**
        Sets up a camera for the player
        @param x is the initial x position of the camera.
        @param y is the initial y position of the camera.
        @param p is the <code>Player</code> that will be observed.
    **/
    public Camera(double x, double y, Player p){
        this.x = x;
        this.y = y;
        this.p = p;
    }

    /** 
        Updates the position of the Camera based on the
        <code>Player</code>'s current location.
        <p></p> It will be shifted according 
        to the <code>Player</code>'s center.
    **/
    public void updatePosition(){
        x = (p.getX()+(p.getWidth()/2)) - (400);
        y = (p.getY()+(p.getHeight()/2)) - (300);
    }

    /**
        Gets the x-position of the camera.
        @return the x-position of the camera.
    **/
    public double getX(){
        return x;
    }

    /**
        Gets the x-position of the camera.
        @return the x-position of the camera.
    **/
    public double getY(){
        return y;
    }

}


