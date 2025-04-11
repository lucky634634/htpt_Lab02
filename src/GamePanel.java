import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
    private float _deltaTime = 0;
    private boolean _isRunning = false;

    private final static GameInput _gameInput = new GameInput();
    private boolean _isHost = false; // Server, Client

    public GamePanel(boolean isHost) {
        setPreferredSize(
                new Dimension(Setting.MAZE_WIDTH * Setting.MAZE_UNIT, Setting.MAZE_HEIGHT * Setting.MAZE_UNIT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(_gameInput);
        _isRunning = false;
        _isHost = isHost;
        setBackground(Color.BLACK);
    }

    public void Setup() {
        _isRunning = true;
        if (_isHost) {
            Maze.GetInstance().Generate((int) (Math.random() * 10000));
            int id = 0;
            TankManager.GetInstance().CreateTank(id, ScoreManager.GetInstance().GetName(id)).SpawnRandom();
        }
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
        if (_isHost) {
            Direction dir = Direction.NONE;
            boolean shoot = _gameInput.GetKey(Setting.KEY_FIRE);
            if (_gameInput.GetKey(Setting.KEY_LEFT)) {
                dir = Direction.LEFT;
            } else if (_gameInput.GetKey(Setting.KEY_RIGHT)) {
                dir = Direction.RIGHT;
            } else if (_gameInput.GetKey(Setting.KEY_UP)) {
                dir = Direction.UP;
            } else if (_gameInput.GetKey(Setting.KEY_DOWN)) {
                dir = Direction.DOWN;
            }

            if (dir != Direction.NONE || shoot) {
                System.out.println("Updating tank 0");
                InputQueue.GetInstance().Add(new Input(0, dir, shoot, false));
            }
            InputQueue.GetInstance().Resolve();
            TankManager.GetInstance().Update(_deltaTime);
            BulletManager.GetInstance().Update(_deltaTime);
            Server.GetInstance()
                    .SendAll(new ServerMessage(Maze.GetInstance().seed, TankManager.GetInstance().GetTankList(),
                            BulletManager.GetInstance().GetBulletTransforms(), ScoreManager.GetInstance().GetScores()));
        } else {
            Direction dir = Direction.NONE;
            boolean shoot = _gameInput.GetKey(Setting.KEY_FIRE);
            if (_gameInput.GetKey(Setting.KEY_LEFT)) {
                dir = Direction.LEFT;
            } else if (_gameInput.GetKey(Setting.KEY_RIGHT)) {
                dir = Direction.RIGHT;
            } else if (_gameInput.GetKey(Setting.KEY_UP)) {
                dir = Direction.UP;
            } else if (_gameInput.GetKey(Setting.KEY_DOWN)) {
                dir = Direction.DOWN;
            }

            if (dir != Direction.NONE || shoot) {
                System.out.println("Updating tank 0");
                Client.GetInstance().SendMessage(new Input(ScoreManager.GetInstance().id, dir, shoot, false));
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
        g.setColor(new Color(0, 0, 100));
        g.drawString("FPS: " + (int) (1f / _deltaTime), 0, 20);

    }
}
