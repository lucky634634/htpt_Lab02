
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class Maze {
    public final ArrayList<Cell> cells = new ArrayList<>();
    private final Random _random = new Random();

    public void Generate(int width, int height) {
        cells.clear();
        for (int i = 0; i < width * height; i++) {
            cells.add(new Cell(i % width, i / width));
        }
    }

    public void Draw(Graphics g) {
        g.setColor(Color.WHITE);
    }
}
