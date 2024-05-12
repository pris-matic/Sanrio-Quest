import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
The GameServer class is responsible for server-sided operations.
It handles the connections of the players, as well as sending
out the information on the current gamestate.

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

public class GameServer {

    private ServerSocket ss;
    private int playerCount;
    private int maxPlayerCount;
    
    private Socket p1Socket;
    private Socket p2Socket;
    
    private ReadFromClient rfc1;
    private ReadFromClient rfc2;

    private WriteToClient wtc1;
    private WriteToClient wtc2;

    private String p1Name,p2Name,p1CharacterType,p2CharacterType;
    private double p1x,p1y,p2x,p2y,p1rotation,p2rotation,p1Health,p2Health;
    private int p1CurrentImage,p2CurrentImage;

    // contents of the arraylist sent as primitive types
    private int p1ProjectileCount,p2ProjectileCount;
    private ArrayList<Double> p1ProjectileX, p1ProjectileY, p2ProjectileX, p2ProjectileY;
    
    /** 
        Sets up a GameServer instance, with initial values for the players.
        A maximum of two players can join the server, once one disconnects, the server must be restarted.
        <p></p> ArrayLists that will temporarily contain the projectiles received and will also
        be sent out are also created.
    **/
    public GameServer(){

        playerCount = 0;
        maxPlayerCount = 2;

        p1x = 200;
        p1y = 200;

        p2x = 400;
        p2y = 200;

        p1ProjectileX = new ArrayList<>();
        p1ProjectileY = new ArrayList<>();

        p2ProjectileX = new ArrayList<>();
        p2ProjectileY = new ArrayList<>();

        try {
            ss = new ServerSocket(45375);
        } catch (IOException ex) {
            System.out.println("IOException from GameServer");
        }
        
    }

    /**
        Allows players to connect to the server. The threads for sending and
        receiving information from the client/s will also be instantiated.
        <p></p> When the max player count is achieved, it will not allow
        any other connection.
        @see GameServer.ReadFromClient
        @see GameServer.WriteToClient
    **/
    public void allowConnection(){

        try {
            System.out.println("Waiting for players to Connect...");
            while (playerCount < maxPlayerCount) {
                // checks for connections 
                Socket s = ss.accept();
                DataInputStream in = new DataInputStream(s.getInputStream());
                DataOutputStream out = new DataOutputStream(s.getOutputStream());

                playerCount ++;
                out.writeInt(playerCount); // sends integer or playerID to client
                System.out.println("Player # " + playerCount + " has connected.");
                
                ReadFromClient rfc = new ReadFromClient(playerCount, in);
                WriteToClient wtc = new WriteToClient(playerCount, out);

                if (playerCount == 1){
                    p1Socket = s;
                    rfc.receiveInitialValues();
                    rfc1 = rfc;
                    wtc1 = wtc;

                } else {
                    p2Socket = s;
                    rfc.receiveInitialValues();
                    rfc2 = rfc;
                    wtc2 = wtc;
                    
                    wtc1.startGame();
                    wtc2.startGame();

                    Thread readThread1 = new Thread(rfc1);
                    Thread readThread2 = new Thread(rfc2);
                    readThread1.start();
                    readThread2.start();

                    Thread writeThread1 = new Thread(wtc1);
                    Thread writeThread2 = new Thread(wtc2);
                    writeThread1.start();
                    writeThread2.start();
                }

            }
            System.out.println("No longer accepting players.");

        } catch (IOException ex) {
            System.out.println("IOException from allowConnection()");
        }
    }

    /**
        The ReadFromClient class is responsible for receiving information from the client.
        It stores the information from each player, and will be sent out again afterward.

        @author Anthony B. Deocadiz Jr. (232166)
        @author Ramona Miekaela S. Laciste (233403)
        @version March 16, 2024
    **/
    class ReadFromClient implements Runnable{
        
        private int playerID;
        private DataInputStream dataIn;

        /**
            Constructs a new instance of the ReadFromClient class that
            asks for the player's ID.
            @param pid is the player ID
            @param in is the DataInputStream that will receive the data.
        **/
        public ReadFromClient(int pid, DataInputStream in){
            playerID = pid;
            dataIn = in;
            System.out.println("RFC"  + playerID + " created.");
        }

        /**
            Continuously stores information from the client.
            The information is stored depending on the PlayerID of the client.
        **/
        @Override
        public void run(){
            try {
                while(true){
                    if(playerID == 1){
                        p1Name = dataIn.readUTF();
                        p1x = dataIn.readDouble();
                        p1y = dataIn.readDouble();
                        p1rotation = dataIn.readDouble();

                        p1ProjectileCount = dataIn.readInt();
                        ArrayList<Double> tempX = new ArrayList<>();
                        ArrayList<Double> tempY = new ArrayList<>();

                        for (int i = 0; i < p1ProjectileCount ; i++){
                            tempX.add(dataIn.readDouble());
                            tempY.add(dataIn.readDouble());
                        }

                        p1CurrentImage = dataIn.readInt();
                        p1Health = dataIn.readDouble();

                        p1ProjectileX = tempX;
                        p1ProjectileY = tempY;
   
                    } else {
                        p2Name = dataIn.readUTF();
                        p2x = dataIn.readDouble();
                        p2y = dataIn.readDouble();
                        p2rotation = dataIn.readDouble();
                        
                        p2ProjectileCount = dataIn.readInt();

                        ArrayList<Double> tempX = new ArrayList<>();
                        ArrayList<Double> tempY = new ArrayList<>();

                        for (int i = 0; i < p2ProjectileCount ; i++){
                            tempX.add(dataIn.readDouble());
                            tempY.add(dataIn.readDouble());
                        }

                        p2CurrentImage = dataIn.readInt();
                        p2Health = dataIn.readDouble();

                        p2ProjectileX = tempX;
                        p2ProjectileY = tempY;

                    }
                }
            } catch (IOException ex) {
                System.out.println("IOException at RFC.run()");
            }
        }

        /**
            Gets the Initial values from the players through the waitServer() method
            it is used to initialize the GameFrame for both players.
            @see GameFrame#createPlayers()
            @see GameFrame.ReadFromServer#waitServer()
        **/
        public void receiveInitialValues(){
            try {
                if (playerID == 1){
                    p1Name = dataIn.readUTF();
                    p1CharacterType = dataIn.readUTF();
                } else {
                    p2Name = dataIn.readUTF();
                    p2CharacterType = dataIn.readUTF();
                }

            } catch (IOException ex) {
                System.out.println("IOException from rfc.RecieveInitialValues()");
            }
        }

    }

    /**
        The WriteToClient class is responsible for sending information to the client.
        The stored the information from each player will be sent to the other one.

        @author Anthony B. Deocadiz Jr. (232166)
        @author Ramona Miekaela S. Laciste (233403)
        @version March 16, 2024
    **/
    class WriteToClient implements Runnable{

        private int playerID;
        private DataOutputStream dataOut;

        /**
            Instantiates a WriteToClient object that will send out information
            to the players.
            @param pid is the player's ID
            @param out is the DataOutputStream responsible in sending out information.
        **/
        public WriteToClient(int pid, DataOutputStream out){
            playerID = pid;
            dataOut = out;
            System.out.println("WTC"  + playerID + " created.");
        }

        /**
            Continuously sends out information to the clients.
        **/
        @Override
        public void run(){
            try {
                while (true) {
                    if (playerID == 1){
                        dataOut.writeUTF(p2Name);
                        dataOut.writeDouble(p2x);
                        dataOut.writeDouble(p2y);
                        dataOut.writeDouble(p2rotation);

                        dataOut.writeInt(p2ProjectileCount);

                        for (int i = 0; i < p2ProjectileCount ; i++){
                            dataOut.writeDouble(p2ProjectileX.get(i));
                            dataOut.writeDouble(p2ProjectileY.get(i));
                        }

                        dataOut.writeInt(p2CurrentImage);
                        dataOut.writeDouble(p2Health);

                        dataOut.flush();
    
                    } else {
                        dataOut.writeUTF(p1Name);
                        dataOut.writeDouble(p1x);
                        dataOut.writeDouble(p1y);
                        dataOut.writeDouble(p1rotation);

                        dataOut.writeInt(p1ProjectileCount);

                        for (int i = 0; i < p1ProjectileCount ; i++){
                            dataOut.writeDouble(p1ProjectileX.get(i));
                            dataOut.writeDouble(p1ProjectileY.get(i));
                        }

                        dataOut.writeInt(p1CurrentImage);
                        dataOut.writeDouble(p1Health);

                        dataOut.flush();

                    }
                    try {
                        Thread.sleep(35);
                    } catch (InterruptedException ex) {
                        System.out.println("InterruptedException from WTC.run()");
                    }
                }
            } catch (IOException ex) {
                System.out.println("IOException from WTC.run()");
            }
        }

        /**
            Starts the Game for both players after the server has received the intial information.
            @see GameFrame.ReadFromServer#waitServer()
            @see GameServer.ReadFromClient#receiveInitialValues()
        **/
        public void startGame(){
            try {
                if (playerID == 1){
                    dataOut.writeUTF(p2Name);
                    dataOut.writeUTF(p2CharacterType);
                } else {
                    dataOut.writeUTF(p1Name);
                    dataOut.writeUTF(p1CharacterType);
                }
                dataOut.writeUTF("All players have connected. Starting Game.");
                dataOut.flush();

            } catch (IOException e) {
                System.out.println("IOException at WTC.startGame()");
            }
        }
    }

    public static void main(String[] args) {
        GameServer gs = new GameServer();
        gs.allowConnection();
    }
    
}
