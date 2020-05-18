import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class Player {

    private Color color;
    private Point2D position;
    private Shape shape;
    private int height;
    private int width;
    private int valuepoints;
    private int valuepointslast;
    private double radius;
    private String name;

    public Player(Point2D position, Color color, int height, int width, String name) {
        this.color = color;
        this.position = position;
        this.shape = new Ellipse2D.Double(position.getX(), position.getY(), width, height);
        this.radius = width * 0.5;
        this.height = height;
        this.width = width;
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

    public void draw(FXGraphics2D graphics2D){
        graphics2D.setColor(this.color);
        graphics2D.fill(this.shape);
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString(this.name, (int)position.getX() + (width / 2), (int)position.getY() + (height / 2));
    }
}
