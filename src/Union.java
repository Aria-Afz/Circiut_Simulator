import java.util.ArrayList;

public class Union {
    byte name;
    ArrayList<Node> nodes = new ArrayList<>();
    Node main;
    double voltage = 0;

    Union(Node main, byte name) {
        nodes.add(main);
        this.name = name;
        this.main = main;
    }

    public void update(int i, double dt) {
//        for (Node e : nodes) {
//            if (main == e)
//                main.storedVoltages.add(voltage);
//            else
//                for (Element ele : e.elementNeighbours)
//
//        }
    }

    public byte getName() { return name; }
}
