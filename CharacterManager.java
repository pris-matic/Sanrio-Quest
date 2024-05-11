import java.awt.Graphics2D;

/**
The CharacterManager abstract class is used to define the 
movement of different characters that will be defined
throughout the game. Both <code>Enemy</code> and the <code>Player</code>
classes rely on this class for their movement mechanics.

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

public abstract class CharacterManager {
    
    protected double x, y; // player's coordinates
    protected int width, height, speed;
    protected boolean up,down,left,right; // movement
    protected CharacterType ct; // used by the player
    protected EnemyType et; // used by the enemies
    protected String name;

    // for invincibility frames | IFrames
    protected boolean invincible;
    protected int invincibleCooldown;

    /**
        moves the object horizontally 
        @param movement is the value of how much it will move
    **/
    public void moveX(double movement){
        x += movement;
    }

    /**
        moves the object vertically 
        @param movement is the value of how much it will move
    **/
    public void moveY(double movement){
        y += movement;
    }

    /**
        Sets the x-position of the object, used in updating
        the other <code>CharacterManager</code>s, or when 
        certain conditions are met.
        @param position is the new position of the object.
    **/
    public void setX(double position){
        x = position;
    }

    /**
        sets the y-position of the object, used in updating
        the other <code>CharacterManager</code>s, or when 
        certain conditions are met.
        @param position is the new position of the object.
    **/
    public void setY(double position){
        y = position;
    }

    /**
        Returns the current x-coordinate of this object.
        @return the x-coordinate of the object.
    **/
    public double getX(){
        return x;
    }

    /**
        Returns the current y-coordinate of this object.
        @return the y-coordinate of the object.
    **/
    public double getY(){
        return y;
    }

    /**
        Returns the width of the object. 
        @return the width of the object.
    **/
    public int getWidth(){
        return width;
    }

    /**
        Returns the height of the object. 
        @return the height of the object.
    **/
    public int getHeight(){
        return height;
    }

    /**
        Checks whether the object is currently moving upward.
        @return true if it is moving up, false otherwise.
    **/
    public boolean isMovingUp(){
        return up;
    }

    /**
        Checks whether the object is currently moving downward.
        @return true if it is moving down, false otherwise.
    **/
    public boolean isMovingDown(){
        return down;
    }

    /**
        Checks whether the object is currently moving left.
        @return true if it is moving left, false otherwise.
    **/
    public boolean isMovingLeft(){
        return left;
    }

    /**
        Checks whether the object is currently moving right.
        @return true if it is moving right, false otherwise.
    **/
    public boolean isMovingRight(){
        return right;
    }

    // TODO finalize this first
    public boolean isInvincible(){
        return invincible;
    }

    public void setInvincibility(){
        if (invincible){
            invincible = false;
        } else {
            invincible = true;
        }
    }

    /**
        Gets the speed of the object.
        @return the speed of the object.
    **/
    public int getSpeed(){
        return speed;
    }

    /**
        Gets the <Code>CharacterType</code> of the object.
        It allows the object to access the CharacterType's methods
        @return the <Code>CharacterType</code> of the object.
        @see CharacterType
    **/
    public CharacterType getCharacterType(){
        return ct;
    }

    /**
        Gets the <Code>EnemyType</code> of the object.
        It allows the object to access the EnemyType's methods
        @return the <Code>EnemyType</code> of the object.
        @see EnemyType
    **/
    public EnemyType getEnemyType(){
        return et;
    }

    /**
        Gets the name of the object.
        @return the name of the object.
    **/
    public String getName(){
        return name;
    }

    /**
        Sets the name of the object
        @param n is the name of the object.
    **/
    public void setName(String n){
        name = n;
    }

    /**
        Checks whether the object is currently hitting a wall;
        @param wall is an instance of the <code>Walls</code> class
        @return true if the object is colliding with a <code>Walls</code> object, false otherwise
        @see Walls
    **/
    public boolean isCollidingWithWall(Walls wall){
        return !(wall.getX() + wall.getWidth() <= x ||
        wall.getX() >= x + width ||
        wall.getY() + wall.getHeight() <= y + (height * 0.75) ||
        wall.getY() >= y + height);
    }

    /**
        Draws the character inside the canvas
        @param g2d is the Graphics2D object that will be drawing the character
    **/
    public abstract void drawCharacter(Graphics2D g2d);
    
    /**
        Determines whether two instances of the <code>CharacterManager</code> are colliding
        with each other.
        @param player is the player instance under <code>CharacterManager</code>.
        @param enemy is the enemy instance under <code>CharacterManager</code>.
        @return true if they are colliding with each other, false otherwise.
    */
    public boolean isCollidingWithEntity(CharacterManager player, CharacterManager enemy){
       
        boolean collision = false;

        collision = !(player.getX() + player.getWidth() <= enemy.getX()||
        player.getX()>= enemy.getX() + enemy.getWidth() ||
        player.getY() + player.getHeight() <= enemy.getY()||
        player.getY()>= enemy.getY() + enemy.getHeight());

        return collision;

    }

    /**
        Moves the object in the specified direction
        @param direction is the direction the movement will be checked
        @param move tells whether the object will move in that direction.
        @see GameFrame.KeysPressed
        @see GameFrame.KeysReleased
    **/
    public void moveCharacter(String direction, boolean move){
    
        if (direction.equalsIgnoreCase("up")){
            up = move;
        }
        if (direction.equalsIgnoreCase("down")){
            down = move;
        }
        if (direction.equalsIgnoreCase("left")){
            left = move;
        }
        if (direction.equalsIgnoreCase("right")){
            right = move;
        }
    }

}
