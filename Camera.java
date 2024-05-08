public class Camera {
    
    private double x,y;
    private Player p;

    public Camera(double x, double y, Player p){
        this.x = x;
        this.y = y;
        this.p = p;
    }

    public void updatePosition(){
        x = (p.getX()+(p.getWidth()/2)) - (400);
        y = (p.getY()+(p.getHeight()/2)) - (300);
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

}


