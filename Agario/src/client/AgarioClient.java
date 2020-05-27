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

public class AgarioClient extends Application {

    private Socket socket;
    private String hostname;
    private int port;
    private String name;
    private Player player;
    private Boolean running = true;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private FXGraphics2D graphics;
    private ResizableCanvas canvas;
    private ArrayList<Food> foods = new ArrayList<>();


    public static void main(String[] args) {
//        AgarioClient agarioClient = new AgarioClient("localhost", 10000, "Someone");
//        agarioClient.connect();
        launch(AgarioClient.class);
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

            //Step 2: get the food from the server.
//            while (running) {
                new Thread(() -> {
                    while (true) {
                        try {
                            this.foods = (ArrayList<Food>) objectInputStream.readObject();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        for (Food food : foods) {
                            System.out.println(food.getPosition());
                        }
                    }
                }).start();
//            }
//            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void init(){
//    }
//
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        BorderPane mainPane = new BorderPane();
//        canvas = new ResizableCanvas(g -> draw(g), mainPane);
//        mainPane.setCenter(canvas);
//        graphics = new FXGraphics2D(canvas.getGraphicsContext2D());
//        new AnimationTimer() {
//            long last = -1;
//
//            @Override
//            public void handle(long now) {
//                if (last == -1)
//                    last = now;
//                update((now - last) / 1000000000.0);
//                last = now;
//                draw(graphics);
//            }
//        }.start();
//
////        canvas.setOnKeyPressed(e -> keyPressed(e));
////        canvas.setOnKeyReleased(e -> keyReleased(e));
////        canvas.setOnMousePressed(e -> mouseClicked(e));
////        canvas.setOnMouseDragged(e -> mouseDragged(e));
////        canvas.setFocusTraversable(true);
//
//        primaryStage.setScene(new Scene(mainPane));
//        primaryStage.setTitle("Agar.io [re-made by Dave and Jan Kees]");
//        primaryStage.show();
//
//        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
//    }
//
//    private void update(double v){
//
//    }
//
//    public void draw(FXGraphics2D graphics) {
//        graphics.setTransform(new AffineTransform());
//        graphics.setBackground(Color.white);
//        graphics.clearRect(0, 0, (int)canvas.getWidth(), (int)canvas.getHeight());
//
//        for (Food food : foods){
//            food.draw(graphics);
//        }
////        for (Player player : this.players){
////            player.draw(graphics);
////        }
//    }




















//    ResizableCanvas canvas;
//    ArrayList<Player> players = new ArrayList<>();
//    ArrayList<Food> foods = new ArrayList<>();
    Color[] colors = {Color.RED,Color.GREEN,Color.BLUE,Color.YELLOW,Color.CYAN,Color.MAGENTA,Color.BLACK,Color.GRAY,Color.LIGHT_GRAY,Color.DARK_GRAY,Color.ORANGE,Color.PINK};
    private Point2D mousepoint;
    private Player selectedShape = null;
    private Food touchedFood = null;
    private double speed = 0;
    int screenx = 1920;
    int screeny = 1080;
//    private FXGraphics2D graphics;

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        graphics = new FXGraphics2D(canvas.getGraphicsContext2D());
        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                last = now;
                draw(graphics);
            }
        }.start();

        canvas.setOnKeyPressed(e -> keyPressed(e));
        canvas.setOnKeyReleased(e -> keyReleased(e));
        canvas.setOnMousePressed(e -> mouseClicked(e));
        canvas.setOnMouseDragged(e -> mouseDragged(e));
        canvas.setFocusTraversable(true);

        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Agar.io [re-made by Dave and Jan Kees]");
        primaryStage.show();

        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }

    private void update(double v) {
        for (Food food : this.foods){
//            for (Player player : this.players){
//                double distance = food.getRadius() + player.getRadius();
//                if (player.getPosition().distance(food.getPosition()) < distance){
//                    touchedFood = food;
//                    player.growSize();
//                    player.draw(this.graphics);
//                }
//            }
        }

        //TODO: Sent to server
        this.foods.remove(touchedFood);
    }

    public void init(){
    }

    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int)canvas.getWidth(), (int)canvas.getHeight());

        for (Food food : foods){
            food.draw(graphics);
        }
//        for (Player player : this.players){
//            player.draw(graphics);
//        }
    }

    public void mouseClicked(MouseEvent e){
        mousepoint = new Point2D.Double(e.getX(), e.getY());
        System.out.println(mousepoint);
//        for (Player player : this.players){
//            if (player.getShape().contains(mousepoint)){
//                player.setPosition(mousepoint);
//                selectedShape = player;
//
//            }
//        }
//        this.foods.remove(selectedShape);
    }

    public void mouseDragged(MouseEvent e){
        if(selectedShape != null){
            AffineTransform affineTransform = new AffineTransform(1, 0, 0, 1,
                    (e.getX() - selectedShape.getPosition().getX()) * canvas.getScaleX(),
                    (e.getY() - selectedShape.getPosition().getY()) * canvas.getScaleY());
            selectedShape.setPosition(new Point2D.Double(e.getX(), e.getY()));
            selectedShape.setShape(affineTransform.createTransformedShape(selectedShape.getShape()));
            draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        }
    }

    public Color getRandomColor(){
        int rnd = new Random().nextInt(colors.length);
        return (Color) Array.get(colors, rnd);
    }

    public void keyPressed(KeyEvent e){
        System.out.println("public void keyPressed");
        switch (e.getCode()){
            case LEFT:
                System.out.println("Pressed " + e.getCode());
                break;
            case RIGHT:
                System.out.println("Pressed " + e.getCode());
                break;
            case UP:
                System.out.println("Pressed " + e.getCode());
                break;
            case DOWN:
                System.out.println("Pressed " + e.getCode());
                break;
            case W:
                System.out.println("Pressed " + e.getCode());
                break;
            case A:
                System.out.println("Pressed " + e.getCode());
                break;
            case S:
                System.out.println("Pressed " + e.getCode());
                break;
            case D:
                System.out.println("Pressed " + e.getCode());
                break;
        }
    }

    private void keyReleased(KeyEvent e) {
        System.out.println("public void keyReleased");
        switch (e.getCode()){
            case LEFT:
                System.out.println("Released " + e.getCode());
                break;
            case RIGHT:
                System.out.println("Released " + e.getCode());
                break;
            case UP:
                System.out.println("Released " + e.getCode());
                break;
            case DOWN:
                System.out.println("Released " + e.getCode());
                break;
            case W:
                System.out.println("Released " + e.getCode());
                break;
            case A:
                System.out.println("Released " + e.getCode());
                break;
            case S:
                System.out.println("Released " + e.getCode());
                break;
            case D:
                System.out.println("Released " + e.getCode());
                break;
        }
    }

}
