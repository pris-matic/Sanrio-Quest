import java.awt.*;

/**
The Player class is the one that will be interacted
by a person. It stores information about their character
such as their movement, and current stats.

@author Anthony B. Deocadiz Jr. (232166)
@author Ramona Miekaela S. Laciste (233403)
@version March 16, 2024
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

public class Player extends CharacterManager{
      
    public Player(String name, String characterType, int xPos, int yPos){
        
        this.name = name;

        if (characterType.equalsIgnoreCase("ranger")){
            ct = new Ranger(this);
        } else if (characterType.equalsIgnoreCase("melee")){
            ct = new Melee(this);
        } else if (characterType.equalsIgnoreCase("wizard")){
            ct = new Wizard(this);
        }
        
        x = xPos;
        y = yPos;

        up = false;
        down = false;
        left = false;
        right = false;

        invincible = false;
        invincibleCooldown = 0;

        width = 60;
        height = 90;
        speed = 4;

        
    }

    @Override
    public void drawCharacter(Graphics2D g2d){
       
        g2d.drawImage(ct.useImage(),(int) x,(int) y,width,height,null);  
        g2d.setColor(Color.BLACK);
        g2d.drawString(name, (int) x, (int)y);

    }

    public void isCollidingWithBullet(Enemy enemy){

        if (enemy.getEnemyType().getProjectiles() != null){
            for (EnemyType.EnemyProjectiles p : enemy.getEnemyType().getProjectiles()){
                if (p.isCollidingWith(this)){;
                    p.setActive();
                    p.setProjectileX(-5000);
                    p.setProjectileY(-5000);
                    this.getCharacterType().takeDamage(enemy.getEnemyType().getAttack());
                    break;
                }
            }
        }
    }
    
}
