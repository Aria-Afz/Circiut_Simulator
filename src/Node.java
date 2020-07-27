import java.util.ArrayList;

public class Node {
	final private byte name;
	boolean visited;
	ArrayList<Double> storedVoltages = new ArrayList<>();
	ArrayList<Element> neighbours = new ArrayList<>();
	
	Node (byte name) {
		this.name = name;
		storedVoltages.add(0.0d);
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
