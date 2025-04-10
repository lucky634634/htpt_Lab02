import java.util.ArrayList;

public class ScoreManager {

    private static ScoreManager _instance = null;
    private final ArrayList<ScoreObject> _scores = new ArrayList<>();

    private ScoreManager() {

    }

    public static ScoreManager GetInstance() {
        if (_instance == null) {
            synchronized (ScoreManager.class) {
                _instance = new ScoreManager();
            }
        }
        return _instance;
    }

}