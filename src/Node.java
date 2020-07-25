import java.util.ArrayList;
import java.util.HashMap;

public class Node {
	final private byte name;
	//private boolean visited = false;
	ArrayList<Double> storedVoltages = new ArrayList<>();
	private HashMap<Byte, String> neighbours = new HashMap<>();
	
	Node (byte name) {
		this.name = name;
		storedVoltages.add((double) 0);
	}

	public void setNeighbours(HashMap<Byte, String> neighbours) { this.neighbours = neighbours; }

	public HashMap<Byte, String> getNeighbours() { return neighbours; }

	public byte getName() { return name; }

	public double getVoltage(int cycle) { return storedVoltages.get(cycle); }

//	public ArrayList<Double> getStoredVoltage() { return storedVoltages; }
//
//	public ArrayList<Double> setStoredVoltage()

//	public boolean getVisited() { return visited; }
//
//	public void setVisited(boolean visited) { this.visited = visited; }
}
