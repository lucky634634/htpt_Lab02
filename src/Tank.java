
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Tank {
    public int x = 0;
    public int y = 0;

    public Direction direction = Direction.UP; // U D L R
    public Color color = Color.RED;

    private Image _image = null;
    private static final int MAZE_UNIT = GamePanel.MAZE_UNIT;

    public static final float SPEED = 2f;
    public static final float ROTATION_SPEED = 2f;
    public static final float FIRE_RATE = SPEED;

    private float _moveTime = 0;
    private float _rotateTime = 0;
    private float _fireTime = 0;

    public Tank(Image image) {
        this._image = image;
    }

    public void Init(int x, int y) {
        _moveTime = 0;
        _rotateTime = 0;
        _fireTime = 0;
        this.x = x;
        this.y = y;
    }

    public void Draw(Graphics g) {
        g.setColor(color);
        int centerX = x * GamePanel.MAZE_UNIT + GamePanel.MAZE_UNIT / 2;
        int centerY = y * GamePanel.MAZE_UNIT + GamePanel.MAZE_UNIT / 2;
        Graphics2D g2d = (Graphics2D) g;

        AffineTransform originalTransform = g2d.getTransform();
        switch (direction) {
            case UP -> g2d.rotate(Math.toRadians(0), centerX, centerY);
            case LEFT -> g2d.rotate(Math.toRadians(270), centerX, centerY);
            case DOWN -> g2d.rotate(Math.toRadians(180), centerX, centerY);
            case RIGHT -> g2d.rotate(Math.toRadians(90), centerX, centerY);
            case NONE -> {
            }
        }
        g2d.drawImage(_image, x * MAZE_UNIT, y * MAZE_UNIT, MAZE_UNIT, MAZE_UNIT, null);
        g2d.setTransform(originalTransform);
    }

    public void SetPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void Move(Maze maze, Direction dir) {
        if (dir != Direction.NONE && dir != direction) {
            direction = dir;
            _rotateTime = 1 / ROTATION_SPEED;
            return;
        }
        if (_moveTime > 0 || _rotateTime > 0)
            return;
        _moveTime = 1 / SPEED;
        if (maze.CheckMove(x, y, dir)) {
            switch (dir) {
                case UP -> y -= 1;
                case DOWN -> y += 1;
                case LEFT -> x -= 1;
                case RIGHT -> x += 1;
                case NONE -> {
                }
            }
        }
    }

    public void Update(float deltaTime) {
        _moveTime -= deltaTime;
        _rotateTime -= deltaTime;
        _fireTime -= deltaTime;
    }

    public void Shoot(ArrayList<Bullet> bullets) {
        if (_fireTime > 0)
            return;
        System.out.println("Fire");
        _fireTime = 1 / FIRE_RATE;
        bullets.add(new Bullet(x, y, direction));
    }
}