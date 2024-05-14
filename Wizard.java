import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import java.awt.event.*;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.imageio.ImageIO;
import javax.swing.Timer;

/**
The Wizard class is a subclass of the <code> CharacterType </code>
abstract class. It is the type of character the player uses
inside the game.

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

public class Wizard extends CharacterType{

    private CopyOnWriteArrayList<Projectiles> orbList;
    private Timer orbMovement;

     /**
     * Constructor for the Wizard class.
     * @param cm The CharacterManager object associated with the game.
     */
    public Wizard(CharacterManager cm){
        
        maxHp = 125;
        hp = 125;
        atk = 7;
        def = 5;

        alive = true;

        this.cm = cm;
        orbList = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 3; i ++){
            orbList.add(new Orb(-5000, -5000, 0));
        }
        attacking = false;
        getImages();

        img = idle;

        alive = true;
    }

    /**
     * Returns the type of the character.
     * @return A string representing the character type ("Wizard").
     */
    @Override
    public String showCharacterType() {
        return "Wizard";
    }

    /**
     * Loads images for the wizard character from the resource directory.
     */
    @Override
    public void getImages(){

        try {

            idle = ImageIO.read(getClass().getResourceAsStream("/Sprites/WizardSprites/idle_wizard.png"));
            front1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/WizardSprites/front1_wizard.png"));
            front2 = ImageIO.read(getClass().getResourceAsStream("/Sprites/WizardSprites/front2_wizard.png"));
            back1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/WizardSprites/back1_wizard.png"));
            back2 = ImageIO.read(getClass().getResourceAsStream("/Sprites/WizardSprites/back2_wizard.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/WizardSprites/left1_wizard.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/Sprites/WizardSprites/left2_wizard.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/WizardSprites/right1_wizard.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/Sprites/WizardSprites/right2_wizard.png"));
            weaponImg = ImageIO.read(getClass().getResourceAsStream("/Sprites/Weapons/staff.png"));

            imageList[0] = idle;
            imageList[1] = front1;
            imageList[2] = front2;
            imageList[3] = left1;
            imageList[4] = left2;
            imageList[5] = right1;
            imageList[6] = right2;
            imageList[7] = back1;
            imageList[8] = back2;

        } catch (IOException e) {
            System.out.println("IOException in CharacterType.getImages()");
        }
    }

    /**
     * Draws the wizard's weapon on the game screen.
     * @param g2d The Graphics2D object used for drawing.
     */
    @Override
    public void drawWeapon(Graphics2D g2d) {
        
        g2d.drawImage(weaponImg, (int) (cm.getX()+(cm.getWidth()*0.65)), (int) (cm.getY()+30), 15, 50, null);
    }

    /**
     * Draws the attacks of the wizard character on the game screen.
     * @param g2d The Graphics2D object used for drawing.
     */
    @Override
    public void drawAttacks(Graphics2D g2d){
        for (Projectiles o : orbList){
            o.drawProjectile(g2d);
        }
    }

    /**
     * Changes the rotation angle of the wizard's projectile.
     * @param yPos The y-coordinate of the target position.
     * @param xPos The x-coordinate of the target position.
     */
    @Override
    public void changeRotation(double yPos, double xPos){
        
        double dy = yPos - (cm.getY()+75);
        double dx = xPos - (cm.getX()+(cm.getWidth()*0.65)+7.5);
        rotation = Math.atan2(dy,dx);

    }

    /**
     * Initiates an attack action for the wizard character.
     */
    @Override
    public void attack(){
        
        if (alive){
            attacking = true;
            if (orbList.size() < 3){
                orbList.add(new Orb((cm.getX()+(cm.getWidth()*0.7)+10), cm.getY()+30, rotation));
            }

            for (Projectiles o : orbList){
                if (!o.isActive()){

                    o.setInitialX((cm.getX()+(cm.getWidth()*0.65)+10));
                    o.setInitialY(cm.getY()+35);

                    o.setProjectileX((cm.getX()+(cm.getWidth()*0.65)+10));
                    o.setProjectileY(cm.getY()+35);
                    o.setProjectileRotation(rotation);
                    o.setActive();
                    
                    break;
                }
            }
        }
        attackMovement();
    }

    /**
     * Gets the projectiles associated with the wizard character.
     * @return A list of Projectiles associated with the wizard character.
     */
    @Override
    public CopyOnWriteArrayList<Projectiles> getProjectiles(){
        return orbList;
    }

    /**
     * Handles the movement of the wizard's projectile during an attack action.
     */
    @Override
    public void attackMovement(){
        
        if (orbMovement == null || !orbMovement.isRunning()){
            ActionListener moveBullets = new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    
                    for (int i = 0; i < orbList.size() ; i++){
                        orbList.get(i).moveProjectileX();
                        orbList.get(i).moveProjectileY();
                    }
                    
                    removeProjectiles(orbList);
                    
                    attacking = false;
                    
                }
            };
            orbMovement = new Timer(15, moveBullets);
            orbMovement.start();
        }
          
    }

    /**
     * The Orb Class represents the Orb projectile fired by the wizard character.
     * Extends the Projectiles class.
     */
    class Orb extends Projectiles{

        /**
         * Constructor for the Orb class.
         * @param x The initial x-coordinate of the projectile.
         * @param y The initial y-coordinate of the projectile.
         * @param rotation The rotation angle of the projectile.
         */
        public Orb(double x, double y, double rotation){

            xPos = x;
            yPos = y;
            
            initX = x;
            initY = y;

            projectileSpeed = 5;
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
        public void drawProjectile(Graphics2D g2d){
            g2d.setColor(Color.RED);
            Ellipse2D.Double orb = new Ellipse2D.Double(xPos,yPos,this.width,this.height);
            g2d.fill(orb);
        }
    }

    /**
        Checks whether the player is currently attacking with their weapon.
        @param enemy is the enemy being checked if the player's weapon is colliding with them
    **/
    @Override
    public void weaponCollidingWithEnemy(Enemy enemy) {
        
        double initialX = (cm.getX()+(cm.getWidth()*0.65));
        double width = 15;

        double initialY = (cm.getY()+30);
        double height = 50;

        if (attacking){

            boolean colliding = false;

            colliding = !(initialX + width <= enemy.getX()
            || initialX >= enemy.getX() + enemy.getWidth()
            || initialY + height <= enemy.getY()
            || initialY >= enemy.getY() + enemy.getHeight());

            if (colliding){
                enemy.getEnemyType().takeDamage(atk);
            }

        }
    }
 
}
