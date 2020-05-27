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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class GUI extends Application {
    ResizableCanvas canvas;
    ArrayList<Player> players;
    ArrayList<Food> foods;
    Color[] colors = {Color.RED,Color.GREEN,Color.BLUE,Color.YELLOW,Color.CYAN,Color.MAGENTA,Color.BLACK,Color.GRAY,Color.LIGHT_GRAY,Color.DARK_GRAY,Color.ORANGE,Color.PINK};
    private Point2D mousepoint;
    private Player selectedPlayer = null;
    private Food selectedFood;
    private FXGraphics2D graphics;

    public GUI(){
        this.players = new ArrayList<>();
        this.foods = new ArrayList<>();
    }

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
        this.players = AgarioClient.getPlayers();
        this.foods = AgarioClient.getFoods();
        AgarioClient.update(selectedPlayer.getPosition());
    }

    @Override
    public void init() {
        System.out.println("init()");
        for (Food food : AgarioClient.getFoods()){
            this.foods.add(food);
        }
        for (Player player : AgarioClient.getPlayers()){
            this.players.add(player);
        }
        selectedPlayer = this.players.get(0);
    }

    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int)canvas.getWidth(), (int)canvas.getHeight());

        for (Food food : AgarioClient.getFoods()){
            food.draw(graphics);
        }
        for (Player player : AgarioClient.getPlayers()){
            player.draw(graphics);
        }
    }

    public void mouseClicked(MouseEvent e){
        mousepoint = new Point2D.Double(e.getX(), e.getY());
        System.out.println(mousepoint);
        for (Player player : this.players){
            if (player.getShape().contains(mousepoint)){
                player.setPosition(mousepoint);
                selectedPlayer = player;

            }
        }
        for (Food food : this.foods){
            if (food.getShape().contains(mousepoint)){
                selectedFood = food;
                food.setEaten(true);
            }
        }
        this.foods.remove(selectedFood);
    }

    public void mouseDragged(MouseEvent e){
        if(selectedPlayer != null){
            AffineTransform affineTransform = new AffineTransform(1, 0, 0, 1,
                    (e.getX() - selectedPlayer.getPosition().getX()) * canvas.getScaleX(),
                    (e.getY() - selectedPlayer.getPosition().getY()) * canvas.getScaleY());
            selectedPlayer.setPosition(new Point2D.Double(e.getX(), e.getY()));
            selectedPlayer.setShape(affineTransform.createTransformedShape(selectedPlayer.getShape()));
            draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
            System.out.println(selectedPlayer.getPosition());
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
                selectedPlayer.setPosition(new Point2D.Double(selectedPlayer.getPosition().getX() - 5, selectedPlayer.getPosition().getY()));
                break;
            case RIGHT:
                System.out.println("Pressed " + e.getCode());
                selectedPlayer.setPosition(new Point2D.Double(selectedPlayer.getPosition().getX() + 5, selectedPlayer.getPosition().getY()));
                break;
            case UP:
                System.out.println("Pressed " + e.getCode());
                selectedPlayer.setPosition(new Point2D.Double(selectedPlayer.getPosition().getX(), selectedPlayer.getPosition().getY() - 5));
                break;
            case DOWN:
                System.out.println("Pressed " + e.getCode());
                selectedPlayer.setPosition(new Point2D.Double(selectedPlayer.getPosition().getX(), selectedPlayer.getPosition().getY() + 5));
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
