import javax.swing.*;
import java.awt.*;
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

public class GameCanvas extends JComponent implements Runnable {
    
    private Player p;
    private Player p2;
    private ArrayList<Walls> gameBackground;
    private Thread gameThread;
    private final int FPS;
    private Camera camera;

    public GameCanvas(Player p, Player p2){
       
        this.p = p;
        this.p2 = p2;
        this.setPreferredSize(new Dimension(800,600));
        FPS = 60;

        gameBackground = new ArrayList<>();
        camera = new Camera((p.getX()+(p.getWidth()/2)),(p.getY()+(p.getHeight()/2)),p);

        Walls a = new Walls(0, 0, 1200, 50);
        Walls b = new Walls(0, 0, 50, 800);
        Walls c = new Walls(1150, 0, 50, 800);
        Walls d = new Walls(0, 750,1200, 50);

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

        rh.put(RenderingHints.KEY_INTERPOLATION, 
            RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        g2d.setRenderingHints(rh);

        AffineTransform reset = g2d.getTransform();
        AffineTransform saveState = g2d.getTransform();

        g2d.translate(-(camera.getX()), -(camera.getY()));

        for (Walls w : gameBackground){
            w.draw(g2d);
        }

        
        // draws the character and its weapon
        p.drawCharacter(g2d);
        saveState = g2d.getTransform();
        p.getCharacterType().drawWeapon(g2d);
    
        // resets its rotation and then draws the attack / projectiles
        g2d.setTransform(saveState);
        p.getCharacterType().drawAttacks(g2d);
        
        // draws the second character and its weapon
        p2.drawCharacter(g2d);
        saveState = g2d.getTransform();
        p2.getCharacterType().drawWeapon(g2d);

        // resets its rotation and then draws the attack / projectiles
        g2d.setTransform(saveState);
        p2.getCharacterType().drawAttacks(g2d);

        g2d.setTransform(reset);
        g2d.dispose();

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

    public void checkMovement(){

        if (p.isMovingUp()){
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
        
    }

    public void startThread(){
        
        gameThread = new Thread(this);
        gameThread.start();

    }

    @Override
    public void run(){
        
        double drawTime = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime(); 
        long currentTime;

        while (gameThread != null){

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawTime;

            lastTime = currentTime;

            if (delta >= 1){
                checkMovement();
                updateCamera();
                repaint();
                delta --;
            } 
        }
    }

    public void updateCamera(){
        camera.updatePosition();
    }

    public Camera getCamera(){
        return camera;
    }

}
