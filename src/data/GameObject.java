package data;

import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Random;

public abstract class GameObject implements Serializable {

    public GameObject(int points, int width, int height) {
        this.points = points;
        this.position = getRandomPosition();
        this.color = getRandomColor();
        this.width = width;
        this.height = height;
        this.shape = new Ellipse2D.Double(this.position.getX(),this.position.getY(),this.width,this.height);
    }

    private Color color;
    private Shape shape;
    private int points;
    private int width;
    private int height;
    private Point2D position;
    private Color[] colors = {Color.RED,Color.GREEN,Color.BLUE,Color.YELLOW,Color.CYAN,Color.MAGENTA,Color.BLACK,Color.GRAY,Color.LIGHT_GRAY,Color.DARK_GRAY,Color.ORANGE,Color.PINK};

    public void draw(FXGraphics2D graphics2D){
        graphics2D.setColor(this.color);
        graphics2D.fill(this.shape);
    }

    public Point2D getRandomPosition(){

        int randomX = (int) (Math.random() * 1000);
        int randomY = (int) (Math.random() * 1000);

        return new Point2D.Double(randomX, randomY);
    }

    public Color getRandomColor(){
        int rnd = new Random().nextInt(colors.length);
        return (Color) Array.get(colors, rnd);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
