import java.io.Serializable;

public class ClientMessage implements Serializable {
    public String name = "";
    public int id = -1;
    public Direction direction = Direction.NONE;
    public boolean shoot = false;

    private final static long serialVersionUID = 1L;

    public ClientMessage(String name, int id, Direction direction, boolean shoot) {
        this.name = name;
        this.id = id;
        this.direction = direction;
        this.shoot = shoot;
    }

    @Override
    public String toString() {
        return "ClientMessage{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", direction=" + direction +
                ", shoot=" + shoot +
                '}';
    }
}