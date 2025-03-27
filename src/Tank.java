import javax.swing.*;
import java.awt.*;

public class Tank {
    private Cell cell;
    private int direction;
    private Image tankImage;

    public Tank(int x, int y, int direction, int speed) {
        this.cell = new Cell(x, y);
        this.direction = direction;
        this.tankImage = new ImageIcon("tank1.png").getImage();
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public Image getTankImage() {
        return tankImage;
    }
}
