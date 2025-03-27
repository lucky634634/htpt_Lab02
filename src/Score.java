import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class Score extends JPanel {
    private JTable scoreTable;
    String[][] scoreData = {
            {"Player1", "10", "Human"},
            {"Robot1", "10", "Robot"},
    };

    public Score() {
        setLayout(new BorderLayout());
        String[] columnNames = {"Name", "Score", "Type"};
        DefaultTableModel model = new DefaultTableModel(scoreData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        scoreTable = new JTable(model);
        JScrollPane scoreScroll = new JScrollPane(scoreTable);
        add(scoreScroll, BorderLayout.CENTER);
    }

    public void updateScore(String name, int score) {
        for (int i = 0; i < scoreData.length; i++) {
            if (scoreData[i][0].equals(name)) {
                scoreData[i][1] = String.valueOf(score);
                break;
            }
        }
    }
}