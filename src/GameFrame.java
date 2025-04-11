
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameFrame extends JFrame implements Runnable {
    private GamePanel _gamePanel;
    private final ConsolePanel _consolePanel = new ConsolePanel();
    private final ScorePanel _scorePanel = new ScorePanel();

    public GameFrame(boolean isHost) {
        // add(new GamePanel());
        // pack();
        setLayout(new BorderLayout());
        _gamePanel = new GamePanel(isHost);
        add(_gamePanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(Setting.MAZE_WIDTH * Setting.MAZE_UNIT, 200));
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

    @Override
    public void run() {
        _gamePanel.Setup();
        _consolePanel.Init();
        _gamePanel.Run();
    }
}
