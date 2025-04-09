import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;


public class Transform implements Serializable {
    private static final long serialVersionUID = 1L;
    public int x = 0;
    public int y = 0;

    public Direction direction;

    public static ArrayList<Transform> fromTankList(ArrayList<Tank> tanks) {
        ArrayList<Transform> transforms = new ArrayList<>();
        for (Tank tank : tanks) {
            Transform transform = new Transform();
            transform.x = tank.x;
            transform.y = tank.y;
            transform.direction = tank.direction;
            transforms.add(transform);
        }
        return transforms;
        }
    
    public static ArrayList<Tank> toTankList(ArrayList<Transform> transforms, int id) {
        ArrayList<Tank> tanks = new ArrayList<>();
        Tank tank = null;
        for (int i = 0; i < transforms.size(); i++) {
            if(i == 0)
            tank = new Tank(new ImageIcon("assets/tank1.png").getImage(), "Host");
            else if (i == id + 1)
                {
                tank = new Tank(new ImageIcon("assets/tank1.png").getImage(), "Player " + i);}
            else
                tank = new Tank(new ImageIcon("assets/tank2.png").getImage(), "Player " + i);
            Transform transform = transforms.get(i);
            tank.x = transform.x;
            tank.y = transform.y;
            tank.direction = transform.direction;

            tanks.add(tank);
        }
        return tanks;
    }

    public static ArrayList<Transform> fromBulletList(ArrayList<Bullet> bullets) {
            ArrayList<Transform> transforms = new ArrayList<>();
            for (Bullet bullet : bullets) {
                Transform transform = new Transform();
                transform.x = bullet.x;
                transform.y = bullet.y;
                transform.direction = bullet.direction;
                transforms.add(transform);
            }
            return transforms;
            }

    public static ArrayList<Bullet> toBulletList(ArrayList<Transform> transforms) {
            ArrayList<Bullet> bullets = new ArrayList<>();
            for (Transform transform : transforms) {
                Bullet bullet = new Bullet(transform.x, transform.y, transform.direction, 0);
                bullets.add(bullet);
            }
            return bullets;
        }
}