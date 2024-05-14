import java.awt.Graphics2D;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.imageio.ImageIO;

/**
* The Ghost sublcass is one of the enemies that the
    players can face against.
* Extends the EnemyType class.
*/
public class Ghost extends EnemyType {

    /**
     * Constructor for the Ghost class.
     * @param cm The CharacterManager object associated with the game.
     */
    public Ghost(CharacterManager cm){

        this.cm = cm;
        
        maxHp = 100;
        hp = 100;
        atk = 5;
        def = 2;

        alive = true;
        width = 50;
        height = 50;

        getImages();

        img = front1;

    }

    /**
     * Returns the type of the enemy.
     * @return A string representing the enemy type ("Ghost").
     */
    @Override
    public String showEnemyType() {
        return "Ghost";
    }

    /**
     * Loads images for the ghost enemy from the resource directory.
     */
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

    /**
     * Draws the attack animations of the ghost enemy.
     * @param g2d The Graphics2D object used for drawing.
     */
    @Override
    public void drawAttacks(Graphics2D g2d) {

    }

    /**
     * Initiates an attack action for the ghost enemy.
     */
    @Override
    public void attack() {
        // unused do to the subclass having no projeciles
    }

    /**
     * Handles the movement during an attack action for the ghost enemy.
     */
    @Override
    public void attackMovement() {
        // unused do to the subclass having no projeciles
    }

    /**
     * Gets the projectiles associated with the ghost enemy.
     * @return A list of EnemyProjectiles associated with the ghost enemy (null for Ghost).
     */
    @Override
    public CopyOnWriteArrayList<EnemyProjectiles> getProjectiles() {
        return null;
    }
    
}
