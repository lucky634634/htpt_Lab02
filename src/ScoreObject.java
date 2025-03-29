public class ScoreObject {
    public String name = "";
    public int score = 0;
    public String type = "";

    public Object[] ConvertToObject() {
        return new Object[] { name, String.valueOf(score), type };
    }
}