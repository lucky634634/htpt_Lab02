
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import javax.swing.ImageIcon;

public class Tank {
    public int x = 0;
    public int y = 0;

    public char direction = 'U'; // U D L R
    public Color color = Color.RED;

    private final Image _image = new ImageIcon("assets/tank1.png").getImage();
    private final int MAZE_UNIT = GamePanel.MAZE_UNIT;

    public final int SPEED = 2;

    public Tank(Color color) {
        this.color = color;
    }

    public void Draw(Graphics g) {
        g.setColor(color);
        int centerX = x * GamePanel.MAZE_UNIT + GamePanel.MAZE_UNIT / 2;
        int centerY = y * GamePanel.MAZE_UNIT + GamePanel.MAZE_UNIT / 2;
        Graphics2D g2d = (Graphics2D) g;

        AffineTransform originalTransform = g2d.getTransform();
        switch (direction) {
            case 'U' -> {
            }
            case 'L' -> g2d.rotate(Math.toRadians(270), centerX, centerY);
            case 'D' -> g2d.rotate(Math.toRadians(180), centerX, centerY);
            case 'R' -> g2d.rotate(Math.toRadians(90), centerX, centerY);
        }
        g2d.drawImage(_image, x * MAZE_UNIT, y * MAZE_UNIT, MAZE_UNIT, MAZE_UNIT, null);
        g2d.setTransform(originalTransform);
    }

    public void Move(Maze maze, char direction) {
    }

}