import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class GameInput extends KeyAdapter {
    private final Map<Integer, Boolean> _keyCurrent = new HashMap<>();
    private final Map<Integer, Boolean> _keyPrevious = new HashMap<>();

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key pressed: " + e.getKeyCode());
        _keyCurrent.put(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        _keyCurrent.put(e.getKeyCode(), false);
    }

    public boolean GetKey(int key) {
        return _keyCurrent.getOrDefault(key, false);
    }

    public boolean GetKeyDown(int key) {
        return _keyCurrent.getOrDefault(key, false) && !_keyPrevious.getOrDefault(key, false);
    }

    public boolean GetKeyUp(int key) {
        return !_keyCurrent.getOrDefault(key, false) && _keyPrevious.getOrDefault(key, false);
    }

    public void UpdateKey() {
        _keyPrevious.clear();
        _keyPrevious.putAll(_keyCurrent);
    }
}