package src;
public class obstacle {
    public enum Orientation{North, South, East, West};
    Double x;
    Double y;
    Orientation orientation;

    public obstacle(double x,double y, Orientation orientation)
    {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
    }
}
