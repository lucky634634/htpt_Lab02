
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameFrame extends JFrame {
    private final GamePanel _gamePanel = new GamePanel();
    private final ConsolePanel _consolePanel = new ConsolePanel();
    private final ScorePanel _scorePanel = new ScorePanel();

    public GameFrame() {
        // add(new GamePanel());
        // pack();
        setLayout(new BorderLayout());
        add(_gamePanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(GamePanel.MAZE_WIDTH * GamePanel.MAZE_UNIT, 200));
        bottomPanel.setLayout(new GridLayout(1, 2));
        bottomPanel.add(_consolePanel);
        bottomPanel.add(_scorePanel);
        add(bottomPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setTitle("Zace game");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    public void Run() {
        _gamePanel.Init();
        _gamePanel.Run();
        _scorePanel.Init();
        _consolePanel.Init();
    }
}
