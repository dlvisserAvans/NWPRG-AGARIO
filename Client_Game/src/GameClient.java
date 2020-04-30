import java.awt.*;
import java.awt.geom.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class GameClient extends Application {
    ResizableCanvas canvas;
    ArrayList<Food> foods = new ArrayList<>();
    int screenx = 2560;
    int screeny = 1440;
    int foodamount = 128;
    Color[] colors = {Color.RED,Color.GREEN,Color.BLUE,Color.YELLOW,Color.CYAN,Color.MAGENTA,Color.BLACK,Color.GRAY,Color.LIGHT_GRAY,Color.DARK_GRAY,Color.ORANGE,Color.PINK};

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

        canvas.setOnMouseClicked(mouseEvent -> {
            int i = 0;
            while (i < 10){
            int rnd = new Random().nextInt(foods.size());
            i++;
            foods.remove(rnd);
        }});

        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Agar.io [re-made by Dave and Jan Kees]");
        primaryStage.show();

        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }

    private void update(double v) {
        if (foods.size() < foodamount){
            foods.add(new Food(getRandomColor(),new Point2D.Double(Math.random()*screenx,Math.random()*screeny),20,20));
        }
    }

    public void init(){
        for (int i =0 ; i<foodamount; i++){
            Color color = (Color) Array.get(colors,i%12);
            Food food = new Food(color,new Point2D.Double(Math.random() * screenx, Math.random() * screeny),20,20);
            this.foods.add(food);

        }

        for (Food food : foods){
            System.out.println(food.getGainValue());
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
        return (Color)Array.get(colors,rnd);
    }


    public static void main(String[] args) {
        launch(GameClient.class);
    }
}
