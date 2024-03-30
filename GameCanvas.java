import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;


/**
The GameCanvas class is responsible for drawing the
character sprites necessary for the game. Custom Drawings
are passed on to the GameCanvas class, and will draw
each of them.

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

public class GameCanvas extends JComponent {
    
    private Player p;
    private Player p2;
    private Timer t;
    private ArrayList<Walls> gameBackground;

    public GameCanvas(Player p, Player p2){
       
        this.p = p;
        this.p2 = p2;
        t = new Timer(15, new PlayerTimer());
        t.start();

    }

    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        RenderingHints rh = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setRenderingHints(rh);

        AffineTransform reset = g2d.getTransform();

        p.drawCharacter(g2d);
        p.getCharaterType().drawWeapon(g2d);

        g2d.setTransform(reset);
        
        p2.drawCharacter(g2d);
        p2.getCharaterType().drawWeapon(g2d);
        
        g2d.dispose();

    } 

    public Player getPlayer(){
        return p;
    }

    class PlayerTimer implements ActionListener{

        private int distance;

        public PlayerTimer(){
            distance = 5;
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (p.isMovingUp()){
                p.moveY(-distance);
            }
            if (p.isMovingDown()){
                p.moveY(distance);
            }
            if (p.isMovingLeft()){
                p.moveX(-distance);
            }
            if (p.isMovingRight()){
                p.moveX(distance);
            }

            repaint();
        }

    }

}
