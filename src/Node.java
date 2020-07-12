import java.util.ArrayList;
import java.util.HashMap;

public class Node {
	private double voltage;
	final private String name;
	private ArrayList<Double> storedVoltages = new ArrayList<>();
	private HashMap<Node, String> neighbours = new HashMap<>();
	
	Node (String name) {
		voltage = 0;
		this.name = name;
	}

	public HashMap<Node, String> getNeighbours() { return neighbours; }

	public String getName() { return name; }

	public double getVoltage() {return voltage; }
}
