import java.io.Serializable;

public class Input implements Serializable {
    public int id = 0;
    public Direction dir = Direction.NONE;
    public boolean shoot = false;
    public boolean quit = false;
}