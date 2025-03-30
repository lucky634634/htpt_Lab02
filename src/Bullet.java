import java.awt.Color;
import java.awt.Graphics;

public class Bullet {
    public int x = 0;
    public int y = 0;
    public Direction direction = Direction.NONE;
    public int tankId = 0;

    private final Color _color = Color.RED;
    private final int SIZE = GamePanel.MAZE_UNIT / 4;
    private final float SPEED = Tank.SPEED * 4;

    private float _moveTime = 0;

    public Bullet(int x, int y, Direction direction, int tankId) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        _moveTime = 0;
    }

    public void Update(float _deltaTime) {
        _moveTime -= _deltaTime;
        if (_moveTime > 0)
            return;
        _moveTime = 1 / SPEED;
        if (!Maze.GetInstance().CheckMove(x, y, direction)) {
            direction = Direction.NONE;
            return;
        }
        switch (direction) {
            case UP -> y -= 1;
            case DOWN -> y += 1;
            case LEFT -> x -= 1;
            case RIGHT -> x += 1;
            case NONE -> {
            }
        }
        Tank tank = TankManager.GetInstance().GetTankByPosition(x, y);
        if (tank == null)
            return;
        if (tankId == TankManager.GetInstance().GetIndex(tank)) {
            return;
        }
        direction = Direction.NONE;
        tank.Hit();
    }

    public void Draw(Graphics g) {
        g.setColor(_color);
        int centerX = x * GamePanel.MAZE_UNIT + GamePanel.MAZE_UNIT / 2;
        int centerY = y * GamePanel.MAZE_UNIT + GamePanel.MAZE_UNIT / 2;
        g.fillOval(centerX - SIZE / 2, centerY - SIZE / 2, SIZE, SIZE);
    }
}