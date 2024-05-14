import java.awt.Graphics2D;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.imageio.ImageIO;

public class Ghost extends EnemyType {

    public Ghost(CharacterManager cm){

        this.cm = cm;
        
        maxHp = 100;
        hp = 100;
        atk = 4;
        def = 2;

        alive = true;
        width = 50;
        height = 50;

        getImages();

        img = front1;

    }
    @Override
    public String showEnemyType() {
        return "Ghost";
    }

    @Override
    public void getImages() {
        
        try {
            front1 = ImageIO.read(getClass().getResourceAsStream("/Sprites/EnemySprites/Ghost/front1_ghost.png"));
            front2 = ImageIO.read(getClass().getResourceAsStream("/Sprites/EnemySprites/Ghost/front2_ghost.png"));

            imageList[0] = front1;
            imageList[1] = front2;

        } catch (IOException e) {
            System.out.println("IOException in EnemyType.getImages()");
        }
    }

    @Override
    public void drawAttacks(Graphics2D g2d) {

    }

    @Override
    public void attack() {
        
    }

    @Override
    public void attackMovement() {
        
    }

    @Override
    public CopyOnWriteArrayList<EnemyProjectiles> getProjectiles() {
        return null;
    }
    
}
