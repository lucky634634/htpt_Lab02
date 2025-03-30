
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
                _instance = new TankManager();
            }
        }
        return _instance;
    }

    public void AddTank(Tank tank) {
        tanks.add(tank);
    }

    public Tank GetTank(int index) {
        return tanks.get(index);
    }

    public void RemoveTank(Tank tank) {
        tanks.remove(tank);
    }

    public void Clear() {
        tanks.clear();
    }

    public void Update(float deltaTime) {
        for (Tank tank : tanks) {
            tank.Update(deltaTime);
        }
    }

    public void Draw(Graphics g) {
        for (Tank tank : tanks) {
            tank.Draw(g);
        }
    }
}