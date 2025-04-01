import java.awt.Color;
import java.awt.Graphics;

public class Bullet {
    public int x = 0;
    public int y = 0;
    public Direction direction = Direction.NONE;
    public int tankId = 0;

    private final Color _color = Color.RED;

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
        _moveTime = 1 / Setting.BULLET_SPEED;
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
        LogHandler.GetInstance().Log("Bullet hit tank " + tank.name);
    }

    public void Draw(Graphics g) {
        g.setColor(_color);
        int centerX = x * Setting.MAZE_UNIT + Setting.MAZE_UNIT / 2;
        int centerY = y * Setting.MAZE_UNIT + Setting.MAZE_UNIT / 2;
        g.fillOval(centerX - Setting.BULLET_SIZE / 2, centerY - Setting.BULLET_SIZE / 2, Setting.BULLET_SIZE,
                Setting.BULLET_SIZE);
    }
}