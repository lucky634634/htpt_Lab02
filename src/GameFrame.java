
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

public class GameFrame extends JFrame {
    public GameFrame() {
        // add(new GamePanel());
        // pack();
        setSize(GamePanel.MAZE_WIDTH * GamePanel.MAZE_UNIT, GamePanel.MAZE_HEIGHT *
                GamePanel.MAZE_UNIT + 200);

        setLayout(new BorderLayout());
        add(new GamePanel(), BorderLayout.NORTH);

        JTextArea consoleLog = new JTextArea();
        JScrollPane consoleScroll = new JScrollPane(consoleLog);

        String[] columns = { "Player", "Score" };
        Object[][] data = { { "A", "0" }, { "B", "0" } };
        JTable table = new JTable(data, columns);
        JScrollPane tableScroll = new JScrollPane(table);

        // Split Console and Table
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                consoleScroll, tableScroll);
        splitPane.setDividerLocation(400);
        add(splitPane, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setTitle("Zace game");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }
}
