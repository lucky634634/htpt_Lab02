
import java.awt.Graphics;
import java.util.ArrayList;

public class BulletManager {
    public ArrayList<Bullet> bullets = new ArrayList<>();

    private static BulletManager _instance = null;

    private BulletManager() {
    }

    public static BulletManager GetInstance() {
        if (_instance == null) {
            synchronized (BulletManager.class) {
                _instance = new BulletManager();
            }
        }
        return _instance;
    }

    public void Clear() {
        bullets.clear();
    }

    public void CreateBullet(int x, int y, Direction direction, int tankId) {
        if (direction == Direction.NONE)
            return;
        bullets.add(new Bullet(x, y, direction, tankId));
    }

    public void Update(float deltaTime) {
        for (Bullet bullet : bullets) {
            bullet.Update(deltaTime);
        }
        bullets.removeIf(bullet -> bullet.x < 0 || bullet.x >= Setting.MAZE_WIDTH || bullet.y < 0
                || bullet.y >= Setting.MAZE_HEIGHT || bullet.direction == Direction.NONE);
    }

    public void Draw(Graphics g) {
        if (bullets.isEmpty())
            return;
        for (Bullet bullet : bullets) {
            bullet.Draw(g);
        }
    }
}