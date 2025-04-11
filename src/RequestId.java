import java.io.Serializable;

public class RequestId implements Serializable {
    public String name;
    private final static long serialVersionUID = 1L;

    public RequestId(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RequestId{ " + "name='" + name + '\'' + '}';
    }
}