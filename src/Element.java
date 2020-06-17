import java.util.ArrayList;

class Element {
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

	public double getCurrent(int cycle, int dt) {
		if(name.charAt(0) == 'R')
			return getVoltage(cycle) / value;
		else if(name.charAt(0) == 'I')
			return value;
		else if(name.charAt(0) == 'L') {
			double i = 0;
			for(int j = 0; j < cycle; j++)
				i += getVoltage(j);
			return i / value;
		} else if (name.charAt(0) == 'C') {
			if (cycle == 0)
				return 0;
			return (getVoltage(cycle) - getVoltage(cycle - 1)) / dt;
		}
		return 0;
	}
	
}
