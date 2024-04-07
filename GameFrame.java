import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

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

    // TODO fix networking issue here
    // private String name;
    // private String playerType;

    private Player p;
    private Player p2;
    private Socket socket;
    private int playerID;
    private ReadFromServer rfs;
    private WriteToServer wts;
    
    public GameFrame(){

        frame = new JFrame();
        gamePane = (JPanel) frame.getContentPane();
        gamePane.setFocusable(true);

    }
    
    // GUI

    public void setUpGUI(){

        createPlayers();
    
        frame.setTitle("Dungeon Crawler Testing | " + p.getName());

        gc = new GameCanvas(p,p2);
        frame.add(gc);
        frame.pack();

        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
    }

    private void createPlayers(){
        if (playerID == 1){
            p = new Player("prism", "ranger",200,200);
            p2 = new Player("test","ranger",400,200);
        } else {
            p2 = new Player("prism", "ranger",200,200);
            p = new Player("test","ranger",400,200);
        }
    }

    // Controls

    class KeysPressed extends AbstractAction {

        private String direction;

        public KeysPressed(String dir){
            direction = dir;
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            p.trackMovement(direction, true);
        }
        
    }

    class KeysReleased extends AbstractAction {

        private String direction;

        public KeysReleased(String dir){
            direction = dir;
        }
        @Override
        public void actionPerformed(ActionEvent ae) {
            p.trackMovement(direction, false);
        }

    }

    class ConfigureWeapon implements MouseListener, MouseMotionListener{

        private CharacterType ct;

        public ConfigureWeapon(){
            ct = p.getCharacterType();
        }

        @Override
        public void mouseClicked(MouseEvent me) {
            ct.attack();
        }

        @Override
        public void mousePressed(MouseEvent me) {

        }

        @Override
        public void mouseReleased(MouseEvent me) {
            
        }

        @Override
        public void mouseEntered(MouseEvent me) {

        }

        @Override
        public void mouseExited(MouseEvent me) {
            
        }

        @Override
        public void mouseDragged(MouseEvent me) {
            ct.changeRotation(me.getY(),me.getX());
        }

        @Override
        public void mouseMoved(MouseEvent me) {
            ct.changeRotation(me.getY(),me.getX());
        }

    }

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

    class ReadFromServer implements Runnable {   

        private DataInputStream dataIn;

        public ReadFromServer(DataInputStream in){
            dataIn = in;
            System.out.println("RFS initialized");
        }

        @Override
        public void run(){
            try {
                while (true){

                    String playerName = dataIn.readUTF();
                    double playerX = dataIn.readDouble();
                    double playerY = dataIn.readDouble();
                    double weaponRotation = dataIn.readDouble();

                    int projectileCount = dataIn.readInt();

                    ArrayList<Double> projectileX = new ArrayList<>();
                    ArrayList<Double> projectileY = new ArrayList<>();

                    for (int i = 0; i < projectileCount ; i++){
                        projectileX.add(dataIn.readDouble());
                        projectileY.add(dataIn.readDouble());
                    }  

                   
                    
                    if(p2 != null){
                        p2.setName(playerName);
                        p2.setX(playerX);
                        p2.setY(playerY);
                        p2.getCharacterType().setRotation(weaponRotation);
                        p2.getCharacterType().getProjectiles().clear();

                        // a getter method to determine what type of projectile is being sent
                        CharacterType ct = p.getCharacterType();
                        
                        for (int i = 0; i < projectileCount ; i++){ 
                            if (ct instanceof Ranger){
                                CharacterType.Projectiles b = ((Ranger) ct).new Bullet(projectileX.get(i), projectileY.get(i), weaponRotation);
                                p2.getCharacterType().getProjectiles().add(b);
                            } else if (ct instanceof Wizard){
    
                            }
                            
                        }

                        projectileX.clear();
                        projectileY.clear();
                        

                    }   
                }
            } catch (IOException ex) {
                System.out.println("IOEXception from RFS.run()");
            }
        }

        public void waitServer(){
            try {
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

    class WriteToServer implements Runnable {

        private DataOutputStream dataOut;

        public WriteToServer(DataOutputStream out){
            dataOut = out;
            System.out.println("WTS initialized");
        }

        @Override
        public void run(){
            try {

                while (true){
                    
                    if(p != null){
                        dataOut.writeUTF(p.getName());
                        dataOut.writeDouble(p.getX());
                        dataOut.writeDouble(p.getY());
                        dataOut.writeDouble(p.getCharacterType().getRotation());
                        dataOut.writeInt(p.getCharacterType().getProjectiles().size());
                        
                        for (int i = 0; i < p.getCharacterType().getProjectiles().size() ; i++){
                            dataOut.writeDouble(p.getCharacterType().getProjectiles().get(i).getX());
                            dataOut.writeDouble(p.getCharacterType().getProjectiles().get(i).getY());
                        }

                        dataOut.flush();

                    }

                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException ex) {
                        System.out.println("InterruptedException from WTS.run()");
                    }
                }
           
            } catch (IOException ex) {
                System.out.println("IOException from WTS.run()");
            }
        }
    }

    public void connectToServer(){
        try {
            socket = new Socket("localhost",45375);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            playerID = in.readInt();
            System.out.println("You are player # " + playerID);
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

}
