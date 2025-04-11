
import java.awt.Graphics;
import java.util.ArrayList;

public class TankManager {
    public ArrayList<Tank> tanks = new ArrayList<>();

    private static TankManager _instance = null;

    private TankManager() {
    }

    public static TankManager GetInstance() {
        if (_instance == null) {
            synchronized (TankManager.class) {
                if (_instance == null)
                    _instance = new TankManager();
            }
        }
        return _instance;
    }

    public synchronized Tank CreateTank(int id, String name) {
        Tank tank = new Tank(id, name);
        tank.tankType = id == 0 ? TankType.PLAYER : TankType.ENEMY;
        tanks.add(tank);
        return tank;
    }

    public synchronized Tank GetTank(int id) {
        for (Tank tank : tanks) {
            if (tank.id == id) {
                return tank;
            }
        }
        return null;
    }

    public synchronized void RemoveTank(Tank tank) {
        tanks.remove(tank);
    }

    public synchronized void RemoveTank(int id) {
        for (int i = 0; i < tanks.size(); i++) {
            if (tanks.get(i).id == id) {
                tanks.remove(i);
                return;
            }
        }
    }

    public synchronized void Clear() {
        tanks.clear();
    }

    public synchronized void Update(float deltaTime) {
        for (Tank tank : tanks) {
            tank.Update(deltaTime);
        }
    }

    public synchronized void Draw(Graphics g) {
        if (tanks.isEmpty())
            return;
        for (Tank tank : tanks) {
            tank.Draw(g);
        }
    }

    public synchronized Tank GetTankByPosition(int x, int y) {
        for (Tank tank : tanks) {
            if (tank.x == x && tank.y == y) {
                return tank;
            }
        }
        return null;
    }

    public synchronized int GetIndex(Tank tank) {
        if (tank == null)
            return -1;
        return tanks.indexOf(tank);
    }

    public synchronized void SpawnRandom(Tank tank) {
        boolean found = false;
        tank.SetPosition(-1, -1);
        do {
            int x = (int) (Math.random() * Setting.MAZE_WIDTH);
            int y = (int) (Math.random() * Setting.MAZE_HEIGHT);
            if (GetTankByPosition(x, y) != null) {
                continue;
            }
            found = true;
            tank.SetPosition(x, y);
        } while (!found);
    }

    public synchronized void SetTankList(Transform[] ttl) {
        if (ttl == null || ttl.length == 0)
            return;
        tanks.clear();
        for (Transform t : ttl) {
            Tank tank = new Tank(t.id, ScoreManager.GetInstance().GetName(t.id));
            tank.tankType = ScoreManager.GetInstance().id == t.id ? TankType.PLAYER : TankType.ENEMY;
            tank.SetPosition(t.x, t.y);
            tank.direction = t.direction;
            tanks.add(tank);
        }

    }

    public synchronized Transform[] GetTankList() {
        Transform[] ttl = new Transform[tanks.size()];
        for (int i = 0; i < tanks.size(); i++) {
            ttl[i] = new Transform(tanks.get(i).id, tanks.get(i).x, tanks.get(i).y, tanks.get(i).direction);
        }
        return ttl;
    }
}