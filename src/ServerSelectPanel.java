import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ServerSelectPanel extends JPanel {

    private final JTextField _addressField = new JTextField(20);
    private final JTextField _portField = new JTextField(20);
    private final JButton _startButton = new JButton("Start Server");

    public ServerSelectPanel() {
        setLayout(new GridLayout(3, 1));

        JPanel addressPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        add(addressPanel);
        JLabel addressLabel = new JLabel("Server Address:", JLabel.LEFT);
        addressPanel.add(addressLabel);
        addressPanel.add(_addressField);

        JPanel portPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        add(portPanel);
        JLabel portLabel = new JLabel("Server Port:        ", JLabel.LEFT);
        portPanel.add(portLabel);
        portPanel.add(_portField);
        add(_startButton);
    }
}