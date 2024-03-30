/**
The GameServer class is responsible for client-sided operations.
This class allows the players to run the game. Actions done by
the player is then sent to the server, and will reflect to the
other player's perspective.

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

public class GameStarter {

    public static void main(String[] args) {
        
        GameFrame gf = new GameFrame();
        gf.connectToServer();
        gf.setUpGUI();
        gf.addControls();

    }
}
