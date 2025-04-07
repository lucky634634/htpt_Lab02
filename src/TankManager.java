
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class TankManager {
    public ArrayList<Tank> tanks = new ArrayList<>();

    private static TankManager _instance = null;
    private int _tankId = 0;

    private final Queue<Integer> _freeTankIds = new LinkedList<>();

    private TankManager() {
        _tankId = 0;
    }

    public static TankManager GetInstance() {
        if (_instance == null) {
            synchronized (TankManager.class) {
                _instance = new TankManager();
            }
        }
        return _instance;
    }

    public Tank CreateTank(int x, int y, Image image, String name) {
        Tank tank = CreateTank(image, name);
        tank.Init(x, y);
        return tank;
    }

    public Tank CreateTank(Image image, String name) {
        Tank tank = new Tank(_tankId, image, name);
        _tankId++;
        tanks.add(tank);
        return tank;
    }

    public Tank GetTank(int index) {
        return tanks.get(index);
    }

    public void RemoveTank(Tank tank) {
        tanks.remove(tank);
        _freeTankIds.add(_tankId);
    }

    public void Clear() {
        _tankId = 0;
        _freeTankIds.clear();
        tanks.clear();
    }

    public void Update(float deltaTime) {
        for (Tank tank : tanks) {
            tank.Update(deltaTime);
        }
    }

    public void Draw(Graphics g) {
        if (tanks.isEmpty())
            return;
        for (Tank tank : tanks) {
            tank.Draw(g);
        }
    }

    public Tank GetTankByPosition(int x, int y) {
        for (Tank tank : tanks) {
            if (tank.x == x && tank.y == y) {
                return tank;
            }
        }
        return null;
    }

    public int GetIndex(Tank tank) {
        if (tank == null)
            return -1;
        return tanks.indexOf(tank);
    }

    public void SpawnRandom(Tank tank) {
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
}