import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.Timer;

public class Fairy extends CharacterType{
    
    private CopyOnWriteArrayList<Projectiles> starsList;
    private int shooterCooldown;
    private Timer autoShooter,starMovement;

    public Fairy(CharacterManager cm){

        this.cm = cm;

        maxHp = 100;
        hp = 100;
        atk = 6;
        def = 6;

        alive = true;
       
        width = 55;
        height = 55;
        
        starsList = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 2; i ++){
            starsList.add(new Stars(-5000, -5000, 0));
        }

        getImages();

        img = idle;

        shooterCooldown = 2000;

    }

    @Override
    public void getImages() {
        // TODO Auto-generated method stub
    }

    @Override
    public void drawAttacks(Graphics2D g2d) {
        for (Projectiles ep : starsList){
            ep.drawProjectile(g2d);
        }
        attack();
    }

    @Override
    public void changeRotation(double yPos, double xPos) {
        rotation = Math.atan2(yPos,xPos);
    }

    @Override
    public void attack() {

        if (alive){
            if (autoShooter == null || !autoShooter.isRunning()){
                
                ActionListener autoShooting = new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        for (Projectiles ep : starsList){
                            if (!ep.isActive()){
                                
                                ep.setInitialX((cm.getX()+(cm.getWidth()/2))-7.5);
                                ep.setInitialY((cm.getY()+(cm.getHeight()/2))-7.5);
                
                                ep.setProjectileX((cm.getX()+(cm.getWidth()/2))-7.5);
                                ep.setProjectileY((cm.getY()+(cm.getHeight()/2))-7.5);
                                ep.setProjectileRotation(rotation);
                                ep.setActive();
                                System.out.println("Shooting | attack()");
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

    @Override
    public CopyOnWriteArrayList<Projectiles> getProjectiles() {
        return starsList;
    }

    class Stars extends Projectiles {

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

        @Override
        public void drawProjectile(Graphics2D g2d) {
            g2d.setColor(new Color(255,205,60));
            Ellipse2D.Double star = new Ellipse2D.Double(xPos,yPos,this.width,this.height);
            g2d.fill(star);
        }

    }

    @Override
    public String showCharacterType() {
        return "Fairy";
    }

    @Override
    public void drawWeapon(Graphics2D g2d) {
        // TODO Auto-generated method stub
    }
    
}
