import java.util.ArrayList;
import java.util.HashMap;

public class Node {
	final private String name;
	private ArrayList<Double> storedVoltages = new ArrayList<>();
	private HashMap<String, String> neighbours = new HashMap<>();
	
	Node (String name) {
		this.name = name;
	}

	public void setNeighbours(HashMap<String, String> neighbours) { this.neighbours = neighbours; }

	public HashMap<String, String> getNeighbours() { return neighbours; }

	public String getName() { return name; }

	public double getVoltage(int cycle) {return storedVoltages.get(cycle); }
}
