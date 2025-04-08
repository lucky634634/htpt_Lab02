import java.util.ArrayList;
import javax.swing.ImageIcon;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static int PORT = 3030;
    public static final String STOP_STRING = "exit";
    public static ArrayList<Integer> connectedPort = new ArrayList<Integer>();

    public Server() {
        TankManager.GetInstance().setOnChangeCallback(this::BroadcastUpdate);
        BulletManager.GetInstance().setOnChangeCallback(this::BroadcastUpdate);
        new Thread(() ->
        {
        GameFrame gameFrame = new GameFrame();
        gameFrame.Run("server", Server.PORT);
        }).start();
        System.out.println("Server started on port " + Server.PORT);
        try (ServerSocket serverSocket = new ServerSocket(Server.PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> HandleIncomeMessage(socket)).start();
            }
        } catch (Exception e) {
        e.printStackTrace();
        }
    }

    private void SendMessage(Message message, int toPort) {
        while (true) {
            try {
                Socket socket = new Socket("127.0.0.1", toPort);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(message);
                oos.flush();
                oos.close();
                socket.close();
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void BroadcastUpdate() {
        ArrayList<Transform> tanks = Transform.fromTankList(TankManager.GetInstance().tanks);
        ArrayList<Transform> bullets = Transform.fromBulletList(BulletManager.GetInstance().bullets);
        for (int port : connectedPort) {
            Message message = new Message("update", Server.PORT, port, connectedPort.indexOf(port), 0, tanks, bullets, null);
            SendMessage(message, port);
        }
    }

    private void HandleIncomeMessage(Socket socket) {
        try (ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
            Message message = (Message) ois.readObject();
            System.out.println("Server: " + message.type + " from client " + message.fromPort);
            if (message.type.equals("hello")) {
                connectedPort.add(message.fromPort);
                TankManager.GetInstance().CreateTank(new ImageIcon("assets/tank1.png").getImage(), "Player").SpawnRandom();
                BroadcastUpdate();
                return;
            }
            if(message.type.equals("input")) {
                TankManager.GetInstance().HandleInput(message.fromPort, message.input);
                return;
            }
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}