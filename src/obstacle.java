package algo.src;
public class obstacle {
    public enum Orientation{North, South, East, West};
    float x;
    float y;
    Orientation orientation;

    public obstacle(float x,float y, Orientation orientation)
    {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
    }
}
