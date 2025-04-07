import java.io.BufferedInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Client {
    public int port ;
    public Client(int port){
        this.port = port;
        sendMessage(new Message("hello", port, 3030, 0, null, null, null));
        System.out.println("Client started on port " + port);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> readMessages(socket)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Message message) {
        try {
            Socket socket = new Socket("127.0.0.1", Server.PORT);
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
    public void readMessages(Socket socket) {
        try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()))) {
            Message message = (Message) ois.readObject();
            System.out.println("Client: " + message.type + " from server " + message.fromPort);
            if (message.type.equals("init")) {
                Maze.GetInstance().Generate(message.seed);
                TankManager.GetInstance().tanks = Transform.toTankList(message.tanks, port);
                BulletManager.GetInstance().bullets = Transform.toBulletList(message.bullets);
                GameFrame gameFrame = new GameFrame();
                gameFrame.Run("client", port);
                return;
            }
            if(message.type.equals("update")) {
                TankManager.GetInstance().tanks = Transform.toTankList(message.tanks, port);
                BulletManager.GetInstance().bullets = Transform.toBulletList(message.bullets);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}