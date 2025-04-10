import java.io.Serializable;

public class ScoreObject implements Serializable {
    public int id = 0;
    public String name = "";
    public int score = 0;
    public String type = "";

    private final static long serialVersionUID = 1L;

    public ScoreObject(int id, String name, int score, String type) {
        this.id = id;
        this.name = name;
        this.score = score;
        this.type = type;
    }

    public Object[] ConvertToObject() {
        return new Object[] { name, String.valueOf(score), type };
    }
}