import java.io.Serializable;
import java.util.ArrayList;

public class TransformBullet implements Serializable {
    public int x = 0;
    public int y = 0;
    public Direction direction = Direction.NONE;
    public int tankId = 0;

    public static ArrayList<TransformBullet> fromBulletList(ArrayList<Bullet> bullets) {
        ArrayList<TransformBullet> transformBullets = new ArrayList<>();
        for (Bullet bullet : bullets) {
            TransformBullet transformBullet = new TransformBullet();
            transformBullet.x = bullet.x;
            transformBullet.y = bullet.y;
            transformBullet.direction = bullet.direction;
            transformBullet.tankId = bullet.tankId;
            transformBullets.add(transformBullet);
        }
        return transformBullets;
        }
        
    public static ArrayList<Bullet> toBulletList(ArrayList<TransformBullet> transformBullets) {
        ArrayList<Bullet> bullets = new ArrayList<>();
        for (TransformBullet transformBullet : transformBullets) {
            Bullet bullet = new Bullet(transformBullet.x, transformBullet.y, transformBullet.direction, transformBullet.tankId);
            bullets.add(bullet);
        }
        return bullets;
    }
}