package client;

import Data.Food;
import Data.Player;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.*;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class AgarioClient {

    private Socket socket;
    private String hostname;
    private int port;
    private String name;
    private static Player player;
    private Boolean running = false;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private FXGraphics2D graphics;
    private ResizableCanvas canvas;
    private static ArrayList<Food> foods = new ArrayList<>();
    private static ArrayList<Player> players = new ArrayList<>();
    private static Food selectedFood;
    private static Point2D mousepoint;


    public static void main(String[] args) {
        AgarioClient agarioClient = new AgarioClient("localhost", 10000, "Someone");
        agarioClient.connect();
    }

    public AgarioClient(String hostname, int port, String name) {
        this.hostname = hostname;
        this.port = port;
        this.name = name;
        this.player = new Player(name);
        dataInputStream = null;
        dataOutputStream = null;
        objectInputStream = null;
        objectOutputStream = null;
        selectedFood = null;
        mousepoint = null;
    }

    public void connect() {
        Scanner scanner = new Scanner(System.in);

        try {
            this.socket = new Socket(this.hostname, this.port);

            dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
            objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
            dataInputStream = new DataInputStream(this.socket.getInputStream());
            objectInputStream = new ObjectInputStream(this.socket.getInputStream());

            //Step 1: Get/Set player name.
            System.out.print("What is your username: ");
            name = scanner.nextLine();
            dataOutputStream.writeUTF(name);
            objectOutputStream.writeObject(new Player(name));
            this.player.setName(name);

            //Step 2: get the food from the server.
            System.out.print("Enter 'start' to start the game: ");
            String start = scanner.nextLine();
            dataOutputStream.writeUTF(start);
            running = true;

            if (start.equals("start")){

                new Thread(() -> {
                    while (true) {
                        try {
                            foods = (ArrayList<Food>) objectInputStream.readObject();
                            players = (ArrayList<Player>) objectInputStream.readObject();
                            objectOutputStream.writeObject(this.player);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (ClassCastException e){
                            System.out.println("Waarom gebeurd dit? " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }).start();
                new Thread(() -> {
                    while(true){
                        Application.launch(GUI.class);
                    }

                }).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassCastException e){
            e.printStackTrace();
        }
    }
    public static void update(Point2D position){
        player.setPosition(position);
    }

    public static ArrayList<Food> getFoods(){
        return foods;
    }

    public static ArrayList<Player> getPlayers(){
        return players;
    }
}
