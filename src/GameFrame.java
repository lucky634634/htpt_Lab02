
import javax.swing.JFrame;

public class GameFrame extends JFrame {
    public GameFrame() {
        add(new GamePanel());
        pack();
        setLocationRelativeTo(null);
        setTitle("Zace game");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }
}
