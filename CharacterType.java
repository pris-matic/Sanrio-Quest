import java.awt.Graphics2D;

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

    // cooldown of attacks of each character
    protected double atkCooldown, skillCooldown, specialCooldown;
    
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

    public double getAutoCooldown(){
        return atkCooldown;
    }

    public double getSkillCooldown(){
        return skillCooldown;
    }

    public double getSpecialCooldown(){
        return specialCooldown;
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

    public abstract void changeRotation(double yPos, double xPos);

}
