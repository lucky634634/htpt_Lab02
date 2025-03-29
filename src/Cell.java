
import java.awt.Graphics;

public class Cell {
    public int x;
    public int y;
    public final boolean[] walls = { true, true, true, true };

    public Cell(int cellX, int cellY) {
        this.x = cellX;
        this.y = cellY;
    }

    public void Draw(Graphics g) {

    }
}