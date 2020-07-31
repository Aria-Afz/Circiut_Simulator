import java.util.ArrayList;

public class Union {
    byte name;
    ArrayList<Node> nodes = new ArrayList<>();
    double voltage = 0;

    Union(Node e) {
        nodes.add(e);
    }

}
