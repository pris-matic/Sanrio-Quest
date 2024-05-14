import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.imageio.ImageIO;
import javax.swing.Timer;

/**
The Ranger class is a subclass of the <code> CharacterType </code>
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

public class Ranger extends CharacterType {
    
    private CopyOnWriteArrayList<Projectiles> bulletList;
    private Timer bulletMovement;

     /**
     * Constructor for the Ranger class.
     * @param cm The CharacterManager object.
     */
    public Ranger(CharacterManager cm){
        
        maxHp = 75;
        hp = 75;
        atk = 10;
        def = 3;

        alive = true;

        this.cm = cm;
        bulletList = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 5; i ++){
            bulletList.add(new Bullet(-5000, -5000, 0));
        }
        attacking = false;
        getImages();

        img = idle;

    }
    
    /**
     * Returns the character type.
     * @return A string representing the character type ("Ranger").
     */
    @Override
    public String showCharacterType(){
        return "Ranger";
    }

    /**
     * Loads images for the Ranger character.
     */
    @Override
    public void getImages(){

        try {

            idle = ImageIO.read(getClass().getResourceAsStream("/Sprites/RangerSprites/idle_ranger.png"));
            front1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/RangerSprites/front1_ranger.png"));
            front2 = ImageIO.read(getClass().getResourceAsStream("/Sprites/RangerSprites/front2_ranger.png"));
            back1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/RangerSprites/back1_ranger.png"));
            back2 = ImageIO.read(getClass().getResourceAsStream("/Sprites/RangerSprites/back2_ranger.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/RangerSprites/left1_ranger.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/Sprites/RangerSprites/left2_ranger.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/RangerSprites/right1_ranger.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/Sprites/RangerSprites/right2_ranger.png"));
            weaponImg = ImageIO.read(getClass().getResourceAsStream("/Sprites/Weapons/slingshot.png"));

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
     * Draws the character's weapon.
     * @param g2d The graphics context.
     */
    @Override
    public void drawWeapon(Graphics2D g2d) {

        g2d.drawImage(weaponImg, (int) (cm.getX()+cm.getWidth()*11/20), (int) (cm.getY()+(cm.getHeight()/2)), 30, 40, null);

    }

    /**
     * Draws the character's attacks.
     * @param g2d The graphics context.
     */
    @Override
    public void drawAttacks(Graphics2D g2d){
        for (Projectiles b : bulletList){
            b.drawProjectile(g2d);
        }
    }

    /**
     * Changes the rotation of the character.
     * @param yPos The y-coordinate of the target position.
     * @param xPos The x-coordinate of the target position.
     */
    @Override
    public void changeRotation(double yPos, double xPos){

        double dy = yPos - (cm.getY()+(cm.getHeight()/2)+20);
        double dx = xPos - ((cm.getX()+cm.getWidth()*11/20)+15);
        rotation = Math.atan2(dy,dx);

    }

     /**
     * Initiates an attack action.
     */
    @Override
    public void attack(){

        if (alive){
            attacking = true;
        
            for (Projectiles b : bulletList){
                
                if (!b.isActive()){
                    
                    b.setInitialX((cm.getX()+(cm.getWidth()/2))+7.5);
                    b.setInitialY((cm.getY()+(cm.getHeight()/2)));

                    b.setProjectileX((cm.getX()+(cm.getWidth()/2))+7.5);
                    b.setProjectileY((cm.getY()+(cm.getHeight()/2)));
                    b.setProjectileRotation(rotation);
                    b.setActive();
                    
                    break;
                } 

            }
        }
        attackMovement();
        
    }

    /**
     * Initiates the movement of the character's attack.
     */
    @Override
    public void attackMovement(){
        if (bulletMovement == null || !bulletMovement.isRunning()){
            ActionListener moveBullets = new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                        
                    for (int i = 0; i < bulletList.size() ; i++){
                        if (bulletList.get(i).isActive()){
                            bulletList.get(i).moveProjectileX();
                            bulletList.get(i).moveProjectileY();
                        }
                    }

                    removeProjectiles(bulletList);

                    attacking = false;
                }
            };
            bulletMovement = new Timer(15, moveBullets);
            bulletMovement.start();
        }
    
    }

    /**
     * Retrieves the list of projectiles.
     * @return A CopyOnWriteArrayList containing projectiles.
     */
    @Override
    public CopyOnWriteArrayList<Projectiles> getProjectiles(){
        return bulletList;
    }

    /**
    The Bullet inner class is a subclass of the <code> Projectiles </code>
    abstract inner class. It sets up the projectiles for this class
    such as its speed.

    @author Anthony B. Deocadiz Jr. (232166)
    @author Ramona Miekaela S. Laciste (233403)
    @version April 02, 2024
    **/
    class Bullet extends Projectiles{

        public Bullet(double x, double y, double rotation){
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
         * Draws the bullet.
         * @param g2d The graphics context.
         */
        @Override
        public void drawProjectile(Graphics2D g2d){
            g2d.setColor(Color.DARK_GRAY);
            Ellipse2D.Double bullet = new Ellipse2D.Double(xPos,yPos,this.width,this.height);
            g2d.fill(bullet);
        }
    }

}
