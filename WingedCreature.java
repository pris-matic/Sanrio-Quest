import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.imageio.ImageIO;
import javax.swing.Timer;

public class WingedCreature extends EnemyType{
    
    private CopyOnWriteArrayList<EnemyProjectiles> starsList;
    private int shooterCooldown;
    private Timer autoShooter,starMovement;

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
        for (int i = 0; i < 2; i ++){
            starsList.add(new Stars(-5000, -5000, 0));
        }

        getImages();

        img = front1;

        shooterCooldown = 1500;

    }

    @Override
    public String showEnemyType() {
        return "Winged Creature";
    }

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

    @Override
    public void drawAttacks(Graphics2D g2d) {
        for (EnemyProjectiles ep : starsList){
            ep.drawProjectile(g2d);
        }
    }

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
    public CopyOnWriteArrayList<EnemyProjectiles> getProjectiles() {
        return starsList;
    }

    class Stars extends EnemyProjectiles {

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
            g2d.setColor(new Color(197, 158, 1));
            Ellipse2D.Double star = new Ellipse2D.Double(xPos,yPos,this.width,this.height);
            g2d.fill(star);
        }

    }

}
