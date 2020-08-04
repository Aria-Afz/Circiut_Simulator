import java.util.ArrayList;
import java.util.HashSet;

public class Node {
	final byte name;
	boolean visited;
	byte union;
	ArrayList<Double> storedVoltages = new ArrayList<>();
	HashSet<Node> nodeNeighbours = new HashSet<>();
	HashSet<Element> elementNeighbours = new HashSet<>();
	
	Node (byte name) {
		this.name = name;
		union = name;
	}
	byte getName(){ return  name;}
	public double getVoltage(int cycle) { return storedVoltages.get(cycle); }
}
