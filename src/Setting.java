
import java.awt.event.KeyEvent;

public class Setting {
    public static final int MAZE_WIDTH = 32;
    public static final int MAZE_HEIGHT = 16;
    public static final int MAZE_UNIT = 32;

    public static final int FPS = 60;
    public static final long TARGET_DELTA_TIME = (long) (1000f / FPS);

    public static final int BULLET_SIZE = MAZE_UNIT / 4;

    public static final float TANK_SPEED = 2f;
    public static final float TANK_FIRE_RATE = TANK_SPEED;

    public static final float BULLET_SPEED = TANK_SPEED * 4;

    public static final int KEY_UP = KeyEvent.VK_UP;
    public static final int KEY_DOWN = KeyEvent.VK_DOWN;
    public static final int KEY_LEFT = KeyEvent.VK_LEFT;
    public static final int KEY_RIGHT = KeyEvent.VK_RIGHT;
    public static final int KEY_FIRE = KeyEvent.VK_SPACE;
    public static final int KEY_QUIT = KeyEvent.VK_ESCAPE;

    public static final String LOG_FILE = "log/log.txt";

    public static final String TANK_IMAGE = "assets/tank1.png";
    public static final String ENEMY_IMAGE = "assets/tank2.png";
}