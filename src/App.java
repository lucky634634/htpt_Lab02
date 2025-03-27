import javax.swing.*;
import java.awt.*;
public class App {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Zace game");
        Maze mazePanel = new Maze();
        Console consolePanel = new Console();
        consolePanel.setBorder(BorderFactory.createTitledBorder("Console"));
        Score scorePanel = new Score();
        scorePanel.setBorder(BorderFactory.createTitledBorder("Score"));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 2));
        bottomPanel.setPreferredSize(new Dimension(
            mazePanel.COLS * mazePanel.CELL_SIZE,
            200)
        );
        bottomPanel.add(consolePanel);
        bottomPanel.add(scorePanel);

        frame.add(mazePanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        consolePanel.log("Welcome to Zace game!");
    }
}
