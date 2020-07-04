import java.util.ArrayList;

public class Node {
	double voltage;
	String name;
	ArrayList<Double> storedVoltages = new ArrayList<>();
	ArrayList<Node> neighbours = new ArrayList<>();
	
	Node (String name) {
		this.name = name;
	}
	
}
