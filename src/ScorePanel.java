
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
        setFocusable(false);
        setEnabled(false);
        _scoreTable.setRowSelectionAllowed(false);
        _scoreTable.setColumnSelectionAllowed(false);
        _scoreTable.setCellSelectionEnabled(false);
        _scoreTable.setEnabled(false);
        _scoreTable.setForeground(Color.BLACK);
        _scoreTable.setModel(new DefaultTableModel(new Object[][] { { "", "" } }, _columnNames));
        ScoreManager.GetInstance().AddScoreListener(this);
    }

    public synchronized void Update(ScoreObject[] scores) {
        if (scores == null || scores.length == 0) {
            return;
        }
        Object[][] data = new Object[scores.length][2];
        for (int i = 0; i < scores.length; i++) {
            data[i][0] = scores[i].name;
            data[i][1] = scores[i].score;
        }

        DefaultTableModel model = (DefaultTableModel) _scoreTable.getModel();
        model.setRowCount(0);
        for (int i = 0; i < data.length; i++) {
            model.addRow(data[i]);
        }
    }

    @Override
    public void OnScoreChange(ScoreObject[] scores) {
        Update(scores);
    }

}