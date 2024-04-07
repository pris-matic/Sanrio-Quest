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
        this.setPreferredSize(new Dimension(800,600));

        gameBackground = new ArrayList<>();

        Walls a = new Walls(0, 0, 800, 50);
        Walls b = new Walls(0, 0, 50, 600);
        Walls c = new Walls(750, 0, 50, 600);
        Walls d = new Walls(0, 550,800, 50);

        gameBackground.add(a);
        gameBackground.add(b);
        gameBackground.add(c);
        gameBackground.add(d);

    }

    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        RenderingHints rh = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setRenderingHints(rh);

        AffineTransform reset = g2d.getTransform();

        for (Walls w : gameBackground){
            w.draw(g2d);
        }

        // draws the character and its weapon
        p.drawCharacter(g2d);
        p.getCharacterType().drawWeapon(g2d);
    
        // resets its rotation and then draws the attack / projectiles
        g2d.setTransform(reset);
        p.getCharacterType().drawAttacks(g2d);
        
        // draws the second character and its weapon
        p2.drawCharacter(g2d);
        p2.getCharacterType().drawWeapon(g2d);

        // resets its rotation and then draws the attack / projectiles
        g2d.setTransform(reset);
        p2.getCharacterType().drawAttacks(g2d);

        g2d.dispose();

    } 

    class PlayerTimer implements ActionListener{
        
        public PlayerTimer(){

        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (p.isMovingUp() ){
                p.moveY(-(p.getSpeed()));
                if ((checkCollision(p))){
                    p.moveY(p.getSpeed());
                }
            }
            if (p.isMovingDown()){
                p.moveY(p.getSpeed());
                if ((checkCollision(p))){
                    p.moveY(-(p.getSpeed()));
                }
            }
            if (p.isMovingLeft()){
                p.moveX(-(p.getSpeed()));
                if ((checkCollision(p))){
                    p.moveX(p.getSpeed());
                }
            }
            if (p.isMovingRight()){
                p.moveX(p.getSpeed());
                if ((checkCollision(p))){
                    p.moveX(-(p.getSpeed()));
                }
            }

            repaint();
        }

    }

    public boolean checkCollision(CharacterManager cm){
        
        boolean collision = false;
        
        for (Walls w : gameBackground){
            if (cm.isCollidingWithWall(w)){
                collision = true;
                break;
            }
        }
        return collision;
    }

}
