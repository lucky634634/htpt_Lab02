import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
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

    public GamePanel() {
        setPreferredSize(new Dimension(MAZE_WIDTH * MAZE_UNIT, MAZE_HEIGHT * MAZE_UNIT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(_gameInput);
        _isRunning = true;
        _moveTime = 1f / SPEED;
        new Thread(this).start();
    }

    private void Update() {
        if (_gameInput.GetKey(KeyEvent.VK_ESCAPE)) {
            _isRunning = false;
        }
        if (_moveTime <= 0) {
            if (_gameInput.GetKey(KeyEvent.VK_UP)) {
                _intY--;
                _moveTime = 1f / SPEED;
            } else if (_gameInput.GetKey(KeyEvent.VK_DOWN)) {
                _intY++;
                _moveTime = 1f / SPEED;
                _moveTime = 1f / SPEED;
            } else if (_gameInput.GetKey(KeyEvent.VK_LEFT)) {
                _intX--;
                _moveTime = 1f / SPEED;
            } else if (_gameInput.GetKey(KeyEvent.VK_RIGHT)) {
                _intX++;
                _moveTime = 1f / SPEED;
            }
        } else {
            _moveTime -= _deltaTime;
        }
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
        g.setColor(Color.RED);
        g.fillOval(_intX * MAZE_UNIT, _intY * MAZE_UNIT, MAZE_UNIT, MAZE_UNIT);
        g.setColor(Color.WHITE);
        g.drawString("FPS: " + (int) (1f / _deltaTime), 10, 10);
    }

    @Override
    public void run() {
        long lastTime = System.currentTimeMillis();
        long currentTime;
        try {
            while (_isRunning) {
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
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
