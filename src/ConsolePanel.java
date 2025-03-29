
import java.awt.BorderLayout;
import java.awt.Color;
import java.time.LocalTime;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ConsolePanel extends JPanel {
    private final JTextArea _consoleText = new JTextArea();

    public ConsolePanel() {
        // setEnabled(false);
        setFocusable(false);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Console"));
        JScrollPane consoleScroll = new JScrollPane(_consoleText);
        consoleScroll.setFocusable(false);
        add(consoleScroll, BorderLayout.CENTER);
        add(consoleScroll);
        _consoleText.setEditable(false);
        _consoleText.setEnabled(false);
        _consoleText.setDisabledTextColor(Color.BLACK);
    }

    public void Init() {
        _consoleText.setText("");
    }

    public void Log(String message) {
        LocalTime localTime = LocalTime.now();
        _consoleText.append("[" + localTime.toString() + "] " + message + "\n");
        _consoleText.setCaretPosition(_consoleText.getDocument().getLength());
    }
}