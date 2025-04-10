import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class ScoreManager {

    private static ScoreManager _instance = null;
    private final ArrayList<ScoreObject> _scores = new ArrayList<>();
    public int id = 0;

    private int _currentId = 0;

    private Queue<Integer> _freeIds = new LinkedList<>();

    private ScoreManager() {
        id = 0;
    }

    public static ScoreManager GetInstance() {
        if (_instance == null) {
            synchronized (ScoreManager.class) {
                _instance = new ScoreManager();
            }
        }
        return _instance;
    }

    public int CreateNewPlayer(String name) {
        if (_freeIds.isEmpty()) {
            int id = _currentId++;
            ScoreObject score = new ScoreObject(id, name, 0);
            _scores.add(score);
            return id;
        }
        int id = _freeIds.poll();
        ScoreObject score = new ScoreObject(id, name, 0);
        _scores.add(score);
        return id;
    }

    public void RemovePlayer(int id) {
        if (_scores.isEmpty()) {
            return;
        }
        if (_scores.removeIf(score -> score.id == id)) {
            _freeIds.add(id);
        }
    }

    public void UpdateList(ScoreObject[] scores) {
        for (int i = 0; i < scores.length; i++) {
            scores[i] = _scores.get(i);
        }
    }

    public String GetName(int id) {
        for (ScoreObject score : _scores) {
            if (score.id == id) {
                return score.name;
            }
        }
        return null;
    }
}