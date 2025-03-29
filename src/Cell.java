
import java.awt.Color;
import java.awt.Graphics;

public class Cell {
    public int x;
    public int y;
    // 0 - UP, 1 - DOWN, 2 - LEFT, 3 - RIGHT
    public final boolean[] walls = { true, true, true, true };

    private final Color _color = Color.BLACK;

    public Cell(int cellX, int cellY) {
        this.x = cellX;
        this.y = cellY;
    }

    public void Draw(Graphics g) {
        g.setColor(_color);
        int halfUnit = GamePanel.MAZE_UNIT / 2;
        int centerX = x * GamePanel.MAZE_UNIT + GamePanel.MAZE_UNIT / 2;
        int centerY = y * GamePanel.MAZE_UNIT + GamePanel.MAZE_UNIT / 2;
        if (walls[0])
            g.drawLine(centerX - halfUnit, centerY - halfUnit, centerX + halfUnit, centerY - halfUnit);
        if (walls[1])
            g.drawLine(centerX - halfUnit, centerY + halfUnit, centerX + halfUnit, centerY + halfUnit);
        if (walls[2])
            g.drawLine(centerX - halfUnit, centerY - halfUnit, centerX - halfUnit, centerY + halfUnit);
        if (walls[3])
            g.drawLine(centerX + halfUnit, centerY - halfUnit, centerX + halfUnit, centerY + halfUnit);
    }
}