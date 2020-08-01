import java.util.ArrayList;
import java.util.HashSet;

public class Union {
    byte name;
    ArrayList<Node> nodes = new ArrayList<>();
    Node main;
    HashSet<Element> elements = new HashSet<>();
    double voltage = 0;

    Union(Node main, byte name) {
        nodes.add(main);
        this.name = name;
        this.main = main;
    }

    void elementCheck() {
        if (nodes.size() > 1)
            for (Node e : nodes)
                for (Element ele : e.elementNeighbours)
                    if (!elements.contains(ele)) {
                        char k = ele.getName().charAt(0);
                        if (k == 'V' || k == 'E' || k == 'H') {
                            Node e1 = ele.getNegativeNode();
                            Node e2 = ele.getPositiveNode();
                            if ((e1 == e && nodes.contains(e2)) || (e2 == e && nodes.contains(e1)))
                                elements.add(ele);
                        }
                }
    }

    void update(int i, double dt) {
        for (Node e : nodes) {

        }
    }

    public byte getName() { return name; }
}
