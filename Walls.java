import java.awt.*;
import java.awt.geom.*;

public class Walls implements BackgroundManager {
    
    private double x,y, width, height;
    private Color color;

    public Walls(double x, double y, double width, double height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        color = Color.GRAY;
    }

    @Override
    public void draw (Graphics2D g2d){
        
        g2d.setColor(color);
        Rectangle2D.Double wall = new Rectangle2D.Double(x,y,width,height);
        g2d.fill(wall);

    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public double getWidth(){
        return width;
    }

    public double getHeight(){
        return height;
    }

}
