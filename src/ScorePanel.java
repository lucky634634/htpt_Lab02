
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ScorePanel extends JPanel {
    private final JTable _scoreTable;
    private final String[] _columnNames = { "Name", "Score", "Type" };

    public ScorePanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Score"));

        _scoreTable = new JTable();
        JScrollPane scoreScroll = new JScrollPane(_scoreTable);
        add(scoreScroll, BorderLayout.CENTER);
        // setEnabled(false);
        setFocusable(false);
        _scoreTable.setRowSelectionAllowed(false);
        _scoreTable.setColumnSelectionAllowed(false);
        _scoreTable.setCellSelectionEnabled(false);
        _scoreTable.setEnabled(false);
        _scoreTable.setForeground(Color.BLACK);
    }

    public void Init() {
        _scoreTable.setModel(new DefaultTableModel(null, _columnNames));
    }

    public void Update(ScoreObject[] scores) {
    }

}