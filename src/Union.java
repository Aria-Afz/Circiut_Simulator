import java.util.ArrayList;
import java.util.HashSet;

public class Union {
    byte name;
    ArrayList<Node> nodes = new ArrayList<>();
    HashSet<Element> elements = new HashSet<>();
    ArrayList<Double> storedVoltages = new ArrayList<>();

    Union(Node main, byte name) {
        nodes.add(main);
        this.name = name;
    }

    void elementCheck() {
        if (nodes.size() > 1)
            for (Node e : nodes)
                for (Element ele : e.elementNeighbours)
                    if (!elements.contains(ele)) {
                        char k = ele.name.charAt(0);
                        if (k == 'V' || k == 'E' || k == 'H') {
                            Node e1 = ele.negativeNode;
                            Node e2 = ele.positiveNode;
                            if ((e1 == e && nodes.contains(e2)) || (e2 == e && nodes.contains(e1)))
                                elements.add(ele);
                        }
                }
    }

    void update(int cycle, double dt) {
        HashSet<Node> updated = new HashSet<>();
        for (Node e: nodes)
            e.visited = false;
        nodes.get(0).storedVoltages.add(cycle, storedVoltages.get(cycle));
        updated.add(nodes.get(0));
        nodes.get(0).visited = true;
        while (updated.size() != nodes.size()) {
            HashSet<Node> updatedBackup = new HashSet<>(updated);
            for(Node e : updatedBackup)
                for (Element ele : e.elementNeighbours)
                    if (elements.contains(ele)) {
                        if (ele.positiveNode == e) {
                            if (!ele.negativeNode.visited) {
                                Node a = ele.negativeNode;
                                a.storedVoltages.add(cycle, ele.getVoltage(cycle, dt) - e.getVoltage(cycle));
                                updated.add(a);
                                a.visited = true;
                            }
                        } else
                            if (!ele.positiveNode.visited) {
                                Node a = ele.positiveNode;
                                a.storedVoltages.add(cycle, ele.getVoltage(cycle, dt) + e.getVoltage(cycle));
                                updated.add(a);
                                a.visited = true;
                            }
                    }
        }
    }
}
