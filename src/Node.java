import java.util.ArrayList;
import java.util.HashMap;

public class Node {
	private double voltage;
	final private String name;
	private ArrayList<Double> storedVoltages = new ArrayList<>();
	private HashMap<Node, Element> neighbours = new HashMap<>();
	
	Node (String name) {
		this.name = name;
	}

	public HashMap<Node, Element> getNeighbours() { return neighbours; }

	public String getName() { return name; }
}
