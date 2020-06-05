package client;

import data.Food;
import data.Player;
import javafx.application.Application;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class GameClient {

    private Socket socket;
    private String host;
    private int port;
    private String name;
    private static ArrayList<Food> foods;
    private static ArrayList<Player> players;

    public GameClient( String host, int port ) {
        this.host = host;
        this.port = port;
    }

    public boolean connect () {
        try {
            this.socket = new Socket(this.host, this.port);

            DataInputStream in = new DataInputStream( this.socket.getInputStream() );
            DataOutputStream out = new DataOutputStream( this.socket.getOutputStream() );
            ObjectInputStream objectInputStream = new ObjectInputStream(this.socket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());

            Scanner scanner = new Scanner( System.in );

            //Step 1: Get and print server details.
            String server = in.readUTF();
            System.out.println(server);

            //Step 2: Send name and create new player object to the server.
            System.out.println("What is your name: ");
            this.name = scanner.nextLine();
            out.writeUTF(this.name);
            objectOutputStream.writeObject(new Player(this.name));

            players = (ArrayList<Player>) objectInputStream.readObject();
            foods = (ArrayList<Food>) objectInputStream.readObject();

            Application.launch(GUI.class);


//            new Thread ( () -> {
//                while ( true ) {
//                    try {
//                        synchronized (objectInputStream) {
//
//                        }
//
//                    } catch (IOException | ClassNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();

//            String message = "";
//            while ( !message.equals("stop" ) ) {
//                System.out.print("> ");
//                message = scanner.nextLine();
//                out.writeUTF(message);
//
//                //System.out.println("avans.ti.chat.server.ChatServer response: " + in.readUTF());
//            }

//            this.socket.close();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Could not connect with the server on " + this.host + " with port " + this.port + ": " + e.getMessage());
            return false;
        }

        return true;
    }

    public void createNewPlayer(String name){
        
    }

    public static ArrayList<Food> getFoods() {
        return foods;
    }

    public void setFoods(ArrayList<Food> foods) {
        this.foods = foods;
    }

    public static ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
}
