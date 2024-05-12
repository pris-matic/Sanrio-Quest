import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
The CharacterType abstract class is used to determine the 
stats of different characters that will be defined
throughout the game. The player selects from three different
<code> CharacterType </code> subclasses of this abstract
class that will determine their stats for the current run.

<p></p>
This class also allows the movement for the weapon of choice 
whenever the <Code> CharacterManager </code> instance moves

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

public abstract class CharacterType {
    
    // basic stats of the chosen character
    protected double hp, atk, def, maxHp; // stats
    protected CharacterManager cm;
    protected double x,y,rotation;

    protected boolean alive; // determines if hp is greater than 0
    protected boolean attacking; // whether the player is currently attacking
    
    // variables related to character images
    protected int updateSpriteCounter = 0; 
    protected int spriteValue = 1;
    protected int spriteNumber = 0;
    protected BufferedImage img,weaponImg;
    protected BufferedImage idle,front1,front2,back1,back2,left1,left2,right1,right2;
    protected BufferedImage[] imageList = new BufferedImage[9];
 
    /**
        Gets the maximum health the <Code>CharacterType</code> object can have
        @return the max health of the object.
    **/
    public double getMaxHealth(){
        return maxHp;
    }

    /**
        Gets the current health of the <Code>CharacterType</code> 
        object.
        @return the current health of the object.
    **/
    public double getHealth(){
        return hp;
    }

    /**
        Gets the attack of the <Code>CharacterType</code> object
        @return the attack of the object.
    **/
    public double getAttack(){
        return atk;
    }

    /**
        Gets the defense of the <Code>CharacterType</code> object
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
        Checks whether the object / player is attacking.
        @return true if the player is attacking, false otherwise.
    **/
    public boolean isAttacking(){
        return attacking;
    }

    /**
        A simple damage calculation whether certain conditions are met.
        It only divides the damage that the <code>EnemyType</code> will deal.
        <p></p> before dealing damage, it checks whether the player had recently
        received damage.
        @param damage is the damage the <code>CharacterType</code> object will take.
        @see EnemyType
        @see #getMaxHealth()
    **/
    public void takeDamage(double damage){
        if (!cm.isInvincible()){
            hp -= damage / def;
            cm.setInvincibility();
            if (hp <= 0){
                alive = false;
            }
        }
    }

    /**
        Heals the player. The amount of healing cannot exceed the 
        maximum health of the <code>CharacterType</code> object.
        @param health is the amount of health that will be healed to the player.
    **/
    public void healCharacter(double health){
        hp += health;
        
        if (hp >= 0){
            alive = true;
        }

        if (hp > maxHp){
            hp = maxHp;
        }
    }

    /**
        Sets the health of the player to the specified amount
        it is used for the HealthBar seen inside the canvas, as it is
        needed to update the other player's health.
        @param health is the amount of health the player has.
        @see GameCanvas.HealthBar
    **/
    public void setHealth(double health){
        hp = health;
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
        Gets the CharacterType of the <code>CharacterType</code> object
        @return a string that shows the object's CharacterType
    **/
    public abstract String showCharacterType();

    /**
        Gets the images used by each <code>CharacterType</code> subclass.
        This method is only called from within the subclasses themselves.
    **/
    public abstract void getImages();

    /**
        Gets the image to be displayed that depends on the character's
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
        A priority for what image will be drawn is as follows:
        <p></p> downward --> left --> right --> upward. <p></p>
        If the player is currently moving at the same direction, it gets
        updated once every certain value of repaints inside the canvas had passed
        @see CharacterManager#moveCharacter(String, boolean)
        @see #imageList
        @see GameCanvas#run()
    **/
    public void displayImage(){

        if (cm.isMovingDown()){
            if (spriteValue == 1){
                img = front1;
                spriteNumber = 1;
            }
            if (spriteValue == 2){
                img = front2;
                spriteNumber = 2;
            }
        } else if (cm.isMovingLeft()){
            if (spriteValue == 1){
                img = left1;
                spriteNumber = 3;
            }
            if (spriteValue == 2){
                img = left2;
                spriteNumber = 4;
            }
        } else if (cm.isMovingRight()){
            if (spriteValue == 1){
                img = right1;
                spriteNumber = 5;
            }
            if (spriteValue == 2){
                img = right2;
                spriteNumber = 6; 
            }         
        } else if (cm.isMovingUp()){
            if (spriteValue == 1){
                img = back1;
                spriteNumber = 7;
            }
            if (spriteValue == 2){
                img = back2;
                spriteNumber = 8;
            }
        } else {
            img = idle;
            spriteNumber = 0;
        }

        updateSpriteCounter ++;
        if (updateSpriteCounter > 12){
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
        Draws the weapon of the <code>CharacterType</code> subclass
        @param g2d is the Graphics2D object that will draw the weapon
    **/
    public abstract void drawWeapon(Graphics2D g2d);

    /**
        Draws the attacks, which are the projectiles of the <code>CharacterType</code> subclass
        @param g2d is the Graphics2D object that will draw the projectiles
        @see CharacterType.Projectiles
    **/
    public abstract void drawAttacks(Graphics2D g2d);

    public void drawEnemies(Graphics2D g2d, ArrayList<Enemy> enemies){
        for (Enemy e : enemies) {
            e.drawCharacter(g2d);
        }
    }

    /**
        Changes the rotation based on mouse movement.
        The calculation varies per <code>CharacterType</code> subclass.
        @param yPos is the y-position of the mouse.
        @param xPos is the x-position of the mouse.
        @see GameFrame.ConfigureWeapon
    **/
    public abstract void changeRotation(double yPos, double xPos);

    /**
        Lets the Player attack when the mouse is clicked. It also
        calls other methods that are related in moving the projectiles.
        @see GameFrame.ConfigureWeapon
        @see #attackMovement()
    **/
    public abstract void attack();

    /**
        Gets the projectiles (if any) of the <code>CharacterType</code> subclass.
        <p></p> A CopyOnWriteArrayList<> is used so that it is safely passed over the network
        @return an arraylist containing projectiles.
    **/
    public abstract CopyOnWriteArrayList<Projectiles> getProjectiles();

    /**
        The Projectiles inner abstract class is used in certain subclasses of the 
        <Code> CharacterType </code> outer class. 
        <p></p>
        This class also allows the movement for the projectiles and is
        responsible for drawing the projectiles when they are called.

        @author Anthony B. Deocadiz Jr. (232166)
        @author Ramona Miekaela S. Laciste (233403)
        @version April 11, 2024
    **/
    abstract class Projectiles {
        
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
            @see CharacterType#removeProjectiles(CopyOnWriteArrayList)
        **/
        public void setInitialX(double position){
            this.initX = position;
        }

        /**
            Sets the initial y-coordinate of the projectile. The initial position
            is used for calculation if the projectile had for a certain value.
            @param position is the new intial y-position of the projectile.
            @see CharacterType#removeProjectiles(CopyOnWriteArrayList)
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
            @see CharacterType#removeProjectiles(CopyOnWriteArrayList)
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
            @see CharacterType#removeProjectiles(CopyOnWriteArrayList)
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
            @see CharacterType#attackMovement()
            @see CharacterType#attack()
        **/
        public boolean isActive(){
            return active;
        }

        /**
            Flips the active status of the projectile. Used
            after checking whether certain conditions are met
            @see CharacterType#removeProjectiles(CopyOnWriteArrayList)
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
            Checks whether the bullet has collided with an <Code>Enemy</code> 
            which will then take damage
            @param chr is the enemy
            @return true if the projectile is colliding with the enemy, false otherwise.
            @see Enemy
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
        Helps in moving the projectiles fired by the player.
        The initial Position of the projectile when called / fired varies per sublcass
        @see CharacterType.Projectiles
    **/
    public abstract void attackMovement();

    /**
        "Removes" the projectiles off the screen when certain conditions are met,
        such as collision with walls, or other entities.
        @param projectiles is the ArrayList to be checked whether they had met the condition
        @see CharacterType.Projectiles
    **/
    public void removeProjectiles(CopyOnWriteArrayList<Projectiles> projectiles){

        for (int i = 0; i < projectiles.size() ; i ++){
            if (projectiles.get(i).isActive()){

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

                if (pythagorean >= 500 && projectiles.get(i).isActive()){
                    projectiles.get(i).setActive();
                    projectiles.get(i).setProjectileX(-5000);
                    projectiles.get(i).setProjectileY(-5000);
                    
                }
            }
        }
    }

    /**
        Checks whether the player is currently attacking with their weapon
        such as swinging the Melee Class' weapon.
        @param enemy is the enemy being checked if the player's weapon is colliding with them
        @see #isAttacking()
        @see Melee
    **/
    public abstract void weaponCollidingWithEnemy(Enemy enemy);

}
