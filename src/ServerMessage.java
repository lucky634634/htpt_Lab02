import java.io.Serializable;

public class ServerMessage implements Serializable {
    public int seed = -1;
    public int id = -1;
    public Transform[] tanks = null;
    public Transform[] bullets = null;
    public ScoreObject[] scores = null;

    private final static long serialVersionUID = 1L;

    public ServerMessage(int seed, int id, Transform[] tanks, Transform[] bullets, ScoreObject[] scores) {
        this.seed = seed;
        this.id = id;
        this.tanks = tanks;
        this.bullets = bullets;
        this.scores = scores;
    }

    @Override
    public String toString() {
        return "ServerMessage{" +
                "seed=" + seed +
                ", id=" + id +
                ", tanks=" + tanks.length +
                ", bullets=" + bullets.length +
                ", scores=" + scores.length +
                '}';
    }
}