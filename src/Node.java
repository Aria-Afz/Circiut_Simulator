import java.util.ArrayList;
import java.util.HashMap;

public class Node {
	final private String name;
	//private boolean visited = false;
	private ArrayList<Double> storedVoltages = new ArrayList<>();
	private HashMap<String, String> neighbours = new HashMap<>();
	
	Node (String name) {
		this.name = name;
		storedVoltages.add((double) 0);
	}

	public void setNeighbours(HashMap<String, String> neighbours) { this.neighbours = neighbours; }

	public HashMap<String, String> getNeighbours() { return neighbours; }

	public String getName() { return name; }

	public double getVoltage(int cycle) { return storedVoltages.get(cycle); }

	public ArrayList<Double> getStoredVoltage() { return storedVoltages; }

//	public boolean getVisited() { return visited; }
//
//	public void setVisited(boolean visited) { this.visited = visited; }
}
