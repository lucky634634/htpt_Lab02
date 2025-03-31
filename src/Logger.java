import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalTime;

public class Logger {
    private static Logger _instance = null;
    private final String _logFile = "log/log.txt";

    private Logger() {
    }

    public static Logger GetInstance() {
        if (_instance == null) {
            synchronized (Logger.class) {
                _instance = new Logger();
            }
        }
        return _instance;
    }

    public void Log(String message) {
        LocalTime localTime = LocalTime.now();
        System.out.println("[" + localTime.toString() + "] " + message);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(_logFile, true))) {
            writer.write("[" + localTime.toString() + "] " + message + "\n");
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}