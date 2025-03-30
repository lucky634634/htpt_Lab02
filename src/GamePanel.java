import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    public static final int MAZE_WIDTH = 32;
    public static final int MAZE_HEIGHT = 16;
    public static final int MAZE_UNIT = 32;

    private static final int FPS = 60;
    private float _deltaTime = 0;
    private boolean _isRunning = false;

    private final static GameInput _gameInput = new GameInput();

    private final Random _random = new Random();

    public GamePanel() {
        setPreferredSize(new Dimension(MAZE_WIDTH * MAZE_UNIT, MAZE_HEIGHT * MAZE_UNIT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(_gameInput);
        _isRunning = false;
    }

    public void Setup() {
        _isRunning = true;
        Maze.GetInstance().Generate(MAZE_WIDTH, MAZE_HEIGHT, 0);
        TankManager.GetInstance().Clear();
        TankManager.GetInstance().AddTank(new Tank(new ImageIcon("assets/tank1.png").getImage()));
        TankManager.GetInstance().GetTank(0).SetPosition(_random.nextInt(MAZE_WIDTH), _random.nextInt(MAZE_HEIGHT));
        BulletManager.GetInstance().Clear();
    }

    public void Run() {
        new Thread(this).start();
    }

    private void Update() {
        if (_gameInput.GetKey(KeyEvent.VK_SPACE)) {
            TankManager.GetInstance().GetTank(0).Shoot();
        }

        if (_gameInput.GetKey(KeyEvent.VK_UP)) {
            TankManager.GetInstance().GetTank(0).Move(Direction.UP);
        } else if (_gameInput.GetKey(KeyEvent.VK_DOWN)) {
            TankManager.GetInstance().GetTank(0).Move(Direction.DOWN);
        } else if (_gameInput.GetKey(KeyEvent.VK_LEFT)) {
            TankManager.GetInstance().GetTank(0).Move(Direction.LEFT);
        } else if (_gameInput.GetKey(KeyEvent.VK_RIGHT)) {
            TankManager.GetInstance().GetTank(0).Move(Direction.RIGHT);
        }

        TankManager.GetInstance().Update(_deltaTime);
        BulletManager.GetInstance().Update(_deltaTime);
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

    @Override
    public void run() {
        long lastTime = System.currentTimeMillis();
        long currentTime;
        while (_isRunning) {
            try {
                currentTime = System.currentTimeMillis();
                _deltaTime = (currentTime - lastTime) / 1000.0f;
                lastTime = currentTime;
                Update();
                repaint();
                long elapsedTime = System.currentTimeMillis() - lastTime;
                long sleepTime = (long) (1000.0f / FPS) - elapsedTime;
                if (sleepTime > 0)
                    Thread.sleep(sleepTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.exit(0);
    }
}
