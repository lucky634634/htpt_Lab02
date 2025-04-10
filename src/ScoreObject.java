import java.io.Serializable;

public class ScoreObject implements Serializable {
    public int id = 0;
    public String name = "";
    public int score = 0;

    private final static long serialVersionUID = 1L;

    public ScoreObject() {
        id = 0;
        name = "";
        score = 0;
    }

    public ScoreObject(int id, String name, int score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    public Object[] ConvertToObject() {
        return new Object[] { name, String.valueOf(score), null };
    }

    @Override
    public String toString() {
        return "{ id: " + id + ", name: " + name + ", score: " + score + " }";
    }
}