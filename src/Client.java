import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class Client {
    private static Client _instance = null;
    private String _serverAddr = "";
    private int _serverPort = 0;
    private AtomicBoolean _running = new AtomicBoolean(false);
    private Socket _socket = null;

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
        _running.set(true);
        try {
            _socket = new Socket(InetAddress.getByName(_serverAddr), _serverPort);
            while (_running.get()) {
                ObjectInputStream ois = new ObjectInputStream(_socket.getInputStream());
                try {
                    Object obj = ois.readObject();
                    if (obj instanceof ServerMessage) {
                        System.out.println("Received message: " + obj.toString());
                    } else if (obj instanceof String) {
                        System.out.println("Received string: " + obj.toString());
                    } else {
                        System.out.println("Received unknown object: " + obj.toString());
                    }
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T> void SendMessage(T msg) {
        if (!_running.get() || _socket == null || _socket.isClosed()) {
            return;
        }

        try {
            ObjectOutputStream oos = new ObjectOutputStream(_socket.getOutputStream());
            oos.writeObject(msg);
            oos.flush();
        } catch (Exception e) {
        }
    }
}