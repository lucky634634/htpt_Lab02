import java.io.Serializable;

public class ServerMessage implements Serializable {
    public int seed = -1;
    public int id = -1;
    public TankTransform[] tanks = null;
    public BulletTransform[] bullets = null;
    public ScoreObject[] scores = null;

    private final static long serialVersionUID = 1L;

    public ServerMessage(int seed, int id, TankTransform[] tanks, BulletTransform[] bullets, ScoreObject[] scores) {
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