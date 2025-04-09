import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
    public float _deltaTime = 0;
    private boolean _isRunning = false;

    private long lastMessageTime = 0;

    private final static GameInput _gameInput = new GameInput();
    public GamePanel() {
        setPreferredSize(new Dimension(Setting.MAZE_WIDTH * Setting.MAZE_UNIT, Setting.MAZE_HEIGHT * Setting.MAZE_UNIT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(_gameInput);
        _isRunning = false;
    }

    public void Setup(String role) {
        _isRunning = true;
        if(role == "server") {
            Maze.GetInstance().Generate(0);
            TankManager.GetInstance().Clear();
            BulletManager.GetInstance().Clear();
            TankManager.GetInstance().CreateTank(new ImageIcon("assets/tank1.png").getImage(), "Host").SpawnRandom();
        }
        LogHandler.GetInstance().Log("Start Game");
    }

    public void Run(String role, int port) {
        // new Thread(this).start();
        long lastTime = System.currentTimeMillis();
        long currentTime;
        while (_isRunning) {
            try {
                currentTime = System.currentTimeMillis();
                _deltaTime = (currentTime - lastTime) / 1000.0f;
                lastTime = currentTime;
                if(role == "client")
                    try {
                        Update(port);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                else
                {
                    TankManager.GetInstance().Update(_deltaTime);
                    BulletManager.GetInstance().Update(_deltaTime);
                }
                repaint();
                long elapsedTime = System.currentTimeMillis() - lastTime;
                long sleepTime = (Setting.TARGET_DELTA_TIME) - elapsedTime;
                if (sleepTime > 0)
                    Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
            }
        }
    }

    private void Update(int port) throws IOException {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastMessageTime < 200) {
            return;
        }
        Message message = new Message(null, 0, 0, 0, 0, null, null, null);
        if (_gameInput.GetKey(KeyEvent.VK_SPACE)) {
            message = new Message("input", port, Server.PORT, 0, 0, null, null, "shoot");
        }
        if (_gameInput.GetKey(KeyEvent.VK_UP)) {
            message = new Message("input", port, Server.PORT, 0, 0, null, null, "up");
        } else if (_gameInput.GetKey(KeyEvent.VK_DOWN)) {
            message = new Message("input", port, Server.PORT, 0, 0, null, null, "down");
        } else if (_gameInput.GetKey(KeyEvent.VK_LEFT)) {
            message = new Message("input", port, Server.PORT, 0, 0, null, null, "left");
        } else if (_gameInput.GetKey(KeyEvent.VK_RIGHT)) {
            message = new Message("input", port, Server.PORT, 0, 0, null, null, "right");
        }
        if(message.type == null) {
            return;
        }
        while (true) {
            try {
                Socket socket = new Socket("127.0.0.1", Server.PORT);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(message);
                oos.flush();
                oos.close();
                socket.close();
                lastMessageTime = currentTime;
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!_isRunning)
            return;

        Maze.GetInstance().Draw(g);
        TankManager.GetInstance().Draw(g);
        BulletManager.GetInstance().Draw(g);
        g.setColor(Color.WHITE);
        g.drawString("FPS: " + (int) (1f / _deltaTime), 10, 10);
    }

}
