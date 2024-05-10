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
    private Enemy e;
    //private ArrayList<Enemy> e;
    private HealthBar p1HealthBorder,p1HealthBar,p2HealthBorder,p2HealthBar;;

    private static ArrayList<Walls> gameBackground;
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
        setDoubleBuffered(true);

        Walls a = new Walls(0, 0, 1200, 50);
        Walls b = new Walls(0, 0, 50, 800);
        Walls c = new Walls(1150, 0, 50, 800);
        Walls d = new Walls(0, 750,1200, 50);

        gameBackground.add(a);
        gameBackground.add(b);
        gameBackground.add(c);
        gameBackground.add(d);

        e = new Enemy("Fairy", 800, 600);

        p1HealthBorder = new HealthBar(10, 10, 200, 35,
            p.getCharacterType().getMaxHealth(), Color.BLACK, false,p);

        p1HealthBar = new HealthBar(10, 10, 200, 35, 
            p.getCharacterType().getMaxHealth(),Color.GREEN.darker(),true,p);

        p2HealthBorder = new HealthBar(590, 10, 200, 35,
            p2.getCharacterType().getMaxHealth(), Color.BLACK, false,p2);

        p2HealthBar = new HealthBar(590, 10, 200, 35, 
            p2.getCharacterType().getMaxHealth(),Color.GREEN.darker(),true,p2);
    }

    @Override
    protected void paintComponent(Graphics g){

        Graphics2D g2d = (Graphics2D) g;
        
        RenderingHints rh = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_INTERPOLATION, 
            RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        rh.put(RenderingHints.KEY_ALPHA_INTERPOLATION, 
            RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);

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

        e.drawCharacter(g2d);
        saveState = g2d.getTransform();
        e.getCharacterType().drawAttacks(g2d);
        
        g2d.setTransform(reset);

        p1HealthBar.draw(g2d);
        p1HealthBorder.draw(g2d);
        p2HealthBar.draw(g2d);
        p2HealthBorder.draw(g2d);

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

    public void checkCollisionWithProjectiles(){
        p.wasHit(p, e);
        e.wasHit(e, p);
    }

    public void checkMovement(){

        if (p.isMovingUp()){
            p.moveY(-(p.getSpeed()));
            if (p.isCollidingWithEntity(p, e)){
                p.moveY(p.getSpeed());
                p.getCharacterType().takeDamage(e.getCharacterType().getAttack());
            }
            if ((checkCollision(p))){
                p.moveY(p.getSpeed());
                
            }
        }
        if (p.isMovingDown()){
            p.moveY(p.getSpeed());
            if (p.isCollidingWithEntity(p, e)){
                p.moveY(-(p.getSpeed()));
                p.getCharacterType().takeDamage(e.getCharacterType().getAttack());
            }
            if ((checkCollision(p))){
                p.moveY(-(p.getSpeed()));
            }
        }
        if (p.isMovingLeft()){
            p.moveX(-(p.getSpeed()));
            if (p.isCollidingWithEntity(p, e)){
                p.moveX(p.getSpeed());
                p.getCharacterType().takeDamage(e.getCharacterType().getAttack());
            }
            if ((checkCollision(p))){
                p.moveX(p.getSpeed());
            }
        }
        if (p.isMovingRight()){
            p.moveX(p.getSpeed());
            if (p.isCollidingWithEntity(p, e)){
                p.moveX(-(p.getSpeed()));
                p.getCharacterType().takeDamage(e.getCharacterType().getAttack());
            }
            if ((checkCollision(p))){
                p.moveX(-(p.getSpeed()));
            }
        }

        p.getCharacterType().displayImage();
        
    }

    public void checkEnemyMovement(){

        e.moveAutomatically();

        if (e.isMovingUp()){
            e.moveY((-(e.getSpeed())));
            if (e.isCollidingWithEntity(e, p)){
                e.moveY((e.getSpeed()));
                p.getCharacterType().takeDamage(e.getCharacterType().getAttack());
            }
            if (checkCollision(e)){
                e.moveY((e.getSpeed()));
            }
            
        }
        if (e.isMovingDown()){
            e.moveY((e.getSpeed()));
            if (e.isCollidingWithEntity(e, p)){
                e.moveY((-(e.getSpeed())));
                p.getCharacterType().takeDamage(e.getCharacterType().getAttack());
            }
            if (checkCollision(e)){
                e.moveY((-(e.getSpeed())));
            }
        }
        if (e.isMovingLeft()){
            e.moveX(-(e.getSpeed()));
            if (e.isCollidingWithEntity(e, p)){
                e.moveX(e.getSpeed());
                p.getCharacterType().takeDamage(e.getCharacterType().getAttack());
            }
            if (checkCollision(e)){
                e.moveX(e.getSpeed());
            }
        }
        if (e.isMovingRight()){
            e.moveX(e.getSpeed());
            if (e.isCollidingWithEntity(e, p)){
                e.moveX(-(e.getSpeed()));
                p.getCharacterType().takeDamage(e.getCharacterType().getAttack());
            }
            if (checkCollision(e)){
                e.moveX(-(e.getSpeed()));
            }
        }

        e.getCharacterType().displayImage();
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
                checkEnemyMovement();
                updateCamera();
                refreshHealth();
                checkCollisionWithProjectiles();
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

    public static ArrayList<Walls> getWalls(){
        return gameBackground;
    }

    public void refreshHealth(){
        p1HealthBar.updateHealth((int) p.getCharacterType().getHealth());
        p2HealthBar.updateHealth((int) p2.getCharacterType().getHealth());
    }

    class HealthBar {
        private int x, y, width, height;
        private double currentHealth,maxHealth;
        private Color color;
        private Player player;
        private boolean fill;

        public HealthBar(int x, int y, int w, int h, double hp, Color c,boolean f, Player player){
            this.x = x;
            this.y = y;
            width = w;
            height = h;
            currentHealth = hp;
            maxHealth = hp;
            color = c;
            fill = f;
            this.player = player;
        }   

        public void draw(Graphics2D g2d){
            
            g2d.setColor(color);
            if (fill){
                double percentage = (currentHealth/maxHealth) * 200;
                Rectangle2D.Double health = new Rectangle2D.Double(this.x,this.y,(int) percentage,this.height);
                g2d.fill(health);
            } else {
                Rectangle2D.Double hollow = new Rectangle2D.Double(this.x,this.y,this.width,this.height);
                g2d.draw(hollow);

                g2d.setColor(Color.BLACK);

                Font healthFont = new Font("Comic Sans MS", Font.BOLD,13);
                g2d.setFont(healthFont);
                String text = player.getName()+ "'s Health: " + player.getCharacterType().getHealth() 
                + "/" + player.getCharacterType().getMaxHealth();
            
                // center-align the text based on how long it is
                FontMetrics metrics = g2d.getFontMetrics(healthFont);
                int textWidth = metrics.stringWidth(text);
                int textHeight = metrics.getHeight();

                int centerX = this.x + (int) ((this.width - textWidth) / 2.0);
                int centerY = this.y + (this.height - textHeight) / 2 + metrics.getAscent();

                g2d.drawString(text, centerX, centerY);
            }

        }

        public void updateHealth(int value){
            currentHealth = value;
        }
        
    }

}
