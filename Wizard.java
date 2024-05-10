import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import java.awt.event.*;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.imageio.ImageIO;
import javax.swing.Timer;

/**
The Melee class is a subclass of the <code> CharacterType </code>
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
    }

    @Override
    public String showCharacterType() {
        return "Wizard";
    }

    @Override
    public void getImages(){

        try {

            idle = ImageIO.read(getClass().getResourceAsStream("/CharacterSprites/RangerSprites/idle_ranger.png"));
            front1 = ImageIO.read(getClass().getResourceAsStream("/CharacterSprites/RangerSprites/front1_ranger.png"));
            front2 = ImageIO.read(getClass().getResourceAsStream("/CharacterSprites/RangerSprites/front2_ranger.png"));
            back1 = ImageIO.read(getClass().getResourceAsStream("/CharacterSprites/RangerSprites/back1_ranger.png"));
            back2 = ImageIO.read(getClass().getResourceAsStream("/CharacterSprites/RangerSprites/back2_ranger.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/CharacterSprites/RangerSprites/left1_ranger.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/CharacterSprites/RangerSprites/left2_ranger.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/CharacterSprites/RangerSprites/right1_ranger.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/CharacterSprites/RangerSprites/right2_ranger.png"));

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

    @Override
    public void drawWeapon(Graphics2D g2d) {
        
        g2d.setColor(Color.GREEN);
        Rectangle2D.Double wizardWep = new Rectangle2D.Double(cm.getX()+(cm.getWidth()*0.65),cm.getY()+30,15,50);
        g2d.fill(wizardWep);
    }

    @Override
    public void drawAttacks(Graphics2D g2d){
        for (Projectiles o : orbList){
            o.drawProjectile(g2d);
        }
    }

    @Override
    public void changeRotation(double yPos, double xPos){
        
        double dy = yPos - (cm.getY()+75);
        double dx = xPos - (cm.getX()+(cm.getWidth()*0.65)+7.5);
        rotation = Math.atan2(dy,dx);

    }

    @Override
    public void attack(){
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
        attackMovement();
    }

    @Override
    public CopyOnWriteArrayList<Projectiles> getProjectiles(){
        return orbList;
    }

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

    class Orb extends Projectiles{
        
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
        
        @Override
        public void drawProjectile(Graphics2D g2d){
            g2d.setColor(Color.RED);
            Ellipse2D.Double orb = new Ellipse2D.Double(xPos,yPos,this.width,this.height);
            g2d.fill(orb);
        }
    }
 
}
