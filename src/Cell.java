public class Cell {
    int row, col;
    boolean topWall = true;
    boolean rightWall = true;
    boolean bottomWall = true;
    boolean leftWall = true;
    // Đã xét hay chưa, phục vụ cho thuật toán sinh Maze
    boolean visited = false;
    
    Cell(int row, int col){
        this.row = row;
        this.col = col;
    }
}