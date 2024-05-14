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
    
    private int currentLevel;

    /**
        Makes a new arraylist of enemies that contains two
        Ghosts and two WingedCreatures
        @see Ghost
        @see WingedCreature
    **/
    public EnemyGenerator(){
        
        super();

        this.add(new Enemy("ghost", 1200, 1800));
        this.add(new Enemy("ghost", 1200, 1800));
        this.add(new Enemy("wingedcreature", 1200, 1800));
        this.add(new Enemy("wingedcreature", 1200, 1800));

        currentLevel = 0;
        
    }

    /**
        Whenever the players enter a new level, the same enemies
        get respawned back, with their health being set back to maximum
        @see EnemyType#getMaxHealth()
        @see EnemyType#setAlive()
    **/
    public void resetHealth(){
        for (Enemy enemy : this){
            enemy.getEnemyType().setHealthToMaximum();
            enemy.getEnemyType().setAlive();
        }
        currentLevel ++;
        switch (currentLevel) {
            case 1:
                for (Enemy enemy : this){
                    enemy.setX(3600);
                    enemy.setY(1700);
                }
                break;
        
            case 2:
                for (Enemy enemy : this){
                    enemy.setX(5900);
                    enemy.setY(1700);
                }
                break;

            case 3:
                for (Enemy enemy : this){
                    enemy.setX(8300);
                    enemy.setY(1700);
                }
                break;

            case 4:
                for (Enemy enemy : this){
                    enemy.setX(10700);
                    enemy.setY(1700);
                }
                break;

            default:
                for (Enemy enemy : this){
                    enemy.setX(-7000);
                    enemy.setY(-7000);
                }
                break;
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

    /**
        Checks whether the player is currently attacking with their weapon
        such as swinging the Melee Class' weapon.   
        @param xPos is the x Position of the player
        @param yPos is the y Position of the player
        @param rotation is the current rotation of the player
        @param characterType is the CharacterType of the player.
    **/
    public void collidingWithWeapon(double xPos, double yPos, double rotation, String characterType){
        for (Enemy enemy : this){
            if (characterType.equalsIgnoreCase("melee")){
                
                double initialX = (xPos+(30)-12);
                double width = 25;
                double swordPositionX = initialX + (Math.cos(rotation) * width);
                
                double initialY = (yPos+(90*2/3));
                double height = 95;
                double swordPositionY = initialY + (Math.sin(rotation) * height);

                boolean colliding = false;

                colliding = !(swordPositionX + width <= enemy.getX()
                || swordPositionX >= enemy.getX() + enemy.getWidth()
                || swordPositionY + height <= enemy.getY()
                || swordPositionY >= enemy.getY() + enemy.getHeight());

                if (colliding){
                    enemy.getEnemyType().takeDamage(5);
                }

            } else if (characterType.equalsIgnoreCase("wizard")){

                double initialX = (xPos+(60*0.65));
                double width = 15;

                double initialY = (yPos+30);
                double height = 50;

                boolean colliding = false;

                colliding = !(initialX + width <= enemy.getX()
                || initialX >= enemy.getX() + enemy.getWidth()
                || initialY + height <= enemy.getY()
                || initialY >= enemy.getY() + enemy.getHeight());

                if (colliding){
                    enemy.getEnemyType().takeDamage(7);
                }
  
            } else if (characterType.equalsIgnoreCase("ranger")){
                double initialX = (xPos+60*11/20);
                double width = 30;

                double initialY = (yPos+(90/2));
                double height = 40;

                boolean colliding = false;

                colliding = !(initialX + width <= enemy.getX()
                || initialX >= enemy.getX() + enemy.getWidth()
                || initialY + height <= enemy.getY()
                || initialY >= enemy.getY() + enemy.getHeight());

                if (colliding){
                    enemy.getEnemyType().takeDamage(10);
                }                
            }
        }
    }

    /**
        Gets the current level where the enemies are being generated.
        Levels start at 0.
        @return the current level.
    **/
    public int getCurrentLevel(){
        return currentLevel;
    }

}
