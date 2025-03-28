
import java.util.ArrayList;
import java.util.Random;

public class Maze {
    public final ArrayList<Cell> cells = new ArrayList<>();
    private final Random _random = new Random();

    public void Generate(int width, int height) {
        cells.clear();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cells.add(new Cell(i, j));
            }
        }
    }
}