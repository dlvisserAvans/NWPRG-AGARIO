package Data;

import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.Serializable;

public class Food implements Serializable {

    private Shape shape;
    private Point2D position;
    private Color color;
    private double gainValue;
    private double radius;
    private boolean eaten;

    public Food(Point2D position, Color color, int width, int height) {
        this.shape = new Ellipse2D.Double(position.getX(), position.getY(), width, height);
        this.position = position;
        this.color = color;
        this.gainValue = calculateGainValue();
        this.radius = 0.5 * width;
        this.eaten = false;
    }

    public void draw(FXGraphics2D graphics2D){
        graphics2D.setColor(this.color);
        graphics2D.fill(this.shape);
    }

    public AffineTransform getTransform(){
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.translate(position.getX(),position.getY());
        return affineTransform;
    }

    public double calculateGainValue(){
        return 1+1*Math.random();
    }

    public double getGainValue() {
        return this.gainValue;
    }

    public Color getColor() {
        return this.color;
    }

    public double getRadius(){
        return this.radius;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Point2D getPosition() {
        return this.position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public Shape getShape() {
        return this.shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public boolean isEaten() {
        return eaten;
    }

    public void setEaten(boolean eaten) {
        this.eaten = eaten;
    }
}
