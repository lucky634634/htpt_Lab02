import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClientSelectPanel extends JPanel {
    private JTextField _serverAddrField = new JTextField("localhost", 20);
    private JTextField _serverPortField = new JTextField("5000", 20);
    private JTextField _clientNameField = new JTextField(20);
    private JButton _connectButton = new JButton("Connect");
    private JFrame _parent = null;

    public ClientSelectPanel(JFrame parent) {
        _parent = parent;
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
        _connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (_serverAddrField.getText().isEmpty() || _serverPortField.getText().isEmpty()
                        || _clientNameField.getText().isEmpty() || !CheckNumber(_serverPortField.getText()))
                    return;

                _parent.setVisible(false);
                String serverAddr = _serverAddrField.getText();
                int serverPort = ConvertStringToInt(_serverPortField.getText());
                String name = _clientNameField.getText();
                System.out.println("Connecting to server at: " + serverAddr + ":" + serverPort + " with name: " + name);

                Thread t = new Thread(new GameFrame(false));
                t.start();

                Client.GetInstance().Start(serverAddr, serverPort);
                Client.GetInstance().SendMessage(new RequestId(name));
            }
        });
        add(_connectButton);

    }

    private boolean CheckNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private int ConvertStringToInt(String str) {
        if (CheckNumber(str)) {
            return Integer.parseInt(str);
        } else {
            throw new IllegalArgumentException("Invalid input: " + str);
        }
    }
}