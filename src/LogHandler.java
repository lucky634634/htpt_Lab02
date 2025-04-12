import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalTime;
import java.util.ArrayList;

public class LogHandler {
    private static LogHandler _instance = null;
    private final ArrayList<LogListener> _listeners = new ArrayList<>();

    private LogHandler() {
    }

    public static LogHandler GetInstance() {
        if (_instance == null) {
            synchronized (LogHandler.class) {
                _instance = new LogHandler();
            }
        }
        return _instance;
    }

    public void AddListener(LogListener listener) {
        _listeners.add(listener);
    }

    public void Log(String message) {
        HandleLog(message);
    }

    public void LogAll(String msg) {
        Log(msg);
        Server.GetInstance().SendAll(new LogMessage(msg));
    }

    private void HandleLog(String message) {
        LocalTime localTime = LocalTime.now();
        System.out.println("[" + localTime.toString() + "] " + message);
        for (LogListener listener : _listeners) {
            listener.Log(message);
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Setting.LOG_FILE, true))) {
            writer.write("[" + localTime.toString() + "] " + message + "\n");
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}