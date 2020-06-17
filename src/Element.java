import java.util.ArrayList;

abstract class Element {
	public String name;
	public Node positiveNode;
	public Node negativeNode;
	public double value;
	public double current;
	public ArrayList<Double> storedVoltages = new ArrayList<>();
	public ArrayList<Double> storedCurrents = new ArrayList<>();
	
	Element (String name, String positiveNode, String negativeNode, double value) {
		this.name = name;
		this.positiveNode = new Node(positiveNode);
		this.negativeNode = new Node(negativeNode);
		this.value = value;
	}
	
	public double getVoltage(int cycle) { return storedVoltages.get(cycle); }
	
}
