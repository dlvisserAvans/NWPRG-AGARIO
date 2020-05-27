package server;

import Data.Food;
import Data.Player;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.*;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class AgarioServer {

    private final int port;
    private ServerSocket serverSocket;
    private Thread serverThread;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    ArrayList<Food> foods = new ArrayList<>();
    ArrayList<Player> players = new ArrayList<>();
    int foodamount = 128;
    int screenx = 3000;
    int screeny = 3000;
    Color[] colors = {Color.RED,Color.GREEN,Color.BLUE,Color.YELLOW,Color.CYAN,Color.MAGENTA,Color.BLACK,Color.GRAY,Color.LIGHT_GRAY,Color.DARK_GRAY,Color.ORANGE,Color.PINK};

    public AgarioServer() {
        this.port = 10000;
        dataInputStream = null;
        dataOutputStream = null;
        objectInputStream = null;
        objectOutputStream = null;
    }

    public static void main(String[] args) {
        System.out.println("Server starting.....");
        AgarioServer server = new AgarioServer();
        server.connect();
    }

    public void connect(){
        try {
            this.serverSocket = new ServerSocket(port);
            System.out.println("Agar.io server 1.0 is starting...");
            boolean isRunning = true;
            initializeGame();

            while (isRunning){
                System.out.println("Waiting for users to connect....");
                Socket socket = this.serverSocket.accept();

                System.out.println("AgarioClient connected via address: " + socket.getInetAddress().getHostAddress());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                dataInputStream = new DataInputStream(socket.getInputStream());
                objectInputStream = new ObjectInputStream(socket.getInputStream());

                String test = dataInputStream.readUTF();
                System.out.println("Name :" + test);

                Player player = (Player) objectInputStream.readObject();
                players.add(player);

                System.out.println(player.getName());
                System.out.println(player.getPosition());
            }
            this.serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void initializeGame(){
        for (int i =0 ; i < foodamount; i++){
            int randomX = (int)(Math.random() * screenx);
            int randomY = (int)(Math.random() * screeny);
            Color color = (Color) Array.get(colors,i%12);
            this.foods.add(new Food(new Point2D.Double(randomX, randomY), getRandomColor(), 20, 20));
        }
    }

    public Color getRandomColor(){
        int rnd = new Random().nextInt(colors.length);
        return (Color)Array.get(colors, rnd);
    }

    public void update(){
        if (foods.size() < foodamount){
            int randomX = (int)(Math.random() * screenx);
            int randomY = (int)(Math.random() * screeny);
            this.foods.add(new Food(new Point2D.Double(randomX, randomY), getRandomColor(), 20, 20));
        }
    }

//    public void sentToAllClients() {
//        for ()
//    }
}
