import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    public String type;
    public int fromPort;
    public int toPort;
    public int id;
    public int seed;
    public ArrayList<Transform> tanks;
    public ArrayList<Transform> bullets;
    public String input;

    public Message(String type, int fromPort, int toPort, int id, int seed, ArrayList<Transform> tanks2, ArrayList<Transform> bullets2, String input) {
        this.type = type;
        this.fromPort = fromPort;
        this.toPort = toPort;
        this.id = id;
        this.seed = seed;
        this.tanks = tanks2;
        this.bullets = bullets2;
        this.input = input;
    }
}