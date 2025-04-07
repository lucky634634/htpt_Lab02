import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    public String type;
    public int fromPort;
    public int toPort;
    public int seed;
    public ArrayList<Transform> tanks;
    public ArrayList<Transform> bullets;
    public String input;

    public Message(String type, int fromPort, int toPort, int seed, ArrayList<Transform> tanks, ArrayList<Transform> bullets, String input) {
        this.type = type;
        this.fromPort = fromPort;
        this.toPort = toPort;
        this.seed = seed;
        this.tanks = tanks;
        this.bullets = bullets;
        this.input = input;
    }
}