import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ServerSelectPanel extends JPanel {

    private final JTextField _nameField = new JTextField(20);
    private final JTextField _portField = new JTextField("5000", 20);
    private final JButton _startButton = new JButton("Start Server");

    public ServerSelectPanel() {
        setLayout(new GridLayout(4, 1));

        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        add(namePanel);
        JLabel nameLabel = new JLabel("Name:           ", JLabel.LEFT);
        namePanel.add(nameLabel);
        namePanel.add(_nameField);

        JPanel portPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        add(portPanel);
        JLabel portLabel = new JLabel("Server Port:", JLabel.LEFT);
        portPanel.add(portLabel);
        portPanel.add(_portField);
        add(new JLabel(""));

        _startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (_nameField.getText().isEmpty() || _portField.getText().isEmpty()
                        || !CheckNumber(_portField.getText()))
                    return;

                System.out.println(
                        "Starting server with name: " + _nameField.getText() + " and port: "
                                + ConvertStringToInt(_portField.getText()));
            }
        });
        add(_startButton);
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