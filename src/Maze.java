import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Maze extends JPanel implements ActionListener{
    final int COLS = 32;
    final int ROWS = 16;
    final int CELL_SIZE = 30;
    Cell[][] cells = new Cell[ROWS][COLS];
    static final int DELAY = 75;
    Tank player = new Tank(0, 0, 0, 0);

    public Maze() {
        this.setPreferredSize(new Dimension(COLS * CELL_SIZE, ROWS * CELL_SIZE));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        // Khởi tạo ma trận cells với mỗi cell có đầy đủ 4 tường
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                cells[r][c] = new Cell(r, c);
            }
        }
        generateMaze();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, COLS * CELL_SIZE, ROWS * CELL_SIZE);
        // Vẽ tường chưa bị xóa
        g.setColor(Color.BLACK);
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                int x = c * CELL_SIZE;
                int y = r * CELL_SIZE;
                if(cells[r][c].topWall) {
                    g.drawLine(x, y, x + CELL_SIZE, y);
                }
                if(cells[r][c].rightWall) {
                    g.drawLine(x + CELL_SIZE, y, x + CELL_SIZE, y + CELL_SIZE);
                }
                if(cells[r][c].bottomWall) {
                    g.drawLine(x + CELL_SIZE, y + CELL_SIZE, x, y + CELL_SIZE);
                }
                if(cells[r][c].leftWall) {
                    g.drawLine(x, y + CELL_SIZE, x, y);
                }
            }
        }
        Graphics2D g2d = (Graphics2D) g;
        int tankX = player.getCell().col * CELL_SIZE;
        int tankY = player.getCell().row * CELL_SIZE;

        // Calculate the center of the tank
        int centerX = tankX + CELL_SIZE / 2;
        int centerY = tankY + CELL_SIZE / 2;

        // Save the original transform
        AffineTransform originalTransform = g2d.getTransform();

        // Rotate based on direction
        switch (player.getDirection()) {
            case 'U':
                break;
            case 'L':
                g2d.rotate(Math.toRadians(270), centerX, centerY);
                break;
            case 'D':
                g2d.rotate(Math.toRadians(180), centerX, centerY);
                break;
            case 'R':
                g2d.rotate(Math.toRadians(90), centerX, centerY);
                break;
        }

        // Draw the tank image
        g2d.drawImage(player.getTankImage(), tankX, tankY, CELL_SIZE, CELL_SIZE, null);

        // Restore the original transform
        g2d.setTransform(originalTransform);
    }

    public void move(){
        switch (player.getDirection()) {
            case 'U':
                if(player.getCell().row >= 0 && !cells[player.getCell().row][player.getCell().col].topWall) {
                    player.getCell().row--;
                }
                break;
            case 'D':
                if(player.getCell().row <= ROWS - 1 && !cells[player.getCell().row][player.getCell().col].bottomWall) {
                    player.getCell().row++;
                }
                break;
            case 'L':
                if(player.getCell().col >= 0 && !cells[player.getCell().row][player.getCell().col].leftWall) {
                    player.getCell().col--;
                }
                break;
            case 'R':
                if(player.getCell().col <= COLS - 1 && !cells[player.getCell().row][player.getCell().col].rightWall) {
                    player.getCell().col++;
                }
                break;
        }
    }

    // Thuật toán sinh Maze dựa trên Depth First Search
    private void generateMaze() {
        // Sử dụng stack để lưu các cell có thể đi tiếp
        Stack<Cell> stack = new Stack<>();
        Cell current = cells[0][0];
        current.visited = true;

        do {
            // Lấy ngẫu nhiên 1 cell kề chưa xét
            Cell next = getUnvisitedNeighbor(current);
            if(next != null) {
                next.visited = true;
                stack.push(current);
                removeWalls(current, next);
                current = next;
            } else {
                // Nếu không có cell kề chưa xét thì quay lại cell trước đó
                current = stack.pop();
            }
        } while (!stack.isEmpty());
    }

    // Hàm lấy ngẫu nhiên 1 cell kề chưa xét
    private Cell getUnvisitedNeighbor(Cell cell) {
        // Lưu các cell kề chưa xét để return random
        ArrayList<Cell> neighbors = new ArrayList<>();
        int row = cell.row;
        int col = cell.col;
        if(row > 0 && !cells[row - 1][col].visited) {
            neighbors.add(cells[row - 1][col]);
        }
        if(col < COLS - 1 && !cells[row][col+1].visited) {
            neighbors.add(cells[row][col+1]);
        }
        if(row < ROWS - 1 && !cells[row+1][col].visited) {
            neighbors.add(cells[row+1][col]);
        }
        if(col > 0 && !cells[row][col-1].visited) {
            neighbors.add(cells[row][col-1]);
        }
        if(neighbors.size() > 0) {
            Collections.shuffle(neighbors);
            return neighbors.get(0);
        } else {
            return null;
        }
    }

    // Hàm xóa tường giữa 2 cell liên tiếp (current và next)
    private void removeWalls(Cell current, Cell next) {
        int x = current.col - next.col;
        int y = current.row - next.row;

        if(x == 1) {
            current.leftWall = false;
            next.rightWall = false;
        } else if(x == -1) {
            current.rightWall = false;
            next.leftWall = false;
        } else if(y == 1) {
            current.topWall = false;
            next.bottomWall = false;
        } else if(y == -1) {
            current.bottomWall = false;
            next.topWall = false;
        }
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()){
                case KeyEvent.VK_UP:
                    player.setDirection('U');
                    break;
                case KeyEvent.VK_DOWN:
                    player.setDirection('D');
                    break;
                case KeyEvent.VK_LEFT:
                    player.setDirection('L');
                    break;
                case KeyEvent.VK_RIGHT:
                    player.setDirection('R');
                    break;
            }
        move();
        repaint();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }
}
