import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
The GameFrame class sets up the player, and displays
the objects inside the GUI. It displays the specific player's
perspective in their machine.

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

public class GameFrame {

    private JFrame frame;
    private JPanel gamePane;
    private GameCanvas gc;

    // used to get the player's names and the CharacterType they are using
    private String name,playerType;
    private String p2Name,p2PlayerType;

    private Player p;
    private Player p2;
    private Socket socket;
    private int playerID;
    private ReadFromServer rfs;
    private WriteToServer wts;
    private EnemyGenerator enemygen;
    
    /**
        Instantiates the GameFrame for the player and
        gets the contentpane as well.
    **/
    public GameFrame(){

        frame = new JFrame();
        gamePane = (JPanel) frame.getContentPane();
        gamePane.setFocusable(true);

    }
    
    // GUI

    /**
        Sets up the GUI that contains the GameCanvas
    **/
    public void setUpGUI(){

        createPlayers();

        frame.setTitle("Sanrio Quest | " + p.getName());

        gc = new GameCanvas(p,p2);
        enemygen = gc.getEnemies();
        gc.startThread();
        frame.add(gc);  
        frame.pack();

        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /** 
        Sets up the players after receiving information from the network
        it is only called locally, inside the SetUpGUI() method
        @see #setUpGUI()
    **/
    private void createPlayers(){
        
        if (playerID == 1){
            p = new Player(name,playerType,400,1900);
            p2 = new Player(p2Name,p2PlayerType,400,2000);
        } else {
            // p2 = new Player(p2Name,p2PlayerType,400,2000);
            // p = new Player(name,playerType,400,2000);
            p2 = new Player("p2Name","melee",400,2000);
            p = new Player("a","wizard",-9630,-9745);
        }
    }
    
    // Controls

    /**
        The KeysPressed inner class is responsible for performing actions
        when the <code> Player </code> presses specific buttons. It is 
        then called in one of the GameFrame's methods.

        @author Anthony B. Deocadiz Jr. (232166)
        @author Ramona Miekaela S. Laciste (233403)
        @version March 24, 2024
        @see GameFrame#addControls()
    **/
    class KeysPressed extends AbstractAction {

        private String direction;

        /**
            Construct a KeysPressed object with a given direction
            @param dir is the direction
        **/
        public KeysPressed(String dir){
            direction = dir;
        }

        /**
            Perform action based on which key was pressed.
            @param ae is the action done
        **/
        @Override
        public void actionPerformed(ActionEvent ae) {
            p.moveCharacter(direction, true);
        }
        
    }

    /**
        The KeysReleased inner class is responsible for performing actions
        when the <code> Player </code> releases specific buttons. It is 
        then called in one of the GameFrame's methods.

        @author Anthony B. Deocadiz Jr. (232166)
        @author Ramona Miekaela S. Laciste (233403)
        @version March 24, 2024
        @see GameFrame#addControls()
    **/
    class KeysReleased extends AbstractAction {

        private String direction;

        /**
            Construct a KeysReleased object with a given direction
            @param dir is the direction
        **/
        public KeysReleased(String dir){
            direction = dir;
        }

        /**
            Perform action based on which key was released.
            @param ae is the action done
        **/
        @Override
        public void actionPerformed(ActionEvent ae) {
            p.moveCharacter(direction, false);
        }

    }

    /**
        The ConfigureWeapon inner class is responsible for performing actions
        when the <code> Player </code> clicks or move the mouse. It is 
        then called in one of the GameFrame's methods.

        @author Anthony B. Deocadiz Jr. (232166)
        @author Ramona Miekaela S. Laciste (233403)
        @version March 31, 2024
        @see GameFrame#addControls()
    **/
    class ConfigureWeapon extends MouseAdapter {

        private CharacterType ct;
        private Camera c;

        /**
            Instantiates a ConfigureWeapon object that
            calls both the characterType of the player, and the Camera
            used.
            @see Player
            @see Camera
        **/
        public ConfigureWeapon(){
            ct = p.getCharacterType();
            c = gc.getCamera();
        }

        /**
            Allows the <code> Player </code> to fire projectiles, or move its weapon.
            @param me is the action done.
        **/
        @Override
        public void mouseClicked(MouseEvent me) {
            ct.attack();
        }

        /**
            Allows the <code> Player </code> to aim their weapon by rotating it.
            @param me is the action done.
        **/
        @Override
        public void mouseDragged(MouseEvent me) {
            if (!ct.isAttacking()){
                ct.changeRotation(me.getY() + c.getY(),me.getX() + c.getX());
            }
        }

        /**
            Allows the <code> Player </code> to aim their weapon by rotating it.
            @param me is the action done.
        **/
        @Override
        public void mouseMoved(MouseEvent me) {
            if (!ct.isAttacking()){
                ct.changeRotation(me.getY() + c.getY(),me.getX() + c.getX());
            }
        }

    }

    /**
        Maps the controls to the JFrame. It then reflects to the <code> Player </code> and is drawn
        inside the <code> GameCanvas </code>.
        <p></p> The configurations for the controls are done by the inner classes of the
        <code> GameFrame </code> class
        @see GameFrame.KeysPressed
        @see GameFrame.KeysReleased
        @see GameFrame.ConfigureWeapon
    **/
    public void addControls(){

        ActionMap am = gamePane.getActionMap();
        InputMap im = gamePane.getInputMap();

        am.put("movingUp", new KeysPressed("up"));
        am.put("movingDown", new KeysPressed("down"));
        am.put("movingLeft", new KeysPressed("left"));
        am.put("movingRight", new KeysPressed("right"));

        am.put("releasedUp", new KeysReleased("up"));
        am.put("releasedDown", new KeysReleased("down"));
        am.put("releasedLeft", new KeysReleased("left"));
        am.put("releasedRight", new KeysReleased("right"));

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0,false), "movingUp");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0,false), "movingDown");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0,false), "movingLeft");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0,false), "movingRight");
        
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0,true), "releasedUp");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0,true), "releasedDown");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0,true), "releasedLeft");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0,true), "releasedRight");

        frame.addMouseListener(new ConfigureWeapon());
        frame.addMouseMotionListener(new ConfigureWeapon());

    }

    // Networking / Server Handling

    /**
        The ReadFromServer class allows the player to receive information
        the <code> GameServer</code> provides them with. This is where the information of the
        other player that the <code> GameCanvas </code> class will draw will
        be taken from.

        @author Anthony B. Deocadiz Jr. (232166)
        @author Ramona Miekaela S. Laciste (233403)
        @version March 24, 2024
        @see GameCanvas
        @see GameServer
    **/
    class ReadFromServer implements Runnable {

        private DataInputStream dataIn;

        /**
            Sets up a ReadFromServer object for the player
            @param in is the DataInputStream that will receive data
        **/
        public ReadFromServer(DataInputStream in){
            dataIn = in;

            System.out.println("RFS initialized");
        }

        /** 
            Continuously receives information from the server and updates the
            game.
        **/
        @Override
        public void run() {
            try {
                while (true){

                    String playerName = dataIn.readUTF();
                    double playerX = dataIn.readDouble();
                    double playerY = dataIn.readDouble();
                    double weaponRotation = dataIn.readDouble();
                    
                    int projectileCount = dataIn.readInt();

                    ArrayList<Double> projectileX = new ArrayList<>();
                    ArrayList<Double> projectileY = new ArrayList<>();
                    
                    ArrayList<Double> enemyX = new ArrayList<>();
                    ArrayList<Double> enemyY = new ArrayList<>();

                    ArrayList<Double> enemyProjectileX = new ArrayList<>();
                    ArrayList<Double> enemyProjectileY = new ArrayList<>();


                    for (int i = 0; i < projectileCount ; i++){
                        projectileX.add(dataIn.readDouble());
                        projectileY.add(dataIn.readDouble());
                    }

                    for (int i = 0; i < 4 ; i ++){
                        enemyX.add(dataIn.readDouble());
                        enemyY.add(dataIn.readDouble());
                    }

                    for (int i = 0; i < 4 ; i ++){
                        int numProjectiles = dataIn.readInt();
                        if (numProjectiles == 2){
                            for (int j = 0; j < numProjectiles ; j++){
                                enemyProjectileX.add(dataIn.readDouble());
                                enemyProjectileY.add(dataIn.readDouble());
                            }
                        }
                    }

                    int playerImage = dataIn.readInt();
                    double player2Health = dataIn.readDouble();
                    boolean player2Alive = dataIn.readBoolean();
                    int toNextLevel = dataIn.readInt();
                    
                    if(p2 != null && enemygen != null){
                        p2.setName(playerName);
                        p2.setX(playerX);
                        p2.setY(playerY);
                        p2.getCharacterType().setRotation(weaponRotation);
                        p2.getCharacterType().setImage(playerImage);
                        p2.getCharacterType().setHealth(player2Health);
                        
                        if (p2.getCharacterType().getProjectiles() != null){
                            // a getter method to determine what type of projectile is being sent by the other player
                            CharacterType ct = p2.getCharacterType();
                            p2.getCharacterType().getProjectiles().clear();
                            
                            for (int i = 0; i < projectileCount ; i++){ 
                                if (ct instanceof Ranger){
                                    CharacterType.Projectiles b = ((Ranger) ct).new Bullet(projectileX.get(i), projectileY.get(i), weaponRotation);
                                    p2.getCharacterType().getProjectiles().add(b);
                                } else if (ct instanceof Wizard){
                                    CharacterType.Projectiles o = ((Wizard) ct).new Orb(projectileX.get(i), projectileY.get(i), weaponRotation);
                                    p2.getCharacterType().getProjectiles().add(o);
                                }
                            }

                        }
                        for (int i = 0; i < 4; i++){
                            gc.getEnemies().get(i).setX(enemyX.get(i));
                            gc.getEnemies().get(i).setY(enemyY.get(i));
                        }

                        // only for the ones with projectiles
                        // which is at index 2 and 3 always
                        for (int i = 2; i < 4; i++){

                            for (int j = 0 ; j < 2; j++){
                                if (i == 2){
                                    gc.getEnemies().get(i).getEnemyType().getProjectiles().get(j).setProjectileX(enemyProjectileX.get(j));
                                    gc.getEnemies().get(i).getEnemyType().getProjectiles().get(j).setProjectileY(enemyProjectileY.get(j));
                                } else {
                                    gc.getEnemies().get(i).getEnemyType().getProjectiles().get(j).setProjectileX(enemyProjectileX.get(j+2));
                                    gc.getEnemies().get(i).getEnemyType().getProjectiles().get(j).setProjectileY(enemyProjectileY.get(j+2));
                                }
                            }
                        }

                        if (!player2Alive){
                            gameOver();
                            p.getCharacterType().setAlive();
                        }

                        if (p.getLevelsCleared() < toNextLevel){
                            moveToNextLevel(toNextLevel);
                            p.addLevelWin();
                        }
                        
                    }
                }
            } catch (IOException ex) {
                System.out.println("IOEXception from RFS.run()");
            }
        }

        /**
            Gets the initial information of the other player. This acts as a
            "go" signal to start the game.
        **/
        public void waitServer(){
            try {
                p2Name = dataIn.readUTF();
                p2PlayerType = dataIn.readUTF();
                String message = dataIn.readUTF();
                System.out.println("Message from Server: " + message);

                Thread readThread = new Thread(rfs);
                Thread writeThread = new Thread(wts);
                readThread.start();
                writeThread.start();

            } catch (IOException ex) {
                System.out.println("IOException from RFS.waitServer()");
            }
        }
    }

    /**
        The WriteToServer class allows the player to send information to
        the <code> GameServer</code>. The information of the
        curent player will be sent to the other player through this class.

        @author Anthony B. Deocadiz Jr. (232166)
        @author Ramona Miekaela S. Laciste (233403)
        @version March 24, 2024
        @see GameServer
    **/
    class WriteToServer implements Runnable {

        private DataOutputStream dataOut;

        /**
            Sets up a WriteToServer object that will send out information
            @param out is the DataOutputStream responsible in sending out necessary information
        **/
        public WriteToServer(DataOutputStream out){
            dataOut = out;
            System.out.println("WTS initialized");
        }

        /**
            Continuously sends out information to the server, which will be received
            by the other player.
        **/
        @Override
        public void run(){
            try {
                while (true){
                    
                    if(p != null){
                        dataOut.writeUTF(p.getName());
                        dataOut.writeDouble(p.getX());
                        dataOut.writeDouble(p.getY());
                        dataOut.writeDouble(p.getCharacterType().getRotation());
                            
                        if (p.getCharacterType().getProjectiles() != null){
                            int currentProjectiles = p.getCharacterType().getProjectiles().size();
                            dataOut.writeInt(currentProjectiles);
                        
                            for (int i = 0; i < currentProjectiles ; i++){
                                dataOut.writeDouble(p.getCharacterType().getProjectiles().get(i).getX());
                                dataOut.writeDouble(p.getCharacterType().getProjectiles().get(i).getY());
                            }
                        } else {
                            dataOut.writeInt(0);
                        }

                        dataOut.writeInt(p.getCharacterType().getSpriteNumber());
                        dataOut.writeDouble(p.getCharacterType().getHealth());
                        dataOut.writeBoolean(p.getCharacterType().isAlive());
                    
                        dataOut.flush();

                    }

                    try {
                        Thread.sleep(35);
                    } catch (InterruptedException ex) {
                        System.out.println("InterruptedException from WTS.run()");
                    }
                }
           
            } catch (IOException ex) {
                System.out.println("IOException from WTS.run()");
            }
        }
    }

    /**
        Allows the player to connect to a server to send and receive data from.
        The user will enter the Server / Host's IP address, along with their name
        and their desired <code>CharacterType</code>.
    **/
    public void connectToServer(){
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Host IP Address: ");
            String hostAddress = sc.nextLine();
            socket = new Socket(hostAddress,45375);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             
            System.out.print("Enter Your name: ");
            name = sc.nextLine();
            System.out.println("Hello " + name + "!");
            System.out.println("Enter CharacterType: ");
            playerType = sc.nextLine();

            sc.close();

            playerID = in.readInt();
            System.out.println("You are player # " + playerID);
            out.writeUTF(name);
            out.writeUTF(playerType);
            
            if (playerID == 1){
                System.out.println("Waiting for other players...");
            }
            rfs = new ReadFromServer(in);
            wts = new WriteToServer(out);
            rfs.waitServer();
        } catch (IOException ex) {
            System.out.println("IOException From connectToServer()");
        }
    }

    /** 
        Teleports the players to the GameOver screen whenever one
        of the players had died.
        @see CharacterType#isAlive()
    **/
    public void gameOver(){
        p.setX(10000);
        p.setY(10000);
        p2.setX(10000);
        p2.setY(10000);
    }

    /** 
        Teleports the players to the Winner's screen when they have
        cleared all the levels without dying.
        @see Player#getLevelsCleared()
    **/
    public void gameWin(){
        p.setX(-9630);
        p.setY(-9745);
        p2.setX(-9630);
        p2.setY(-9745);
    }

    /**
        Moves to the next level whenever all the enemies have been
        eliminated.
        @param level is the next level to go to.
        @see EnemyGenerator#resetHealth()
    **/
    public void moveToNextLevel(int level){

        switch (level) {
            case 1:
                p.setX(2800);
                p.setY(1700);
                p2.setX(2800);
                p2.setY(1700);
                break;
            
            case 2:
                p.setX(5300);
                p.setY(1700);
                p2.setX(5300);
                p2.setY(1700);
                break;

            case 3:
                p.setX(7600);
                p.setY(1700);
                p2.setX(7600);
                p2.setY(1700);
                break;

            case 4:
                p.setX(10000);
                p.setY(1700);
                p2.setX(10000);
                p2.setY(1700);

            case 5:
                gameWin();
                break;

        }
    }
}
