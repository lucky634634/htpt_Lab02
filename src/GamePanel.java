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

    private final Tank _player = new Tank(new ImageIcon("assets/tank1.png").getImage());
    private final Maze _maze = new Maze();

    private final Random _random = new Random();

    public GamePanel() {
        setPreferredSize(new Dimension(MAZE_WIDTH * MAZE_UNIT, MAZE_HEIGHT * MAZE_UNIT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(_gameInput);
        _isRunning = true;
        _maze.Generate(MAZE_WIDTH, MAZE_HEIGHT, 1111);
    }

    public void Init() {
        _player.x = _random.nextInt(MAZE_WIDTH);
        _player.y = _random.nextInt(MAZE_HEIGHT);
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

        _player.Update(_deltaTime);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        _maze.Draw(g);
        _player.Draw(g);

        g.setColor(Color.WHITE);
        g.drawString("FPS: " + (int) (1f / _deltaTime), 10, 10);
    }

    @Override
    public void run() {
        long lastTime = System.currentTimeMillis();
        long currentTime;
        while (_isRunning) {
            currentTime = System.currentTimeMillis();
            _deltaTime = (currentTime - lastTime) / 1000.0f;
            lastTime = currentTime;
            _gameInput.UpdateKey();
            Update();
            repaint();
            long elapsedTime = System.currentTimeMillis() - lastTime;
            long sleepTime = (long) (1000.0f / FPS) - elapsedTime;
            try {
                if (sleepTime > 0)
                    Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
            }
        }
        System.exit(0);
    }
}
