package server;

import data.Food;
import data.GameObject;
import data.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private int port;
    private ServerSocket serverSocket;
    private Thread serverThread;
    private ArrayList<ServerClient> serverClients;
    private ArrayList<Thread> threads;
    private ArrayList<Player> players;
    private ArrayList<Food> foods;

    public Server(int port) {
        this.port = port;
        this.serverClients = new ArrayList<>();
        this.threads = new ArrayList<>();
        this.players = new ArrayList<>();
        this.foods = new ArrayList<>();
    }

    public boolean start(){
        try {
            this.serverSocket = new ServerSocket(port);

            this.serverThread = new Thread ( () -> {
                while ( true ) {
                    System.out.println("Waiting for clients to connect.");
                    try {
                        Socket socket = this.serverSocket.accept();
                        System.out.println("avans.ti.chat.client.Client connected from " + socket.getInetAddress().getHostAddress() + ".");

                        ServerClient client = new ServerClient(socket, this);
                        Thread threadClient = new Thread(client);
                        threadClient.start();
                        this.serverClients.add(client);
                        this.threads.add(threadClient);

                        System.out.println("Total clients connected: " + this.serverClients.size());

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
            System.out.println("avans.ti.chat.server.ChatServer is started and listening on port " + this.port);

        } catch (IOException e) {
            System.out.println("Could not connect: " + e.getMessage());
            return false;
        }

        return true;
    }

    public void updateAllClients(){
        for (ServerClient serverClient : serverClients){
            serverClient.sendObject(this.players);
            serverClient.sendObject(this.foods);
        }
    }

}

