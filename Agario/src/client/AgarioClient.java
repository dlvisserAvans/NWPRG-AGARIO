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

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.*;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class AgarioClient{

    static Socket socket;
    private String hostname;
    private int port;
    private String name;
    private static Player player;
    private Boolean running = false;
    private static DataOutputStream dataOutputStream;
    private static DataInputStream dataInputStream;
    private static ObjectOutputStream objectOutputStream;
    private static ObjectInputStream objectInputStream;
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

        player = new Player(name);

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

            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());

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

            if (start.equals("start")) {
                Application.launch(GUI.class);
            }

//                new Thread(() -> {
//                    while (true) {
//                        try {
//
//
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        } catch (ClassNotFoundException e) {
//                            e.printStackTrace();
//                        } catch (ClassCastException e){
//                            System.out.println("Waarom gebeurd dit? " + e.getMessage());
//                            e.printStackTrace();
//                        }
//                    }
//                }).start();
//            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassCastException e){
            e.printStackTrace();
        }
    }

    public static ArrayList<Food> getFoods(){
        return foods;
    }

    public static ArrayList<Player> getPlayers(){
        return players;
    }

    static Socket getSocket(){
        return socket;
    }

    static Player getPlayer(){
        return player;
    }

    static DataOutputStream getDataOutputStream(){
        return dataOutputStream;
    }

    static DataInputStream getDataInputStream(){
        return dataInputStream;
    }

    static ObjectOutputStream getObjectOutputStream(){
        return objectOutputStream;
    }

    static ObjectInputStream getObjectInputStream(){
        return objectInputStream;
    }
}
