import java.awt.Graphics2D;
import java.util.ArrayList;

/**
The CharacterType abstract class is used to define the 
stats of different characters that will be defined
throughout the game. Both enemies and the Player's
<code> CharacterType </code> are subclasses of this abstract
class.

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
    protected int hp, atk, def, maxHp; // stats
    protected CharacterManager cm;
    protected double x,y,rotation;
    
    protected boolean alive; // determines if hp is greater than 0
    
    public int getHealth(){
        return hp;
    }

    public int getAttack(){
        return atk;
    }

    public int getDefense(){
        return def;
    }

    public boolean isAlive(){
        return alive;
    }

    public void takeDamage(double damage){
        hp -= damage / def;
        if (hp <= 0){
            alive = false;
        }
    }

    public void healCharacter(double health){
        hp += health;
        
        if (hp >= 0){
            alive = true;
        }

        if (hp > maxHp){
            hp = maxHp;
        }
    }

    public double getRotation(){
        return rotation;
    }

    public void setRotation(double rotation){
        this.rotation = rotation;
    }

    public abstract String showCharacterType();

    public abstract void drawWeapon(Graphics2D g2d);

    public abstract void drawAttacks(Graphics2D g2d);

    public abstract void changeRotation(double yPos, double xPos);

    public abstract void attack();

    public abstract ArrayList<Projectiles> getProjectiles();

    abstract class Projectiles {
        
        protected double xPos,yPos,initX,initY,projectileRotation, width, height;
        protected int projectileSpeed;

        public void moveProjectileX(){
            this.xPos += projectileSpeed * Math.cos(projectileRotation);
        }

        public void moveProjectileY(){
            this.yPos += projectileSpeed * Math.sin(projectileRotation);
        }

        public void setProjectileX(double position){
            this.xPos = position;
        }

        public void setProjectileY(double position){
            this.yPos = position;
        }

        public double getX(){
            return this.xPos;
        }

        public double getInitX(){
            return this.initX;
        }

        public double getY(){
            return this.yPos;
        }

        public double getInitY(){
            return this.initY;
        }

        public double getWidth(){
            return this.width;
        }

        public double getHeight(){
            return this.height;
        }

        public abstract void drawProjectile(Graphics2D g2d);

    }

    public abstract void attackMovement();

    public void removeProjectiles(ArrayList<Projectiles> projectiles){

        for  (int i = 0; i < projectiles.size() ; i ++){
            double x = Math.abs(projectiles.get(i).getX() - projectiles.get(i).getInitX());
            double y = Math.abs(projectiles.get(i).getY() - projectiles.get(i).getInitY());
            double pythagorean = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
            if (pythagorean >= 400){
                projectiles.remove(i);
            }
        }
    }

}
