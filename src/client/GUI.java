package client;

import data.Food;
import data.Player;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class GUI extends Application {
    private ResizableCanvas canvas;
    private GameClient gameClient;
    private ArrayList<Player> players;
    private ArrayList<Food> foods;
//    private Player selectedPlayer = null;
//    private Food selectedFood;

    private Player player;

    public GUI() {
        players = new ArrayList<>();
        foods = new ArrayList<>();
    }

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D graphics = new FXGraphics2D(canvas.getGraphicsContext2D());
        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1)
                    last = now;
                update((now - last) / 1000.0);
                last = now;
                draw(graphics);
            }
        }.start();

//        canvas.setOnKeyPressed(e -> keyPressed(e));
//        canvas.setOnKeyReleased(e -> keyReleased(e));
//        canvas.setOnMousePressed(e -> mouseClicked(e));
//        canvas.setOnMouseDragged(e -> mouseDragged(e));
        canvas.setFocusTraversable(true);

        stage.setScene(new Scene(mainPane));
        stage.setTitle("Agar.io [re-made by Dave and Jan Kees]");
        stage.show();

        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }

    @Override
    public void init() {
        System.out.println("init()");

        foods = GameClient.getFoods();
        players = GameClient.getPlayers();

    }

    private void update(double v) {
    }

    private void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int)canvas.getWidth(), (int)canvas.getHeight());

        for (Food food : this.foods){
            food.draw(graphics);
        }
        for (Player player : this.players){
            player.draw(graphics);
        }
    }

//    public void mouseClicked(MouseEvent e){
//        mousepoint = new Point2D.Double(e.getX(), e.getY());
//        System.out.println(mousepoint);
//        for (Player player : this.players){
//            if (player.getShape().contains(mousepoint)){
//                player.setPosition(mousepoint);
//                selectedPlayer = player;
//
//            }
//        }
//        for (Food food : this.foods){
//            if (food.getShape().contains(mousepoint)){
//                selectedFood = food;
//                food.setEaten(true);
//            }
//        }
//        this.foods.remove(selectedFood);
//    }

//    public void mouseDragged(MouseEvent e){
//        if(selectedPlayer != null){
//            AffineTransform affineTransform = new AffineTransform(1, 0, 0, 1,
//                    (e.getX() - selectedPlayer.getPosition().getX()) * canvas.getScaleX(),
//                    (e.getY() - selectedPlayer.getPosition().getY()) * canvas.getScaleY());
//            selectedPlayer.setPosition(new Point2D.Double(e.getX(), e.getY()));
//            selectedPlayer.setShape(affineTransform.createTransformedShape(selectedPlayer.getShape()));
//            draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
//            System.out.println(selectedPlayer.getPosition());
//        }
//    }

//    public void keyPressed(KeyEvent e){
//        System.out.println("public void keyPressed");
//        switch (e.getCode()){
//            case LEFT:
//                System.out.println("Pressed " + e.getCode());
//                selectedPlayer.setPosition(new Point2D.Double(selectedPlayer.getPosition().getX() - 10, selectedPlayer.getPosition().getY()));
//                break;
//            case RIGHT:
//                System.out.println("Pressed " + e.getCode());
//                selectedPlayer.setPosition(new Point2D.Double(selectedPlayer.getPosition().getX() + 10, selectedPlayer.getPosition().getY()));
//                break;
//            case UP:
//                System.out.println("Pressed " + e.getCode());
//                selectedPlayer.setPosition(new Point2D.Double(selectedPlayer.getPosition().getX(), selectedPlayer.getPosition().getY() - 10));
//                break;
//            case DOWN:
//                System.out.println("Pressed " + e.getCode());
//                selectedPlayer.setPosition(new Point2D.Double(selectedPlayer.getPosition().getX(), selectedPlayer.getPosition().getY() + 10));
//                break;
//            case W:
//                System.out.println("Pressed " + e.getCode());
//                break;
//            case A:
//                System.out.println("Pressed " + e.getCode());
//                break;
//            case S:
//                System.out.println("Pressed " + e.getCode());
//                break;
//            case D:
//                System.out.println("Pressed " + e.getCode());
//                break;
//        }
//    }

//    private void keyReleased(KeyEvent e) {
//        System.out.println("public void keyReleased");
//        switch (e.getCode()){
//            case LEFT:
//                System.out.println("Released " + e.getCode());
//                break;
//            case RIGHT:
//                System.out.println("Released " + e.getCode());
//                break;
//            case UP:
//                System.out.println("Released " + e.getCode());
//                break;
//            case DOWN:
//                System.out.println("Released " + e.getCode());
//                break;
//            case W:
//                System.out.println("Released " + e.getCode());
//                break;
//            case A:
//                System.out.println("Released " + e.getCode());
//                break;
//            case S:
//                System.out.println("Released " + e.getCode());
//                break;
//            case D:
//                System.out.println("Released " + e.getCode());
//                break;
//        }
//    }
}
