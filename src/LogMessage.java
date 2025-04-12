import java.io.Serializable;

public class LogMessage implements Serializable {
    private final static long serialVersionUID = 1L;

    public String logString = "";

    public LogMessage(String logString) {
        this.logString = logString;
    }

    @Override
    public String toString() {
        return "LogMessage{" +
                "logString='" + logString + '\'' +
                '}';
    }
}