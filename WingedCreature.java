import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.imageio.ImageIO;
import javax.swing.Timer;

/**
The Winged Creature Class 

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

public class WingedCreature extends EnemyType{
    
    private CopyOnWriteArrayList<EnemyProjectiles> starsList;
    private int shooterCooldown;
    private Timer autoShooter,starMovement;

    /**
     * Constructor for the WingedCreature class.
     * @param cm The CharacterManager object associated with the game.
     */
    public WingedCreature(CharacterManager cm){

        this.cm = cm;

        maxHp = 50;
        hp = 50;
        atk = 10;
        def = 1;

        alive = true;
       
        width = 55;
        height = 55;
        
        starsList = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 3; i ++){
            starsList.add(new Stars(-5000, -5000, 0));
        }

        getImages();

        img = front1;

        shooterCooldown = 1500;

    }

    /**
     * Returns the type of the enemy.
     * @return A string representing the enemy type ("Winged Creature").
     */
    @Override
    public String showEnemyType() {
        return "Winged Creature";
    }

    /**
     * Loads images for the winged creature enemy from the resource directory.
     */
    @Override
    public void getImages() {
        try {
            front1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/EnemySprites/WingedCreature/front1_winged.png"));
            front2 = ImageIO.read(getClass().getResourceAsStream("/Sprites/EnemySprites/WingedCreature/front2_winged.png"));

            imageList[0] = front1;
            imageList[1] = front2;
            
        } catch (IOException e) {
            System.out.println("IOException in EnemyType.getImages()");
        }
    }

    /**
     * Draws the attack animations of the winged creature enemy.
     * @param g2d The Graphics2D object used for drawing.
     */
    @Override
    public void drawAttacks(Graphics2D g2d) {
        for (EnemyProjectiles ep : starsList){
            ep.drawProjectile(g2d);
        }
        attack();
    }

    /**
     * Initiates an attack action for the winged creature enemy.
     */
    @Override
    public void attack() {

        if (alive){
            if (autoShooter == null || !autoShooter.isRunning()){
                
                ActionListener autoShooting = new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        for (EnemyProjectiles ep : starsList){
                            if (!ep.isActive()){
                                
                                ep.setInitialX((cm.getX()+(cm.getWidth()/2))-7.5);
                                ep.setInitialY((cm.getY()+(cm.getHeight()/2))-7.5);
                
                                ep.setProjectileX((cm.getX()+(cm.getWidth()/2))-7.5);
                                ep.setProjectileY((cm.getY()+(cm.getHeight()/2))-7.5);
                                ep.setProjectileRotation(rotation);
                                ep.setActive();
                                break;
                            } 
                        }
                    }
                };
                autoShooter = new Timer(shooterCooldown, autoShooting);
                autoShooter.start();
                attackMovement();
            }
        } else {
            if (autoShooter == null || autoShooter.isRunning()){
                autoShooter.stop();
            }
        }
    }

    /**
     * Handles the movement during an attack action for the winged creature enemy.
     */
    @Override
    public void attackMovement() {
        if (starMovement == null || !starMovement.isRunning()){
            ActionListener moveStars = new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                        
                    for (int i = 0; i < starsList.size() ; i++){
                        if (starsList.get(i).isActive()){
                            starsList.get(i).moveProjectileX();
                            starsList.get(i).moveProjectileY();
                        }
                    }

                    removeProjectiles(starsList);
                }
            };
            starMovement = new Timer(15, moveStars);
            starMovement.start();
        }
    }

    /**
     * Gets the projectiles associated with the winged creature enemy.
     * @return A list of EnemyProjectiles associated with the winged creature enemy.
     */
    @Override
    public CopyOnWriteArrayList<EnemyProjectiles> getProjectiles() {
        return starsList;
    }

    /**
     * The Stars class represents the projectile fired by the winged creature enemy.
     * Extends the EnemyProjectiles class.
     */
    class Stars extends EnemyProjectiles {

        /**
         * Constructor for the Stars class.
         * @param x The initial x-coordinate of the projectile.
         * @param y The initial y-coordinate of the projectile.
         * @param rotation The rotation angle of the projectile.
         */
        public Stars(double x, double y, double rotation){
            xPos = x;
            yPos = y;
            
            initX = x;
            initY = y;

            projectileSpeed = 3;
            projectileRotation = rotation;

            width = 15;
            height = 15;
            
            active = false;
        }

        /**
         * Draws the projectile on the game screen.
         * @param g2d The Graphics2D object used for drawing.
         */
        @Override
        public void drawProjectile(Graphics2D g2d) {
            g2d.setColor(new Color(197, 158, 1));
            Ellipse2D.Double star = new Ellipse2D.Double(xPos,yPos,this.width,this.height);
            g2d.fill(star);
        }

    }

}
