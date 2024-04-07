import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import javax.swing.*;

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
    
    private ArrayList<Projectiles> bulletList;
    private Timer projectileMovement;

    public Ranger(CharacterManager cm){
        
        hp = 75;
        atk = 10;
        def = 3;

        alive = true;

        this.cm = cm;
        bulletList = new ArrayList<>();

    }

    @Override
    public String showCharacterType(){
        return "Ranger";
    }

    @Override
    public void drawWeapon(Graphics2D g2d) {
        
        g2d.setColor(Color.GREEN);

        Rectangle2D.Double rangerWep = new Rectangle2D.Double((cm.getX()+cm.getWidth()/2),cm.getY()+15,35,110);
        g2d.rotate(rotation,(cm.getX()+cm.getWidth()/2),cm.getY()+(cm.getHeight()/2)+10);
        g2d.fill(rangerWep);

    }

    @Override
    public void drawAttacks(Graphics2D g2d){
        for (Projectiles b : bulletList){
            b.drawProjectile(g2d);
        }
    }

    @Override
    public void changeRotation(double yPos, double xPos){

        double dy = yPos - ((cm.getY()+(cm.getHeight()/2))+25);
        double dx = xPos - (cm.getX()+(cm.getWidth()/2));
        rotation = Math.atan2(dy,dx);

    }

    @Override
    public void attack(){

        if (bulletList.size() < 3){
            bulletList.add(new Bullet((cm.getX()+(cm.getWidth()/2)) - 15, (cm.getY()+(cm.getHeight()/2)) - 20, rotation));
            attackMovement();
        }
        
    }

    @Override
    public void attackMovement(){

        if (projectileMovement == null || !projectileMovement.isRunning()){
            ActionListener moveBullets = new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    
                    for (int i = 0; i < bulletList.size() ; i++){
                        bulletList.get(i).moveProjectileX();
                        bulletList.get(i).moveProjectileY();
                    }
    
                    removeProjectiles(bulletList);
    
                }
            };
            projectileMovement = new Timer(15, moveBullets);
            projectileMovement.start();
        }
    
    }

    @Override
    public ArrayList<Projectiles> getProjectiles(){
        return bulletList;
    }

    class Bullet extends Projectiles{

        public Bullet(double x, double y, double rotation){
            xPos = x;
            yPos = y;
            
            initX = x;
            initY = y;

            projectileSpeed = 5;
            projectileRotation = rotation;

            width = 20;
            height = 20;
        }

        @Override
        public void drawProjectile(Graphics2D g2d){
            g2d.setColor(Color.BLUE);
            Ellipse2D.Double bullet = new Ellipse2D.Double(xPos,yPos,this.width,this.height);
            g2d.fill(bullet);
        }
    }

}
