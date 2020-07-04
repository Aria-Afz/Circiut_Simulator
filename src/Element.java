import java.util.ArrayList;

class Element {

	public String name;
	public Node positiveNode;
	public Node negativeNode;
	public double value;
	public double current;
	public ArrayList<Double> storedVoltages = new ArrayList<>();
	public ArrayList<Double> storedCurrents = new ArrayList<>();
	
	Element (String name, Node positiveNode, Node negativeNode, double value) {
		this.name = name;
		this.positiveNode = positiveNode;
		this.negativeNode = negativeNode;
		this.value = value;
	}
	
	public double getVoltage(int cycle) { return storedVoltages.get(cycle); }

	public double getCurrent(int cycle, int dt) {
		switch (name.charAt(0)) {
			case 'R':
				return getVoltage(cycle) / value;
			case 'I':
				return value;
			case 'L':
				double i = 0;
				for(int j = 0; j < cycle; j++)
					i += getVoltage(j);
				return i / value;
			case 'C':
				if (cycle == 0)
					return 0;
				return (getVoltage(cycle) - getVoltage(cycle - 1)) / dt;
		}
		return 0;
	}
	
}
