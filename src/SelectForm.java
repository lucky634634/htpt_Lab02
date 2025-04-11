import java.awt.GridLayout;

import javax.swing.JFrame;

public class SelectForm extends JFrame {
    private final ServerSelectPanel _serverPanel = new ServerSelectPanel(this);
    private final ClientSelectPanel _clientPanel = new ClientSelectPanel(this);

    public SelectForm() {
        super();
        // setSize(800, 600);
        setFocusable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

        setLayout(new GridLayout(1, 2));

        add(_serverPanel);
        add(_clientPanel);

        pack();
    }
}