
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ScorePanel extends JPanel implements ScoreListener {
    private final JTable _scoreTable;
    private final String[] _columnNames = { "Name", "Score" };

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
        _scoreTable.setModel(new DefaultTableModel(null, _columnNames));
        ScoreManager.GetInstance().AddScoreListener(this);
    }

    public synchronized void Update(ScoreObject[] scores) {
        DefaultTableModel model = (DefaultTableModel) _scoreTable.getModel();
        model.setRowCount(0);
        for (ScoreObject score : scores) {
            model.addRow(new Object[] { score.name, score.score });
        }
    }

    @Override
    public void OnScoreChange(ScoreObject[] scores) {
        Update(scores);
    }

}