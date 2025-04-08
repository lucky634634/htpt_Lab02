
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

public class TankManager {
    public ArrayList<Tank> tanks = new ArrayList<>();

    private static TankManager _instance = null;

    private Runnable onChangeCallback; // Callback to notify changes

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

    public Tank AddTank(Tank tank) {
        tanks.add(tank);
        tank.Init(0, 0);
        return tank;
    }

    public Tank CreateTank(int x, int y, Image image, String name) {
        Tank tank = new Tank(image, name);
        tank.Init(x, y);
        tanks.add(tank);
        return tank;
    }

    public Tank CreateTank(Image image, String name) {
        Tank tank = new Tank(image, name);
        tanks.add(tank);
        return tank;
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

    public void HandleInput(int port, String input)
    {
        if (input.equals("shoot")) {
            TankManager.GetInstance().GetTank(port - Server.PORT).Shoot();
        } else if (input.equals("up")) {
            TankManager.GetInstance().GetTank(port - Server.PORT).Move(Direction.UP);
        } else if (input.equals("down")) {
            TankManager.GetInstance().GetTank(port - Server.PORT).Move(Direction.DOWN);
        } else if (input.equals("left")) {
            TankManager.GetInstance().GetTank(port - Server.PORT).Move(Direction.LEFT);
        } else if (input.equals("right")) {
            TankManager.GetInstance().GetTank(port - Server.PORT).Move(Direction.RIGHT);
        }
        notifyChange();
    }
    
    private void notifyChange() {
        if (onChangeCallback != null) {
            onChangeCallback.run();
        }
    }

        public void setOnChangeCallback(Runnable callback) {
        this.onChangeCallback = callback;
    }
}


