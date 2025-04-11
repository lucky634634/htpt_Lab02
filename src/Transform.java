import java.io.Serializable;

public class Transform implements Serializable {
    private final static long serialVersionUID = 1L;
    public int id = 0;
    public int x = 0;
    public int y = 0;
    public Direction direction = Direction.NONE;

    public Transform(int id, int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.id = id;
    }
}
