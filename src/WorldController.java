public class WorldController {
    public int seed = -1;
    private static WorldController _instance = null;

    public static WorldController GetInstance() {
        if (_instance == null) {
            synchronized (WorldController.class) {
                _instance = new WorldController();
            }
        }
        return _instance;
    }

    public void Setup() {
        seed = (int) (Math.random() * 100000);
        Maze.GetInstance().Generate(seed);
        TankManager.GetInstance().Clear();
        BulletManager.GetInstance().Clear();
    }
}
