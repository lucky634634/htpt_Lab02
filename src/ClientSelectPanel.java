import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClientSelectPanel extends JPanel {
    private JTextField _serverAddrField = new JTextField(20);
    private JTextField _serverPortField = new JTextField("5000", 20);
    private JTextField _clientNameField = new JTextField(20);
    private JButton _connectButton = new JButton("Connect");

    public ClientSelectPanel() {
        setLayout(new GridLayout(4, 1));

        JPanel serverAddrPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        add(serverAddrPanel);
        JLabel serverAddrLabel = new JLabel("Server Address:", JLabel.LEFT);
        serverAddrPanel.add(serverAddrLabel);
        serverAddrPanel.add(_serverAddrField);

        JPanel serverPortPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        add(serverPortPanel);
        JLabel serverPortLabel = new JLabel("Server Port:        ", JLabel.LEFT);
        serverPortPanel.add(serverPortLabel);
        serverPortPanel.add(_serverPortField);

        JPanel clientPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        add(clientPanel);
        JLabel clientName = new JLabel("Name:                   ", JLabel.LEFT);
        clientPanel.add(clientName);
        clientPanel.add(_clientNameField);

        add(_connectButton);

    }
}