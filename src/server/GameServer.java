package server;

import data.Food;
import data.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class GameServer {

    private int port;
    private ServerSocket serverSocket;
    private Thread serverThread;
    private ArrayList<GameServerClient> gameServerClients;
    private ArrayList<Thread> threads;
    private ArrayList<Player> players;
    private ArrayList<Food> foods;
    private Boolean running = true;

    public GameServer(int port) {
        this.port = port;
        this.gameServerClients = new ArrayList<>();
        this.threads = new ArrayList<>();
        this.players = new ArrayList<>();
        this.foods = new ArrayList<>();
    }

    public boolean start(){
        try {
            this.serverSocket = new ServerSocket(port);

            for (int i = 0; i < 128 ; i++){
                foods.add(new Food());
            }


            this.serverThread = new Thread ( () -> {
                while ( running ) {
                    System.out.println("Waiting for clients to connect.");
                    try {
                        Socket socket = this.serverSocket.accept();
                        System.out.println("Client connected from " + socket.getInetAddress().getHostAddress() + ".");

                        GameServerClient client = new GameServerClient(socket, this);
                        Thread threadClient = new Thread(client);
                        threadClient.start();
                        this.gameServerClients.add(client);
                        this.threads.add(threadClient);

                        System.out.println("Total clients connected: " + this.gameServerClients.size());

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
            System.out.println("Agar.io gameserver is listening on port: " + this.port);

        } catch (IOException e) {
            System.out.println("Could not connect: " + e.getMessage());
            return false;
        }

        return true;
    }

    public void updateAllClients(){
        for (GameServerClient gameServerClient : gameServerClients){
            gameServerClient.sendObject(this.players);
            gameServerClient.sendObject(this.foods);
        }
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<Food> getFoods() {
        return foods;
    }

    public void setFoods(ArrayList<Food> foods) {
        this.foods = foods;
    }
}

