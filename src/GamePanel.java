import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
    private float _deltaTime = 0;
    private boolean _isRunning = false;

    private final static GameInput _gameInput = new GameInput();
    private String _playerType = "Server"; // Server, Client

    public GamePanel(String playerType) {
        setPreferredSize(
                new Dimension(Setting.MAZE_WIDTH * Setting.MAZE_UNIT, Setting.MAZE_HEIGHT * Setting.MAZE_UNIT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(_gameInput);
        _isRunning = false;
        _playerType = playerType;
    }

    public void Setup() {
        Setup(0);
    }

    public void Setup(int seed) {
        _isRunning = true;
        Maze.GetInstance().Generate(0);
        TankManager.GetInstance().Clear();
        BulletManager.GetInstance().Clear();
        TankManager.GetInstance().CreateTank(Setting.TANK_IMAGE, "Player").SpawnRandom();
        TankManager.GetInstance().CreateTank(Setting.ENEMY_IMAGE, "Enemy").SpawnRandom();
        TankManager.GetInstance().CreateTank(Setting.ENEMY_IMAGE, "Enemy").SpawnRandom();
        TankManager.GetInstance().CreateTank(Setting.ENEMY_IMAGE, "Enemy").SpawnRandom();

        LogHandler.GetInstance().Log("Start Game");
    }

    public void Run() {
        // new Thread(this).start();
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
                long sleepTime = (Setting.TARGET_DELTA_TIME) - elapsedTime;
                if (sleepTime > 0)
                    Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
            }
        }
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
}
