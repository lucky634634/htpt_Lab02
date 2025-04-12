import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class ScoreManager {

    private static ScoreManager _instance = null;
    private final ArrayList<ScoreObject> _scores = new ArrayList<>();
    // Id hiện tại
    public int id = 0;

    // Tạo id mới (Server)
    private int _nextId = 0;
    private Queue<Integer> _freeIds = new LinkedList<>();
    private final ArrayList<ScoreListener> _scoreListeners = new ArrayList<>();

    private ScoreManager() {
        id = 0;
    }

    public static ScoreManager GetInstance() {
        if (_instance == null) {
            synchronized (ScoreManager.class) {
                if (_instance == null)
                    _instance = new ScoreManager();
            }
        }
        return _instance;
    }

    public ScoreObject[] GetScores() {
        ScoreObject[] scores = new ScoreObject[_scores.size()];
        for (int i = 0; i < scores.length; i++) {
            scores[i] = _scores.get(i);
        }
        return scores;
    }

    public synchronized int CreateNewPlayer(String name) {
        if (_freeIds.isEmpty()) {
            int id = _nextId++;
            ScoreObject score = new ScoreObject(id, name, 0);
            _scores.add(score);
            return id;
        }
        int id = _freeIds.poll();
        ScoreObject score = new ScoreObject(id, name, 0);
        _scores.add(score);
        UpdateListener();
        return id;
    }

    public synchronized void SetName(int id, String name) {
        for (ScoreObject score : _scores) {
            if (score.id == id) {
                score.name = name;
                UpdateListener();
                return;
            }
        }
    }

    public synchronized void RemovePlayer(int id) {
        if (_scores.isEmpty()) {
            return;
        }
        if (_scores.removeIf(score -> score.id == id)) {
            _freeIds.add(id);
        }
        UpdateListener();
    }

    public synchronized void UpdateList(ScoreObject[] scores) {
        _scores.clear();
        for (ScoreObject score : scores) {
            _scores.add(score);
        }
        UpdateListener();
    }

    public synchronized String GetName(int id) {
        for (ScoreObject score : _scores) {
            if (score.id == id) {
                return score.name;
            }
        }
        return null;
    }

    public void AddScoreListener(ScoreListener scoreListener) {
        _scoreListeners.add(scoreListener);
        UpdateListener();
    }

    public void UpdateListener() {
        new Thread(() -> HandleListener()).start();
    }

    private synchronized void HandleListener() {
        ScoreObject[] scores = new ScoreObject[_scores.size()];
        for (int i = 0; i < scores.length; i++) {
            scores[i] = _scores.get(i);
        }
        for (ScoreListener listener : _scoreListeners) {

            listener.OnScoreChange(scores);
        }
    }

    public synchronized void IncreaseScore(int id, int score) {
        for (ScoreObject s : _scores) {
            if (s.id == id) {
                s.score += score;
                UpdateListener();
                return;
            }
        }
    }
}