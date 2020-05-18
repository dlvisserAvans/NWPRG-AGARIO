import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class Player {

    private Color color;
    private Point2D position;
    private Shape shape;
    private int width;
    private int height;
    private int valuepoints;
    private int valuepointslast;

    public Player(Color color) {
        this.color = color;
        this.position = new Point2D.Double(0,0);
        this.shape = new Ellipse2D.Double(0,0, width, height);
        this.width = 40;
        this.height = 40;
        this.valuepoints = 0;
    }

    public void growSize(){
        if (valuepointslast != this.valuepoints){
          this.width += (0.5*(valuepointslast-this.valuepoints));
          this.height += (0.5*(valuepointslast-this.valuepoints));
        }
    }

    public int getValuepoints() {
        return valuepoints;
    }
}
