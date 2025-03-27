import javax.swing.*;
import java.awt.*;

public class Console extends JPanel {
    private JTextArea console;

    private void updateConsole(String message) {
        SwingUtilities.invokeLater(() -> console.append(message + "\n"));
    }
    
    public Console() {
        setLayout(new BorderLayout());
        console = new JTextArea(10, 30);
        console.setEditable(false);
        JScrollPane consoleScroll = new JScrollPane(console);
        add(consoleScroll);
    }

    public void log(String message) {
        updateConsole(message);
    }
}
