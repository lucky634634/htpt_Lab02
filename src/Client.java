import java.util.concurrent.atomic.AtomicBoolean;

public class Client {
    private static Client _instance = null;
    private String _serverAddr = "";
    private int _serverPort = 0;
    private AtomicBoolean _running = new AtomicBoolean(false);

    private Client() {
    }

    public static Client GetInstance() {
        if (_instance == null) {
            synchronized (Client.class) {
                _instance = new Client();
            }
        }
        return _instance;
    }

    public void Start(int port, String serverAddr, int serverPort) {
        _serverAddr = serverAddr;
        _serverPort = serverPort;
        Thread t = new Thread(this::Run);
        t.setDaemon(true);
        t.start();
    }

    public void Stop() {
        _running.set(false);
    }

    private void Run() {
    }
}