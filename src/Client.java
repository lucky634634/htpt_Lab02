import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
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

    public void Start(String serverAddr, int serverPort) {
        _serverAddr = serverAddr;
        _serverPort = serverPort;
        Thread t = new Thread(() -> Run());
        // t.setDaemon(true);
        t.start();
        try {
            Thread.sleep(1000);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Stop() {
        _running.set(false);
    }

    private void Run() {
        try {
            _socket = new Socket(InetAddress.getByName(_serverAddr), _serverPort);
            _running.set(true);
            while (_running.get()) {
                try {
                    ObjectInputStream ois = new ObjectInputStream(_socket.getInputStream());
                    Object obj = ois.readObject();
                    if (obj instanceof ResponseId) {
                        ScoreManager.GetInstance().id = ((ResponseId) obj).id;
                        System.out.println("Received new player ID: " + ScoreManager.GetInstance().id);
                        LogHandler.GetInstance().Log("Connected");
                    } else if (obj instanceof ServerMessage) {
                        if (((ServerMessage) obj).seed != Maze.GetInstance().seed) {
                            Maze.GetInstance().Generate(((ServerMessage) obj).seed);
                        }
                        ScoreManager.GetInstance().UpdateList(((ServerMessage) obj).scores);
                        TankManager.GetInstance().SetTankList(((ServerMessage) obj).tanks);
                        BulletManager.GetInstance().SetBulletList(((ServerMessage) obj).bullets);
                    } else if (obj instanceof LogMessage) {
                        String log = ((LogMessage) obj).logString;
                        LogHandler.GetInstance().Log(log);
                    }

                    else if (obj instanceof String) {
                        System.out.println("Received string: " + obj.toString());
                    } else {
                        System.out.println("Received unknown object: " + obj.toString());
                    }
                } catch (Exception e) {
                    Maze.GetInstance().seed = -1;
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
            _running.set(false);
            LogHandler.GetInstance().Log("Connection lost");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> void SendMessage(T msg) {
        if (!_running.get() || _socket == null || _socket.isClosed()) {
            return;
        }

        try {
            // System.out.println("Sending message: " + msg.toString());
            ObjectOutputStream oos = new ObjectOutputStream(_socket.getOutputStream());
            oos.writeObject(msg);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}