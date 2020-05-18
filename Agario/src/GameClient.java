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
    int screenx = 2560;
    int screeny = 1080;
    int foodamount = 128;
    Color[] colors = {Color.RED,Color.GREEN,Color.BLUE,Color.YELLOW,Color.CYAN,Color.MAGENTA,Color.BLACK,Color.GRAY,Color.LIGHT_GRAY,Color.DARK_GRAY,Color.ORANGE,Color.PINK};
    private Point2D mousepoint;
    private Food selectedShape = null;

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();


        canvas.setOnKeyPressed(e -> keyPressed(e));
        canvas.setOnKeyReleased(e -> keyReleased(e));
        canvas.setOnMouseClicked(e -> mouseClicked(e));

        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Agar.io [re-made by Dave and Jan Kees]");
        primaryStage.show();

        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }

    private void update(double v) {
        if (foods.size() < foodamount){
            int randomX = (int)(Math.random() * screenx);
            int randomY = (int)(Math.random() * screeny);
            this.foods.add(new Food(new Ellipse2D.Double(randomX, randomY, 20, 20), new Point2D.Double(randomX, randomY), getRandomColor()));

        }
    }

    public void init(){
        for (int i =0 ; i < foodamount; i++){
            int randomX = (int)(Math.random() * screenx);
            int randomY = (int)(Math.random() * screeny);
            Color color = (Color) Array.get(colors,i%12);
            this.foods.add(new Food(new Ellipse2D.Double(randomX, randomY, 20, 20), new Point2D.Double(randomX, randomY), color));

        }
    }

    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int)canvas.getWidth(), (int)canvas.getHeight());

        for (Food food : foods){
            food.draw(graphics);
        }
    }

    public Color getRandomColor(){
        int rnd = new Random().nextInt(colors.length);
        return (Color)Array.get(colors, rnd);
    }

    public void mouseClicked(MouseEvent e){
//        int i = 0;
//        while (i < 10) {
//            int rnd = new Random().nextInt(foods.size());
//            i++;
//            foods.remove(rnd);
//        }
        mousepoint = new Point2D.Double(e.getX(), e.getY());
        System.out.println(mousepoint);
        for (Food food : this.foods){
            if (food.getShape().contains(mousepoint)){
                System.out.println("Raak");
                selectedShape = food;

            }
        }
        this.foods.remove(selectedShape);
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
