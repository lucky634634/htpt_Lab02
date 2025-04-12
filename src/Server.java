import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server {

    private static Server _instance = null;
    private int _port = 5000;
    private AtomicBoolean _running = new AtomicBoolean(false);
    private ServerSocket _serverSocket = null;
    private final ArrayList<Thread> _clientThreads = new ArrayList<>();
    private final Map<Integer, Socket> _clientSockets = new ConcurrentHashMap<>();

    private Server() {
    }

    public static Server GetInstance() {
        if (_instance == null) {
            synchronized (Server.class) {
                _instance = new Server();
            }
        }
        return _instance;
    }

    public void Start(int port) {
        if (port < 1024 || port > 65535) {
            System.out.println("Invalid port number. Please choose a port between 1024 and 65535.");
            return;
        }
        _port = port;
        Thread t = new Thread(() -> Run());
        // t.setDaemon(true);
        t.start();
    }

    public void Stop() {
        _running.set(false);
        if (_serverSocket != null && _serverSocket.isClosed()) {
            try {
                _serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void Run() {
        _running.set(true);
        try {
            _serverSocket = new ServerSocket(_port);
            while (_running.get()) {
                Socket socket = _serverSocket.accept();
                System.out.println("Client connected: " + socket.getInetAddress());
                Thread t = new Thread(() -> HandleClient(socket));
                t.setDaemon(true);
                _clientThreads.add(t);
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void HandleClient(Socket socket) {
        String name = "";
        int id = -1;
        boolean running = true;
        while (_running.get() && running) {
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                try {
                    Object obj = ois.readObject();
                    System.out.println("Received object from client: " + obj.toString());
                    if (obj instanceof RequestId) {
                        name = ((RequestId) obj).name;
                        id = ScoreManager.GetInstance().CreateNewPlayer(name);
                        _clientSockets.put(id, socket);
                        // System.out.println("Received request ID from client: " + ((RequestId)
                        // obj).name);
                        TankManager.GetInstance().CreateTank(id, name).SpawnRandom();

                        SendMessage(new ResponseId(id), id);
                        // SendMessage(new ServerMessage(Maze.GetInstance().seed,
                        // TankManager.GetInstance().GetTankList(),
                        // BulletManager.GetInstance().GetBulletTransforms(),
                        // ScoreManager.GetInstance().GetScores()), id);
                        LogHandler.GetInstance().LogAll("New player " + name + " with ID " + id + " entered");
                        ScoreManager.GetInstance().UpdateListener();
                    } else if (obj instanceof Input) {
                        InputQueue.GetInstance().Add((Input) obj);
                    } else {
                        System.out.println("Unknown object received from client: " + obj);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
                running = false;
                _clientSockets.remove(id);
                TankManager.GetInstance().RemoveTank(id);
                ScoreManager.GetInstance().RemovePlayer(id);
                LogHandler.GetInstance().Log("Player " + name + " left");
            }
        }
    }

    public <T> void SendMessage(T msg, int id) {
        if (!_running.get() || _serverSocket == null || _serverSocket.isClosed()) {
            return;
        }

        try {
            Socket client = _clientSockets.get(id);
            if (client != null) {
                ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
                oos.writeObject(msg);
                oos.flush();
            } else {
                System.out.println("Client with ID " + id + " not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> void SendAll(T msg) {
        if (!_running.get() || _serverSocket == null || _serverSocket.isClosed()) {
            return;
        }

        for (Socket client : _clientSockets.values()) {
            try {
                ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
                oos.writeObject(msg);
                oos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}