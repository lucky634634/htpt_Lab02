import java.util.LinkedList;
import java.util.Queue;

public class InputQueue {
    private final Queue<Input> _buffer = new LinkedList<>();

    private static InputQueue _instance = null;

    private InputQueue() {
    }

    public static InputQueue GetInstance() {
        if (_instance == null) {
            synchronized (InputQueue.class) {
                _instance = new InputQueue();
            }
        }
        return _instance;
    }

    public void Add(Input input) {
        synchronized (_buffer) {
            _buffer.add(input);
        }
    }

    public void Resolve() {
        Queue<Input> buffer = new LinkedList<>();
        synchronized (_buffer) {
            buffer.addAll(_buffer);
            _buffer.clear();
        }
        while (!buffer.isEmpty()) {
            Input input = buffer.poll();
            if (input != null) {
                Tank t = TankManager.GetInstance().GetTank(input.id);
                if (t != null) {
                    t.Move(input.dir);
                    if (input.shoot) {
                        t.Shoot();
                    }
                    continue;
                }
            }
        }
    }
}