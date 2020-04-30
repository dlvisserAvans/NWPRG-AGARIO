import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class Food{

    private Color color;
    private Point2D position;
    private Shape shape;
    private int width;
    private int height;
    private double gainValue;

    public Food(Color color, Point2D position, int width, int height) {
        this.color = color;
        this.position = position;
        this.width = width;
        this.height = height;
        this.shape = new Ellipse2D.Double(0,0,width,height);
        this.gainValue = calculateGainValue();
    }

    public void draw(FXGraphics2D graphics2D){
        graphics2D.setColor(color);
        graphics2D.fill(getTransformedShape());

    }
    public Shape getTransformedShape(){
        return getTransform().createTransformedShape(shape);
    }

    public AffineTransform getTransform(){
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.translate(position.getX(),position.getY());
        return affineTransform;
    }

    public double calculateGainValue(){
        return 1+1*Math.random();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double getGainValue() {
        return gainValue;
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

}
