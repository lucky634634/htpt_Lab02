import java.awt.Color;
import java.awt.Graphics;

public class Bullet {
    public int x = 0;
    public int y = 0;
    public Direction direction = Direction.NONE;

    private final Color _color = Color.YELLOW;

    public Bullet(int x, int y, Direction direction) {
    }

    public void Draw(Graphics g) {
    }
}