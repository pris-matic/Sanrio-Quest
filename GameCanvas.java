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
    private Enemy enemy;
    //private ArrayList<Enemy> enemies;
    private HealthBar p1HealthBorder,p1HealthBar,p2HealthBorder,p2HealthBar;

    private static ArrayList<Walls> gameBackground;
    private Thread gameThread;
    private final int FPS; // times the screen will be repainted each second
    private Camera camera;
    private LevelGenerator lg;

    /**
        Instantiates a GameCanvas object, with fixed dimensions of
        800 as its width, and 600 as its height.
        A <code> Camera </code> object is responsible to make other
        areas of the canvas visible
        @param p is the first player 
        @param p2 is the second player
        @see Camera
    **/
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
        
        lg = new LevelGenerator();

        for (Walls w : lg.getWalls()){
            gameBackground.add(w);
        }

        enemy = new Enemy("wingedcreature", 800, 600);

        p1HealthBorder = new HealthBar(10, 10, 200, 35,
            p.getCharacterType().getMaxHealth(), Color.BLACK, false,p);

        p1HealthBar = new HealthBar(10, 10, 200, 35, 
            p.getCharacterType().getMaxHealth(),Color.GREEN.darker(),true,p);

        p2HealthBorder = new HealthBar(590, 10, 200, 35,
            p2.getCharacterType().getMaxHealth(), Color.BLACK, false,p2);

        p2HealthBar = new HealthBar(590, 10, 200, 35, 
            p2.getCharacterType().getMaxHealth(),Color.GREEN.darker(),true,p2);
    }

    /**
        Paints the objects that is passed to the canvas.
        @param g is the Graphics object that will paint the custom drawings
    **/
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

        // design for the starting area for the players
        g2d.setColor(new Color(177, 224, 141));
        g2d.fillRect(-500, -400, 2100, 1450);

        g2d.setColor(new Color(255,212,146));
        g2d.fillRect(0, 0, 1200, 800);

        for (Walls w : gameBackground){
            w.draw(g2d);
        }

        lg.draw(g2d);

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

        enemy.drawCharacter(g2d);
        saveState = g2d.getTransform();
        enemy.getEnemyType().drawAttacks(g2d);
        
        g2d.setTransform(reset);

        // paints and displays the HealthBars above all components
        p1HealthBar.draw(g2d);
        p1HealthBorder.draw(g2d);
        p2HealthBar.draw(g2d);
        p2HealthBorder.draw(g2d);

        g2d.dispose();

    } 

    /**
        Checks whether the <Code>CharacterManager</code> object is currently
        colliding with a <Code> Walls </code> object.
        @param cm is the entity / character to be checked
        @return true if it is collding with any wall, false otherwise.
    **/
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

    /** 
        This method is called everytime the canvas is going to be repainted.
        It checks for collision between players and enemies
        @see Player#isCollidingWithBullet(Enemy)
        @see Enemy#isCollidingWithBullet(Player)
        @see #run()
    **/
    public void checkDamageCollision(){
        p.isCollidingWithBullet(enemy);
        enemy.isCollidingWithBullet(p);
        p.getCharacterType().weaponCollidingWithEnemy(enemy);
    }

    /**
        Moves the player according to its truth values
        of up, down, left, right directions.
        <p></p> the player gets pushed back whenever it collides onto something.
    **/
    public void checkMovement(){

        if (p.isMovingUp()){
            p.moveY(-(p.getSpeed()));
            if (p.isCollidingWithEntity(p, enemy)){
                p.moveY(p.getSpeed());
                p.getCharacterType().takeDamage(enemy.getEnemyType().getAttack());
            }
            if ((checkCollision(p))){
                p.moveY(p.getSpeed());
                
            }
        }
        if (p.isMovingDown()){
            p.moveY(p.getSpeed());
            if (p.isCollidingWithEntity(p, enemy)){
                p.moveY(-(p.getSpeed()));
                p.getCharacterType().takeDamage(enemy.getEnemyType().getAttack());
            }
            if ((checkCollision(p))){
                p.moveY(-(p.getSpeed()));
            }
        }
        if (p.isMovingLeft()){
            p.moveX(-(p.getSpeed()));
            if (p.isCollidingWithEntity(p, enemy)){
                p.moveX(p.getSpeed());
                p.getCharacterType().takeDamage(enemy.getEnemyType().getAttack());
            }
            if ((checkCollision(p))){
                p.moveX(p.getSpeed());
            }
        }
        if (p.isMovingRight()){
            p.moveX(p.getSpeed());
            if (p.isCollidingWithEntity(p, enemy)){
                p.moveX(-(p.getSpeed()));
                p.getCharacterType().takeDamage(enemy.getEnemyType().getAttack());
            }
            if ((checkCollision(p))){
                p.moveX(-(p.getSpeed()));
            }
        }

        p.getCharacterType().displayImage();
        
    }

    /**
        Moves the enemy according to its truth values
        of up, down, left, right directions. When it collides with a wall while
        moving in that direction, it bounces back, forcing it to move the opposite
        direction immediately.
        <p></p> the enemy gets pushed back whenever it collides onto something.
    **/
    public void checkEnemyMovement(Enemy enemy){

        enemy.moveAutomatically();

        if (enemy.isMovingUp()){
            enemy.moveY((-(enemy.getSpeed())));
            if (enemy.isCollidingWithEntity(enemy, p)){  
                enemy.moveY((enemy.getSpeed()));
                p.getCharacterType().takeDamage(enemy.getEnemyType().getAttack());
            }
            if (checkCollision(enemy)){
                enemy.moveY((enemy.getSpeed()));
                enemy.moveCharacter("up", false);
                enemy.moveCharacter("down", true);
            }
            
        }
        if (enemy.isMovingDown()){
            enemy.moveY((enemy.getSpeed()));
            if (enemy.isCollidingWithEntity(enemy, p)){
                enemy.moveY((-(enemy.getSpeed())));
                p.getCharacterType().takeDamage(enemy.getEnemyType().getAttack());
            }
            if (checkCollision(enemy)){
                enemy.moveY((-(enemy.getSpeed())));
                enemy.moveCharacter("up", true);
                enemy.moveCharacter("down", false);
            }
        }
        if (enemy.isMovingLeft()){
            enemy.moveX(-(enemy.getSpeed()));
            if (enemy.isCollidingWithEntity(enemy, p)){
                enemy.moveX(enemy.getSpeed());
                p.getCharacterType().takeDamage(enemy.getEnemyType().getAttack());
            }
            if (checkCollision(enemy)){
                enemy.moveX(enemy.getSpeed());
                enemy.moveCharacter("left", false);
                enemy.moveCharacter("right", true);
            }
        }
        if (enemy.isMovingRight()){
            enemy.moveX(enemy.getSpeed());
            if (enemy.isCollidingWithEntity(enemy, p)){
                enemy.moveX(-(enemy.getSpeed()));
                p.getCharacterType().takeDamage(enemy.getEnemyType().getAttack());
            }
            if (checkCollision(enemy)){
                enemy.moveX(-(enemy.getSpeed()));
                enemy.moveCharacter("left", true);
                enemy.moveCharacter("right", false);
            }
        }

        enemy.getEnemyType().displayImage();
    }

    /**
        Starts the GameLoop of the canvas, it repaints 60 times per second.
        @see #FPS 
        @see #run()
    **/
    public void startThread(){
        
        gameThread = new Thread(this);
        gameThread.start();

    }

    /**
        The gameloop of the canvas. Its task is to repaint it according to
        the FPS provided. Other methods are also called other than repaint()
    **/
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
                updateGameState();
                repaint();
                System.out.println(enemy.getEnemyType().getHealth());
                delta --;
            } 
        }
    }

    /** 
        Updates the camera Position
        @see Camera#updatePosition() 
    **/
    public void updateCamera(){
        camera.updatePosition();
    }

    /**
        Gets the camera being used. Its purpose is for the GameFrame to have
        access to the current positioning of the camera, then realign rotate values
        @return the <Code>Camera </code> object used by the player
        @see GameFrame.ConfigureWeapon
    **/
    public Camera getCamera(){
        return camera;
    }

    /**
        Gets the walls that are used throughout the game.
        Only one copy of walls are required. 
        @return the arraylist that stores the <code> Walls </code> objects.
    **/
    public static ArrayList<Walls> getWalls(){
        return gameBackground;
    }

    /**  
        Repaints the <code> HealthBar</code> objects based on the health of each of the players.
        @see GameCanvas.HealthBar
    **/
    public void refreshHealth(){
        p1HealthBar.updateHealth((int) p.getCharacterType().getHealth());
        p2HealthBar.updateHealth((int) p2.getCharacterType().getHealth());
    }

    /**
        Updates everything that needs to be repainted inside the canvas
        @see #run()
    **/
    private void updateGameState(){

        // decided to put everything here rather than calling each one inside the run() method
        checkMovement();
        checkEnemyMovement(enemy);
        updateCamera();
        refreshHealth();
        checkDamageCollision();
        p.reduceTimer();
        enemy.reduceTimer();
    }

    /**
        The HealthBar inner class is a custom made bar that is 
        responsible for drawing the character health. <p></p> 
        It has two parts: one that draws the border, along with the text how much health
        the player has.
        <p></p> The other part will show how much health (as a bar) the player has

        @author Anthony B. Deocadiz Jr. (232166)
        @author Ramona Miekaela S. Laciste (233403)
        @version March 16, 2024
    **/
    class HealthBar {
        private int x, y, width, height;
        private double currentHealth,maxHealth;
        private Color color;
        private Player player;
        private boolean fill;

        /**
            Constructs an instance of the HealthBar.
            @param x is the x-position of the health bar.
            @param y is the y-position of the health bar.
            @param w is the width of the health bar.
            @param h is the height of the health bar.
            @param hp is the maximum health a player has.
            @param c is the color of the bar.
            @param f determines which of the two parts will be drawn, either the border of it, or the health illustration.
            @param player is the player to be checked whether their health will change.
        **/
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

        /**
            Draws the healthbar onto the Canvas. A customized font is also passed along with it.
            @param g2d is the Graphics2D object responsible for drawing.
        **/
        public void draw(Graphics2D g2d){
            
            g2d.setColor(color);
            if (fill){ // the health bar illustration
                double percentage = (currentHealth/maxHealth) * 200;
                Rectangle2D.Double health = new Rectangle2D.Double(this.x,this.y,(int) percentage,this.height);
                g2d.fill(health);
            } else { // the border of the health bar
                Rectangle2D.Double hollow = new Rectangle2D.Double(this.x,this.y,this.width,this.height);
                g2d.draw(hollow);

                g2d.setColor(Color.BLACK);

                Font healthFont = new Font("Comic Sans MS", Font.BOLD,12);
                g2d.setFont(healthFont);
                String text = player.getName()+ "'s Health: " + String.format("%.2f", player.getCharacterType().getHealth())
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

        /**
            Updates the health of the player.
            @param value is the current health of the player.
        **/
        public void updateHealth(int value){
            currentHealth = value;
        }
        
    }

}
