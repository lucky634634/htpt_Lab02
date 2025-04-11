import java.io.Serializable;

public class ResponseId implements Serializable {
    private final static long serialVersionUID = 1L;

    public int id = 0;

    public ResponseId(int id) {
        this.id = id;
    }

}