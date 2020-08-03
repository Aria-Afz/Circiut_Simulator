import java.util.ArrayList;
import java.util.HashSet;

public class Node {
	final private byte name;
	boolean visited;
	byte union;
	ArrayList<Double> storedVoltages = new ArrayList<>();
	HashSet<Node> nodeNeighbours = new HashSet<>();
	HashSet<Element> elementNeighbours = new HashSet<>();
	
	Node (byte name) {
		this.name = name;
		union = name;
	}

	public byte getName() { return name; }

	public double getVoltage(int cycle) { return storedVoltages.get(cycle); }

//	public ArrayList<Element> getNeighbours() { return neighbours; }
//
//	public void setNeighbours(ArrayList<Element> neighbours) { this.neighbours = neighbours; }

//	public ArrayList<Double> getStoredVoltage() { return storedVoltages; }
//
//	public ArrayList<Double> setStoredVoltage()

//	public boolean getVisited() { return visited; }
//
//	public void setVisited(boolean visited) { this.visited = visited; }
}
