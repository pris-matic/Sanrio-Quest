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

    private String p1Name,p2Name;
    private double p1x,p1y,p2x,p2y,p1rotation,p2rotation;

    private int p1ProjectileCount, p2ProjectileCount;
    private ArrayList<Double> p1ProjectileX,p1ProjectileY,p2ProjectileX,p2ProjectileY;

    public GameServer(){

        playerCount = 0;
        maxPlayerCount = 2;

        p1Name = "prism";
        p2Name = "test";

        p1x = 200;
        p1y = 200;
        p1rotation = 0;
        p1ProjectileX = new ArrayList<>();
        p1ProjectileY = new ArrayList<>();

        p2x = 400;
        p2y = 200;
        p2rotation = 0;
        p2ProjectileX = new ArrayList<>();
        p2ProjectileY = new ArrayList<>();

        try {
            ss = new ServerSocket(45375);
        } catch (IOException ex) {
            System.out.println("IOException from GameServer");
        }
    }

    public void allowConnection(){

        try {
            System.out.println("Waiting for players to Connect...");
            while (playerCount < maxPlayerCount) {
                // checks for connections 
                Socket s = ss.accept();
                DataInputStream in = new DataInputStream(s.getInputStream());
                DataOutputStream out = new DataOutputStream(s.getOutputStream());

                playerCount ++;
                out.writeInt(playerCount); // sends integer to client
                System.out.println("Player # " + playerCount + " has connected.");
                
                ReadFromClient rfc = new ReadFromClient(playerCount, in);
                WriteToClient wtc = new WriteToClient(playerCount, out);

                if (playerCount == 1){
                    p1Socket = s;
                    rfc1 = rfc;
                    wtc1 = wtc;
                } else {
                    p2Socket = s;
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

    class ReadFromClient implements Runnable{
        
        private int playerID;
        private DataInputStream dataIn;

        public ReadFromClient(int pid, DataInputStream in){
            playerID = pid;
            dataIn = in;
            System.out.println("RFC"  + playerID + " created.");
        }

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

                        for (int i = 0; i < p1ProjectileCount ; i++){
                            p1ProjectileX.add(dataIn.readDouble());
                            p1ProjectileY.add(dataIn.readDouble());
                        }
                        
                    } else {
                        p2Name = dataIn.readUTF();
                        p2x = dataIn.readDouble();
                        p2y = dataIn.readDouble();
                        p2rotation = dataIn.readDouble();
                        p2ProjectileCount = dataIn.readInt();

                        for (int i = 0; i < p2ProjectileCount ; i++){
                            p2ProjectileX.add(dataIn.readDouble());
                            p2ProjectileY.add(dataIn.readDouble());
                        }

                    }
                }
            } catch (IOException ex) {
                System.out.println("IOException at RFC.run()");
            }
        }

    }

    class WriteToClient implements Runnable{

        private int playerID;
        private DataOutputStream dataOut;

        public WriteToClient(int pid, DataOutputStream out){
            playerID = pid;
            dataOut = out;
            System.out.println("WTC"  + playerID + " created.");
        }

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
                            System.out.println("p2 projectile count: " + p2ProjectileCount);
                            dataOut.writeDouble(p2ProjectileX.get(i));
                            dataOut.writeDouble(p2ProjectileY.get(i));
                        }
          
                        p2ProjectileX.clear();
                        p2ProjectileY.clear();
                        dataOut.flush();

                    } else {
                        dataOut.writeUTF(p1Name);
                        dataOut.writeDouble(p1x);
                        dataOut.writeDouble(p1y);
                        dataOut.writeDouble(p1rotation);
                        dataOut.writeInt(p1ProjectileCount);
                        
                        for (int i = 0; i < p1ProjectileCount ; i++){
                            System.out.println("p1 projectile count: " + p1ProjectileCount);
                            dataOut.writeDouble(p1ProjectileX.get(i));
                            dataOut.writeDouble(p1ProjectileY.get(i));
                        }

                        p1ProjectileX.clear();
                        p1ProjectileY.clear();

                        dataOut.flush();
                    }

                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException ex) {
                        System.out.println("InterruptedException from WTC.run()");
                    }
                }
            } catch (IOException ex) {
                System.out.println("IOException from WTC.run()");
            }
        }

        public void startGame(){
            try {
                dataOut.writeUTF("All players have connected. Starting Game.");
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
