import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClientSelectPanel extends JPanel {
    public ClientSelectPanel() {
        setLayout(new GridLayout(5, 1));

        JPanel serverAddrPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        add(serverAddrPanel);
        JLabel serverAddrLabel = new JLabel("Server Address:", JLabel.LEFT);
        serverAddrPanel.add(serverAddrLabel);
        JTextField serverAddrField = new JTextField(20);
        serverAddrPanel.add(serverAddrField);

        JPanel serverPortPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        add(serverPortPanel);
        JLabel serverPortLabel = new JLabel("Server Port:        ", JLabel.LEFT);
        serverPortPanel.add(serverPortLabel);
        JTextField serverPortField = new JTextField(20);
        serverPortPanel.add(serverPortField);

        JPanel clientPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        add(clientPanel);
        JLabel clientName = new JLabel("Name:                   ", JLabel.LEFT);
        clientPanel.add(clientName);
        JTextField clientNameField = new JTextField(20);
        clientPanel.add(clientNameField);

        JPanel portPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        add(portPanel);
        JLabel clientPort = new JLabel("Port:                      ", JLabel.LEFT);
        portPanel.add(clientPort);
        JTextField clientPortField = new JTextField(20);
        portPanel.add(clientPortField);

        JButton connectButton = new JButton("Connect");
        add(connectButton);

    }
}