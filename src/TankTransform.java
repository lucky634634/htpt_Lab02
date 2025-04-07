import java.io.Serializable;

public class TankTransform implements Serializable {
    private static final long serialVersionUID = 1L;

    public int id = 0;
    public int x = 0;
    public int y = 0;
    public Direction direction = Direction.NONE;

    public TankTransform(int id, int x, int y, Direction direction) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.direction = direction;
    }
}