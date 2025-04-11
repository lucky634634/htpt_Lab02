import java.io.Serializable;

public class Input implements Serializable {
    public int id = 0;
    public Direction dir = Direction.NONE;
    public boolean shoot = false;
    public boolean quit = false;

    public Input(int id, Direction dir, boolean shoot, boolean quit) {
        this.id = id;
        this.dir = dir;
        this.shoot = shoot;
        this.quit = quit;
    }

    @Override
    public String toString() {
        return "Input{ " + "id=" + id + ", dir=" + dir + ", shoot=" + shoot + ", quit=" + quit + '}';
    }

}