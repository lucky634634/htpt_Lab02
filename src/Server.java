import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server {

    private static Server _instance = null;
    private int _port = 5000;
    private AtomicBoolean _running = new AtomicBoolean(false);
    private ServerSocket _serverSocket = null;
    private ArrayList<Thread> _clientThreads = new ArrayList<>();

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
        Thread t = new Thread(this::Run);
        t.setDaemon(true);
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
                _clientThreads.add(new Thread(() -> HandleClient(socket)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void HandleClient(Socket socket) {
        while (_running.get()) {
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                try {

                    Object obj = ois.readObject();
                    if (obj instanceof ClientMessage) {
                        ClientMessage message = (ClientMessage) obj;
                        System.out.println("Received message from client: " + message);
                    } else if (obj instanceof String) {
                        System.out.println("Received string from client: " + obj);
                    } else {
                        System.out.println("Unknown object received from client: " + obj);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public <T> void SendMessage(T msg, int id) {
        if (!_running.get() || _serverSocket == null || _serverSocket.isClosed()) {
            return;
        }
    }
}