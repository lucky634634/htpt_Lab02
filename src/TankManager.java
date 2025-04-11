
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
        if (ttl.length > tanks.size()) {
            for (int i = tanks.size(); i < ttl.length; i++) {
                tanks.add(new Tank());
            }
        } else if (ttl.length < tanks.size()) {
            for (int i = ttl.length; i < tanks.size(); i++) {
                tanks.remove(tanks.size() - 1);
            }
        }

        for (int i = 0; i < ttl.length; i++) {
            TankType t = ScoreManager.GetInstance().id == ttl[i].id ? TankType.PLAYER : TankType.ENEMY;
            tanks.get(i).SetState(ttl[i].id, ttl[i].x, ttl[i].y, ttl[i].direction, t);
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