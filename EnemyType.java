import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.CopyOnWriteArrayList;

/**
The EnemyType abstract class is used to determine the 
stats of different enemies that will be defined
throughout the game. There are two possible
<code> EnemyType </code> subclasses of this abstract
class that will determine their stats for one instance.

@author Anthony B. Deocadiz Jr. (232166)
@author Ramona Miekaela S. Laciste (233403)
@version April 17, 2024
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

public abstract class EnemyType {
    
    // basic stats of an enemy
    protected double hp, atk, def, maxHp;
    protected int width,height; // stats
    protected CharacterManager cm;
    protected double x,y,rotation;
    
    // determines if hp is greater than 0
    // also determines whether the character is an active state or not
    protected boolean alive; 

    // variables related to character images
    protected int updateSpriteCounter = 0; 
    protected int spriteValue = 1;
    protected int spriteNumber = 0;
    protected BufferedImage img;
    protected BufferedImage front1,front2;
    protected BufferedImage[] imageList = new BufferedImage[2];
    
    /**
        Gets the width of the enemyType.
        @return the width of the enemy.
    */
    public int getWidth(){
        return width;
    }

    /**
        Gets the height of the enemyType.
        @return the height of the enemy.
    */
    public int getHeight(){
        return height;
    }

    /**
        Gets the maximum health the <Code>EnemyType</code> object can have
        @return the max health of the object.
    **/
    public double getMaxHealth(){
        return maxHp;
    }

    /**
        Gets the current health of the <Code>EnemyType</code> 
        object.
        @return the current health of the object.
    **/
    public double getHealth(){
        return hp;
    }

    /**
        Gets the attack of the <Code>EnemyType</code> object
        @return the attack of the object.
    **/
    public double getAttack(){
        return atk;
    }

    /**
        Gets the defense of the <Code>EnemyType</code> object
        @return the defense of the object.
    **/
    public double getDefense(){
        return def;
    }

    /**
        Checks whether the object is still alive.
        @return true if the current health of the object is greater than 0, false otherwise.
    **/
    public boolean isAlive(){
        return alive;
    }

    /**
        A simple damage calculation whether certain conditions are met.
        It only divides the damage that the <code>CharacterType</code> will deal.
        @param damage is the damage the <code>EnemyType</code> object will take.
        @see CharacterType
        @see #getMaxHealth()
    **/
    public void takeDamage(double damage){
        hp -= damage / def;
        if (hp <= 0){
            alive = false;
        }
    }

    /**
        Flips the current boolean value of the alive variable.
        Determines whether the enemy instance should be moved inside the
        playing field.
        @see #alive
    **/
    public void setAlive(){
        if (alive){
            alive = false;
        } else {
            alive = true;
        }
    }

    /**
        Gets the rotation of the <code>CharacterType</code>'s weapon
        @return the rotation of the weapon in radians
    **/
    public double getRotation(){
        return rotation;
    }

    /**
        Sets the rotation of the <code>CharacterType</code>'s weapon
        @param rotation is the rotation of the weapon in radians
    **/
    public void setRotation(double rotation){
        this.rotation = rotation;
    }

    /**
        Gets the EnemyType of the <code>EnemyType</code> object
        @return a string that shows the object's EnemyType
    **/
    public abstract String showEnemyType();

    /**
        Gets the images used by each <code>EnemyType</code> subclass.
        This method is only called from within the subclasses themselves.
    **/
    public abstract void getImages();

    /**
        Gets the image to be displayed that depends on the enemy's
        movement.
        @return the image to be displayed.
        @see #displayImage()
    **/
    public BufferedImage useImage(){
        return img;
    }

    /** 
        Displays an image depending on what the direction 
        of the <code>CharacterManager</code> object is.
        <p></p>
        For the enemies, it will only alter between two images.

        @see CharacterManager#moveCharacter(String, boolean)
        @see #imageList
        @see GameCanvas#run()
    **/
    public void displayImage(){

        if (spriteValue == 1){
            img = front1;
            spriteNumber = 0;
        }
        if (spriteValue == 2){
            img = front2;
            spriteNumber = 1;
        }

        updateSpriteCounter ++;
        if (updateSpriteCounter > 15){
            if (spriteValue == 1){
                spriteValue = 2;
            } else {
                spriteValue = 1;
            }
            updateSpriteCounter = 0;
        }
    }

    /**
        Gets the sprite number of the current dispalyed image.
        That number corresponds to a value inside an array of images.
        <p></p> It is used in the networking side of the game so that
        the image would reflect on the other player's screen.
        @see #displayImage()
        @see GameFrame.WriteToServer
        @see GameFrame.ReadFromServer
        @return an integer that is an equivalent of an index inside an array
    **/
    public int getSpriteNumber(){
        return spriteNumber;
    }

    /**
        Sets the image based on the value passed to it.
        The number that is passed corresponds to a value inside an array
        of images.
        @param imageNum is the value taken inside the array.
    **/
    public void setImage(int imageNum){
        img = imageList[imageNum];
    }

    /**
        Draws the attacks, which are the projectiles of the <code>EnemyType</code> subclass
        @param g2d is the Graphics2D object that will draw the projectiles
        @see EnemyType.EnemyProjectiles
    **/
    public abstract void drawAttacks(Graphics2D g2d);

    /**
        Allows the <code>Enemy</code> object to fire out projectiles (if there is any). It also
        calls other methods that are related in moving the projectiles.
        @see #attackMovement()
    **/
    public abstract void attack();

    /**
        Gets the projectiles (if any) of the <code>EnemyType</code> subclass.
        <p></p> A CopyOnWriteArrayList<> is used so that it is safely passed over the network
        @return an arraylist containing projectiles.
    **/
    public abstract CopyOnWriteArrayList<EnemyProjectiles> getProjectiles();

    /**
        The EnemyProjectiles inner abstract class is used in certain subclasses of the 
        <Code> EnemyType </code> outer class. 
        <p></p>
        This class also allows the movement for the projectiles and is
        responsible for drawing the projectiles when they are called.

        @author Anthony B. Deocadiz Jr. (232166)
        @author Ramona Miekaela S. Laciste (233403)
        @version April 29, 2024
    **/
    abstract class EnemyProjectiles {
        
        protected double xPos,yPos,initX,initY,projectileRotation, width, height;
        protected int projectileSpeed;
        protected boolean active;

        /**
            Moves the projectile on the given path horizontally
        **/
        public void moveProjectileX(){
            this.xPos += projectileSpeed * Math.cos(projectileRotation);
        }

        /**
            Moves the projectile on the given path vertically
        **/
        public void moveProjectileY(){
            this.yPos += projectileSpeed * Math.sin(projectileRotation);
        }

        /**
            Draws the projectile at the given location
            @param position is new the x-coordinate of the projectile
        **/
        public void setProjectileX(double position){
            this.xPos = position;
        }

        /**
            Draws the projectile at the given location
            @param position is new the y-coordinate of the projectile
        **/
        public void setProjectileY(double position){
            this.yPos = position;
        }

        /**
            Sets the initial x-coordinate of the projectile. The initial position
            is used for calculation if the projectile had for a certain value.
            @param position is the new intial x-position of the projectile.
            @see EnemyType#removeProjectiles(CopyOnWriteArrayList)
        **/
        public void setInitialX(double position){
            this.initX = position;
        }

        /**
            Sets the initial y-coordinate of the projectile. The initial position
            is used for calculation if the projectile had for a certain value.
            @param position is the new intial y-position of the projectile.
            @see EnemyType#removeProjectiles(CopyOnWriteArrayList)
        **/
        public void setInitialY(double position){
            this.initY = position;
        }

        /**
            Sets the projectile's rotation so that it moves accordingly
            @param rotate is the rotation value
        **/
        public void setProjectileRotation (double rotate){
            this.projectileRotation=rotate;
        }

        /**
            Gets the current x-coordinate of the projectile.
            @return the x-coordinate of the projectile.
        **/
        public double getX(){
            return this.xPos;
        }
        
        /**
            Gets the initial x-coordinate of the projectile. It is used
            for calculation for when to "remove" the projectile off the screen
            @return the initial x-coordinate of the projectile.
            @see EnemyType#removeProjectiles(CopyOnWriteArrayList)
        **/
        public double getInitialX(){
            return this.initX;
        }

        /**
            Gets the current y-coordinate of the projectile.
            @return the y-coordinate of the projectile.
        **/
        public double getY(){
            return this.yPos;
        }

        /**
            Gets the initial y-coordinate of the projectile. It is used
            for calculation for when to "remove" the projectile off the screen
            @return the initial y-coordinate of the projectile.
            @see EnemyType#removeProjectiles(CopyOnWriteArrayList)
        **/
        public double getInitialY(){
            return this.initY;
        }

        /**
            Gets the width of the projectile.
            @return the width of the projectile.
        **/
        public double getWidth(){
            return this.width;
        }

        /**
            Gets the height of the projectile.
            @return the height of the projectile.
        **/
        public double getHeight(){
            return this.height;
        }

        /**
            Checks whether the current projectile is "active" or called.
            If it's not active, then it will remain stationary. If it is
            in an active state, then it will move accordingly.
            @return true if the projectile was recently called, false otherwise.
            @see EnemyType#attackMovement()
            @see EnemyType#attack()
        **/
        public boolean isActive(){
            return active;
        }

        /**
            Flips the active status of the projectile. Used
            after checking whether certain conditions are met
            @see EnemyType#removeProjectiles(CopyOnWriteArrayList)
        **/
        public void setActive(){
            if (active){
                active = false;
            } else {
                active = true;
            }
        }

        /**
            Draws the projectiles inside the canvas
            @param g2d is the graphics object that will draw the projectiles
        **/
        public abstract void drawProjectile(Graphics2D g2d);

        /**
            Checks whether the bullet has collided with a <Code>Player</code> 
            which will then take damage
            @param chr is the player
            @return true if the projectile is colliding with the Player, false otherwise.
            @see Player
        **/
        public boolean isCollidingWith(CharacterManager chr){
            
            return !(chr.getX() + chr.getWidth() <= xPos ||
            chr.getX() >= xPos + this.width ||
            chr.getY() + chr.getHeight() <= yPos ||
            chr.getY() >= yPos + this.height);
            
        }

        /**
            Checks whether the projectile had collided with a wall.
            @param w is the <code>Walls</code> instance.
            @return true if it is collding, false otherwise.
        **/
        public boolean isCollidingWithWall(Walls w){
            return !(w.getX() + w.getWidth() <= xPos ||
            w.getX() >= xPos + this.width ||
            w.getY() + w.getHeight() <= yPos ||
            w.getY() >= yPos + this.height);
        }

    }

    /**
        Helps in moving the projectiles fired by the enemy.
        The initial Position of the projectile when called / fired varies per sublcass
        @see EnemyType.EnemyProjectiles
    **/
    public abstract void attackMovement();

    /**
        "Removes" the projectiles off the screen when certain conditions are met,
        such as collision with walls, or other entities.
        @param projectiles is the ArrayList to be checked whether they had met the condition
        @see EnemyType.EnemyProjectiles
    **/
    public void removeProjectiles(CopyOnWriteArrayList<EnemyProjectiles> projectiles){

        for  (int i = 0; i < projectiles.size() ; i ++){

            for (Walls w : GameCanvas.getWalls()){
                if (projectiles.get(i).isCollidingWithWall(w)){
                    projectiles.get(i).setActive();
                    projectiles.get(i).setProjectileX(-5000);
                    projectiles.get(i).setProjectileY(-5000);
                }
            }

            double x = Math.abs(projectiles.get(i).getX() - projectiles.get(i).getInitialX());
            double y = Math.abs(projectiles.get(i).getY() - projectiles.get(i).getInitialY());
            double pythagorean = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
            if (pythagorean >= 600 && projectiles.get(i).isActive()){
                projectiles.get(i).setActive();
                projectiles.get(i).setProjectileX(-5000);
                projectiles.get(i).setProjectileY(-5000);
            }
        }
    }

}
