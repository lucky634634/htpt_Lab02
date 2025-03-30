import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
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

    private final Tank _player = new Tank(new ImageIcon("assets/tank1.png").getImage());
    private final Maze _maze = new Maze();

    private final Random _random = new Random();

    private final static ArrayList<Bullet> _bullets = new ArrayList<>();
    private final static ArrayList<Tank> _enemies = new ArrayList<>();

    public GamePanel() {
        setPreferredSize(new Dimension(MAZE_WIDTH * MAZE_UNIT, MAZE_HEIGHT * MAZE_UNIT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(_gameInput);
        _isRunning = false;
    }

    public void Setup() {
        _player.x = _random.nextInt(MAZE_WIDTH);
        _player.y = _random.nextInt(MAZE_HEIGHT);
        _maze.Generate(MAZE_WIDTH, MAZE_HEIGHT, 0);
        _bullets.clear();
        _enemies.clear();
        _isRunning = true;
    }

    public void Run() {
        new Thread(this).start();
    }

    private void Update() {
        if (_gameInput.GetKey(KeyEvent.VK_UP))
            _player.Move(_maze, Direction.UP);
        else if (_gameInput.GetKey(KeyEvent.VK_DOWN))
            _player.Move(_maze, Direction.DOWN);
        else if (_gameInput.GetKey(KeyEvent.VK_LEFT))
            _player.Move(_maze, Direction.LEFT);
        else if (_gameInput.GetKey(KeyEvent.VK_RIGHT))
            _player.Move(_maze, Direction.RIGHT);

        if (_gameInput.GetKey(KeyEvent.VK_SPACE)) {
            _player.Shoot(_bullets);
        }

        _player.Update(_deltaTime);
        Iterator<Bullet> bulletIterator = _bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next(); // Move iterator once and store bullet reference
            bullet.Update(_deltaTime);

            if (bullet.x < 0 || bullet.x >= MAZE_WIDTH || bullet.y < 0 || bullet.y >= MAZE_HEIGHT) {
                bulletIterator.remove(); // Safe way to remove while iterating
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!_isRunning)
            return;
        _maze.Draw(g);
        _player.Draw(g);

        for (Bullet bullet : _bullets) {
            bullet.Draw(g);
        }
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
                _gameInput.UpdateKey();
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
