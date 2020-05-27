package Data;

import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Random;

public class Player implements Serializable {

    private Color color;
    private Point2D position;
    private Shape shape;
    private int height;
    private int width;
    private int valuepoints;
    private int valuepointslast;
    private double radius;
    private String name;
    Color[] colors = {Color.RED,Color.GREEN,Color.BLUE,Color.YELLOW,Color.CYAN,Color.MAGENTA,Color.BLACK,Color.GRAY,Color.LIGHT_GRAY,Color.DARK_GRAY,Color.ORANGE,Color.PINK};


    public Player(String name) {
        this.color = getRandomColor();
        this.position = getRandomPoint();
        this.shape = new Ellipse2D.Double(position.getX(), position.getY(), width, height);
        this.radius = width * 0.5;
        this.height = 40;
        this.width = 40;
        this.name = name;
    }

    public void growSize(){

        this.width += 3;
        this.height += 3;
        this.shape = new Ellipse2D.Double(position.getX(), position.getY(), this.width, this.height);
        this.radius = this.width * 0.5;

    }

    public int getValuepoints() {
        return valuepoints;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public double getRadius(){
        return this.radius;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void draw(FXGraphics2D graphics2D){
        graphics2D.setColor(this.color);
        graphics2D.fill(this.shape);
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString(this.name, (int)position.getX() + (width / 2), (int)position.getY() + (height / 2));
    }

    public Point2D getRandomPoint() {

        int randomX = (int) (Math.random() * 3000);
        int randomY = (int) (Math.random() * 3000);

        return new Point2D.Double(randomX, randomY);
    }

    public Color getRandomColor(){
        int rnd = new Random().nextInt(colors.length);
        return (Color) Array.get(colors, rnd);
    }


//    Player player = new Player(new Point2D.Double(randomX, randomY), getRandomColor(), 40, 40, "Jan Kees");
//        this.players.add(player);
}
