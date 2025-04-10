import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server {

    private static Server _instance = null;
    private int _port = 5000;
    private AtomicBoolean _running = new AtomicBoolean(false);

    private Server() {
    }

    public static Server get_instance() {
        if (_instance == null) {
            synchronized (Server.class) {
                _instance = new Server();
            }
        }
        return _instance;
    }

    public void Start(int port) {
        _port = port;
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