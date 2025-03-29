import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    public static final int MAZE_WIDTH = 32;
    public static final int MAZE_HEIGHT = 16;
    public static final int MAZE_UNIT = 32;

    private static final int FPS = 60;
    private float _deltaTime = 0;
    private boolean _isRunning = false;

    private int _intX = 0;
    private int _intY = 0;
    private float _moveTime = 0;
    private final float SPEED = 5f;
    private final static GameInput _gameInput = new GameInput();

    private final Tank _player = new Tank(Color.GREEN);

    private final Random _random = new Random();

    public GamePanel() {
        setPreferredSize(new Dimension(MAZE_WIDTH * MAZE_UNIT, MAZE_HEIGHT * MAZE_UNIT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(_gameInput);
        _isRunning = true;
        _moveTime = 1f / SPEED;
    }

    public void Init() {
        _player.x = _random.nextInt(MAZE_WIDTH);
        _player.y = _random.nextInt(MAZE_HEIGHT);
    }

    public void Run() {
        new Thread(this).start();
    }

    private void Update() {
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(50, 50, 50));
        for (int i = 0; i < MAZE_WIDTH; i++) {
            g.drawLine(i * MAZE_UNIT, 0, i * MAZE_UNIT, MAZE_HEIGHT * MAZE_UNIT);
        }
        for (int j = 0; j < MAZE_HEIGHT; j++) {
            g.drawLine(0, j * MAZE_UNIT, MAZE_WIDTH * MAZE_UNIT, j * MAZE_UNIT);
        }

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
