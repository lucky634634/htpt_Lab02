
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

public class Maze {
    public final ArrayList<Cell> cells = new ArrayList<>();
    private int width = 32, height = 16;

    public void Generate(int width, int height, int seed) {
        cells.clear();
        this.width = width;
        this.height = height;
        for (int i = 0; i < width * height; i++) {
            cells.add(new Cell(i % width, i / width));
        }
        Random random = new Random(seed);
        GenerateMaze(random);
    }

    public void Draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width * GamePanel.MAZE_UNIT, height * GamePanel.MAZE_UNIT);
        if (width == 0 || height == 0)
            return;
        for (Cell cell : cells) {
            cell.Draw(g);
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
        if (cell.x > 0 && !visited.contains(cells.get(cell.y * width + cell.x - 1)))
            neighbors.add(cells.get(cell.y * width + cell.x - 1));

        if (cell.x < width - 1 && !visited.contains(cells.get(cell.y * width + cell.x + 1)))
            neighbors.add(cells.get(cell.y * width + cell.x + 1));

        if (cell.y > 0 && !visited.contains(cells.get((cell.y - 1) * width + cell.x)))
            neighbors.add(cells.get((cell.y - 1) * width + cell.x));

        if (cell.y < height - 1 && !visited.contains(cells.get((cell.y + 1) * width + cell.x)))
            neighbors.add(cells.get((cell.y + 1) * width + cell.x));

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
                return !cells.get(y * this.width + x).walls[0];
            }
            case DOWN -> {
                return !cells.get(y * this.width + x).walls[1];
            }
            case LEFT -> {
                return !cells.get(y * this.width + x).walls[2];
            }
            case RIGHT -> {
                return !cells.get(y * this.width + x).walls[3];
            }
            default -> {
            }
        }
        return true;
    }
}
