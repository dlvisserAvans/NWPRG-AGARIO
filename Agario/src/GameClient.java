import java.awt.*;
import java.awt.geom.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class GameClient extends Application {
    ResizableCanvas canvas;
    ArrayList<Food> foods = new ArrayList<>();
    ArrayList<Player> players = new ArrayList<>();
    int screenx = 2560;
    int screeny = 1080;
    int foodamount = 128;
    Color[] colors = {Color.RED,Color.GREEN,Color.BLUE,Color.YELLOW,Color.CYAN,Color.MAGENTA,Color.BLACK,Color.GRAY,Color.LIGHT_GRAY,Color.DARK_GRAY,Color.ORANGE,Color.PINK};
    private Point2D mousepoint;
    private Player selectedShape = null;
    private Food touchedFood = null;
    private double speed = 0;
    private FXGraphics2D graphics;

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
        if (foods.size() < foodamount){
            int randomX = (int)(Math.random() * screenx);
            int randomY = (int)(Math.random() * screeny);
            this.foods.add(new Food(new Point2D.Double(randomX, randomY), getRandomColor(), 20, 20));

        }
        for (Food food : this.foods){
            for (Player player : this.players){
                double distance = food.getRadius() + player.getRadius();
                if (player.getPosition().distance(food.getPosition()) < distance){
                    touchedFood = food;
                    player.growSize();
                    player.draw(this.graphics);

                }
            }

        }
        this.foods.remove(touchedFood);

    }

    public void init(){
        for (int i =0 ; i < foodamount; i++){
            int randomX = (int)(Math.random() * screenx);
            int randomY = (int)(Math.random() * screeny);
            Color color = (Color) Array.get(colors,i%12);
            this.foods.add(new Food(new Point2D.Double(randomX, randomY), getRandomColor(), 20, 20));
        }

        int randomX = (int)(Math.random() * screenx);
        int randomY = (int)(Math.random() * screeny);
        Player player = new Player(new Point2D.Double(40, 40), getRandomColor(), 40, 40, "Jan Kees");
        this.players.add(player);
    }

    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int)canvas.getWidth(), (int)canvas.getHeight());

        for (Food food : foods){
            food.draw(graphics);
        }
        for (Player player : this.players){
            player.draw(graphics);
        }
    }

    public Color getRandomColor(){
        int rnd = new Random().nextInt(colors.length);
        return (Color)Array.get(colors, rnd);
    }

    public void mouseClicked(MouseEvent e){
        mousepoint = new Point2D.Double(e.getX(), e.getY());
        System.out.println(mousepoint);
        for (Player player : this.players){
            if (player.getShape().contains(mousepoint)){
                player.setPosition(mousepoint);
                selectedShape = player;

            }
        }
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

    public static void main(String[] args) {
        launch(GameClient.class);
    }
}
