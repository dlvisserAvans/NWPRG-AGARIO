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
    private ArrayList<Client> clients;
    private ArrayList<Thread> threads;
    private ArrayList<Food> foods = new ArrayList<>();
    private ArrayList<Player> players;

    int foodamount = 128;
    int screenx = 3000;
    int screeny = 3000;
    Color[] colors = {Color.RED,Color.GREEN,Color.BLUE,Color.YELLOW,Color.CYAN,Color.MAGENTA,Color.BLACK,Color.GRAY,Color.LIGHT_GRAY,Color.DARK_GRAY,Color.ORANGE,Color.PINK};

    public AgarioServer() {
        this.port = 10000;
        this.players = new ArrayList<>();

        this.clients = new ArrayList<>();
        this.threads = new ArrayList<>();
    }

    public static void main(String[] args) {
        System.out.println("Server starting.....");
        AgarioServer server = new AgarioServer();
        server.connect();
    }

    public void connect(){
        try {
            this.serverSocket = new ServerSocket(port);

            this.serverThread = new Thread( () -> {
                System.out.println("Agar.io server 1.0 is starting...");
                boolean isRunning = true;
                initializeGame();

                while (isRunning){
                    System.out.println("Waiting for users to connect....");

                    Socket socket = null;
                    try {
                        socket = this.serverSocket.accept();
                        System.out.println("AgarioClient connected via address: " + socket.getInetAddress().getHostAddress());

                        Client client = new Client(socket, this);
                        Thread threadClient = new Thread(client);
                        threadClient.start();
                        this.clients.add(client);
                        this.threads.add(threadClient);

                        System.out.println("Total users connected: " + this.clients.size());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Thread.yield();
                }
            });
            this.serverThread.start();

        } catch (IOException e) {
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

    public void update(Player player){
        System.out.println("Name 1 : " + player.getName() + " - Position: " + player.getPosition());
        for (Player p : this.players){
            if (p.getName().equals(player.getName())){
                p.setPosition(player.getPosition());
                System.out.println("Name 2 : " + p.getName() + " - Position: " + p.getPosition());
            }
        }
//        if (foods.size() < foodamount){
//            int randomX = (int)(Math.random() * screenx);
//            int randomY = (int)(Math.random() * screeny);
//            this.foods.add(new Food(new Point2D.Double(randomX, randomY), getRandomColor(), 20, 20));
//        }
    }

    public void addPlayer(Player player){
        this.players.add(player);
    }

    public void sentFoodToAllClients(){
        for (Client client : this.clients){
            client.writeObject(this.foods);
        }
    }

    public void sentPlayersToAllClients(){
        for (Client client : this.clients){
            client.writeObject(this.players);
        }
    }


}
