
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

public class Maze {
    public final ArrayList<Cell> cells = new ArrayList<>();
    public int seed = -1;

    private static Maze _instance = null;

    public static Maze GetInstance() {
        if (_instance == null) {
            synchronized (Maze.class) {
                _instance = new Maze();
            }
        }
        return _instance;
    }

    private Maze() {
    }

    public void Generate(int seed) {
        this.seed = seed;
        cells.clear();
        for (int i = 0; i < Setting.MAZE_WIDTH * Setting.MAZE_HEIGHT; i++) {
            cells.add(new Cell(i % Setting.MAZE_WIDTH, i / Setting.MAZE_WIDTH));
        }
        Random random = new Random(seed);
        GenerateMaze(random);
    }

    public void Draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, Setting.MAZE_WIDTH * Setting.MAZE_UNIT, Setting.MAZE_HEIGHT * Setting.MAZE_UNIT);
        synchronized (cells) {
            for (Cell cell : cells) {
                cell.Draw(g);
            }
        }
    }

    private void GenerateMaze(Random random) {
        Stack<Cell> stack = new Stack<>();
        Queue<Cell> visited = new LinkedList<>();
        Cell current = cells.get(0);
        visited.add(current);

        do {
            Cell next = GetUnvisitedNeighbor(current, visited, random);
            if (next != null) {
                visited.add(next);
                stack.push(current);
                RemoveBetween(current, next);
                current = next;
            } else {
                current = stack.pop();
            }
        } while (!stack.isEmpty());

    }

    private Cell GetUnvisitedNeighbor(Cell cell, Queue<Cell> visited, Random random) {
        ArrayList<Cell> neighbors = new ArrayList<>();
        if (cell.x > 0 && !visited.contains(cells.get(cell.y * Setting.MAZE_WIDTH + cell.x - 1)))
            neighbors.add(cells.get(cell.y * Setting.MAZE_WIDTH + cell.x - 1));

        if (cell.x < Setting.MAZE_WIDTH - 1 && !visited.contains(cells.get(cell.y * Setting.MAZE_WIDTH + cell.x + 1)))
            neighbors.add(cells.get(cell.y * Setting.MAZE_WIDTH + cell.x + 1));

        if (cell.y > 0 && !visited.contains(cells.get((cell.y - 1) * Setting.MAZE_WIDTH + cell.x)))
            neighbors.add(cells.get((cell.y - 1) * Setting.MAZE_WIDTH + cell.x));

        if (cell.y < Setting.MAZE_HEIGHT - 1
                && !visited.contains(cells.get((cell.y + 1) * Setting.MAZE_WIDTH + cell.x)))
            neighbors.add(cells.get((cell.y + 1) * Setting.MAZE_WIDTH + cell.x));

        if (neighbors.isEmpty())
            return null;
        return neighbors.get(random.nextInt(neighbors.size()));
    }

    private void RemoveBetween(Cell a, Cell b) {
        int x = b.x - a.x;
        int y = b.y - a.y;
        if (x == 1) {
            a.walls[3] = false;
            b.walls[2] = false;
        } else if (x == -1) {
            a.walls[2] = false;
            b.walls[3] = false;
        } else if (y == 1) {
            a.walls[1] = false;
            b.walls[0] = false;
        } else if (y == -1) {
            a.walls[0] = false;
            b.walls[1] = false;
        }
    }

    public boolean CheckMove(int x, int y, Direction dir) {
        switch (dir) {
            case UP -> {
                return !cells.get(y * Setting.MAZE_WIDTH + x).walls[0];
            }
            case DOWN -> {
                return !cells.get(y * Setting.MAZE_WIDTH + x).walls[1];
            }
            case LEFT -> {
                return !cells.get(y * Setting.MAZE_WIDTH + x).walls[2];
            }
            case RIGHT -> {
                return !cells.get(y * Setting.MAZE_WIDTH + x).walls[3];
            }
            default -> {
            }
        }
        return true;
    }

    public Cell GetCell(int x, int y) {
        return cells.get(y * Setting.MAZE_WIDTH + x);
    }
}
