
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

public class Tank {
    public int x = 0;
    public int y = 0;

    public Direction direction = Direction.UP; // U D L R
    public Color color = Color.RED;
    public String name = "";
    public int id = 0;

    private Image _image = null;

    private float _moveTime = 0;
    private float _fireTime = 0;

    public Tank(int id, Image image, String name) {
        this._image = image;
        this.name = name;
        this.id = id;
    }

    public void Init(int x, int y) {
        _moveTime = 0;
        _fireTime = 0;
        this.x = x;
        this.y = y;
    }

    public void Draw(Graphics g) {
        g.setColor(color);
        int centerX = x * Setting.MAZE_UNIT + Setting.MAZE_UNIT / 2;
        int centerY = y * Setting.MAZE_UNIT + Setting.MAZE_UNIT / 2;
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
        g2d.drawImage(_image, x * Setting.MAZE_UNIT, y * Setting.MAZE_UNIT, Setting.MAZE_UNIT, Setting.MAZE_UNIT, null);
        g2d.setTransform(originalTransform);

        g.setColor(Color.BLACK);
        g.drawString(name, centerX, centerY + 25);
    }

    public void SetPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void Move(Direction dir) {
        if (dir != Direction.NONE && dir != direction) {
            direction = dir;
            _moveTime = 1 / Setting.TANK_SPEED;
            return;
        }
        if (_moveTime > 0)
            return;
        _moveTime = 1 / Setting.TANK_SPEED;
        if (Maze.GetInstance().CheckMove(x, y, dir)) {
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
        _fireTime -= deltaTime;
    }

    public void Shoot() {
        if (_fireTime > 0)
            return;
        System.out.println("Fire");
        _fireTime = 1 / Setting.TANK_FIRE_RATE;
        BulletManager.GetInstance().CreateBullet(x, y, direction, id);
    }

    public void SetState(int x, int y, Direction dir) {
        this.x = x;
        this.y = y;
        this.direction = dir;
    }

    public void Hit() {
        System.out.println("Hit");
        TankManager.GetInstance().SpawnRandom(this);
    }

    public void SpawnRandom() {
        TankManager.GetInstance().SpawnRandom(this);
    }
}