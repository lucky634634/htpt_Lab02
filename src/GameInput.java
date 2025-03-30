import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class GameInput extends KeyAdapter {
    private final Map<Integer, Boolean> _keyCurrent = new HashMap<>();

    @Override
    public void keyPressed(KeyEvent e) {
        _keyCurrent.put((e.getKeyCode()), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        _keyCurrent.put((e.getKeyCode()), false);
    }

    public boolean GetKey(int key) {
        return _keyCurrent.getOrDefault(key, false);
    }
}