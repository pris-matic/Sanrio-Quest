import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
    The EnemyGenerator Class is responsible for generating
    all the enemies, and move them at the same time. It is
    also an arraylist that stores each and every enemy visible to the
    player's screen.

    @author Anthony B. Deocadiz Jr. (232166)
    @author Ramona Miekaela S. Laciste (233403)
    @version May 08, 2024
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

public class EnemyGenerator extends CopyOnWriteArrayList<Enemy>{
    
    public EnemyGenerator(){
        
        super();

        // TODO final work

        // this.add(new Enemy("ghost", -5000, -5000));
        // this.add(new Enemy("ghost", -5000, -5000));
        // this.add(new Enemy("wingedcreature", -5000, -5000));
        // this.add(new Enemy("wingedcreature", -5000, -5000));
        
        // TODO debug mode

        this.add(new Enemy("ghost", 600, 200));
        this.add(new Enemy("ghost", 600, 200));
        this.add(new Enemy("wingedcreature", 600, 200));
        this.add(new Enemy("wingedcreature", 600, 200));
        
    }

    /**
        Whenever the players enter a new level, the same enemies
        get respawned back, with their health being set back to maximum
        @see EnemyType#getMaxHealth()
    **/
    public void resetHealth(){
        for (Enemy enemy : this){
            enemy.getEnemyType().setHealthToMaximum();
            enemy.getEnemyType().setAlive();
        }
    }

    /**
        Moves all the enemies at the same time.
    **/
    public void moveAllEnemies(){
        for (Enemy enemy : this){
            enemy.moveAutomatically();
        }
    }
    
    /**
        The Invincibility Frames (IFrames) of each enemy gets
        reduced, for them to be able to take damage again.
        @see CharacterManager#reduceTimer()
    **/
    public void reduceIFrames(){
        for (Enemy enemy : this){
            enemy.reduceTimer();
        }
    }

    /**
        This checks whether the x and y values of the players projectiles hit the enemy in check.
        @param projectilesX is the set of x values of the projectiles of the player
        @param projectilesY is the set of y values of the projectiles of the player
        @param characterType determines the damage the enemy will take.
    **/
    public void collidedWithBullet(ArrayList<Double> projectilesX, ArrayList<Double> projectilesY,String characterType){
        for (Enemy enemy : this){

            if (enemy.getEnemyType().isAlive()){
                if(enemy.isCollidingWithBullet(projectilesX, projectilesY)){
                    if (characterType.equalsIgnoreCase("ranger")){
                        enemy.getEnemyType().takeDamage(10);
                    } else if (characterType.equalsIgnoreCase("wizard")){
                        enemy.getEnemyType().takeDamage(7);
                    } else if (characterType.equalsIgnoreCase("melee")){
                        enemy.getEnemyType().takeDamage(5);
                    }
                }   
            } 
        }
    }

    public void collidingWithWeapon(){
        for (Enemy enemy : this){
            
        }
    }

}
