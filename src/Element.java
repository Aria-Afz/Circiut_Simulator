import java.util.ArrayList;

class Element {
	private final String name;
	private final Node positiveNode;
	private final Node negativeNode;
	private double value;
	ArrayList<Double> storedVoltages = new ArrayList<>();
	ArrayList<Double> storedCurrents = new ArrayList<>();

	// for controlled sources
	private Element ele;
	private double k;
	private Node nodeA;
	private Node nodeB;

	// for ac sources
	private double v;
	private double u;
	private double frequency;
	private double phase;

	// for RLC, Diode and DC sources (I)(V)
	Element(String name, Node positiveNode, Node negativeNode, double value) {
		this.name = name;
		this.positiveNode = positiveNode;
		this.negativeNode = negativeNode;
		this.value = value;
		storedCurrents.add(0.0d);
		storedVoltages.add(0.0d);
	}

	// for current controlled sources (F)(W)
	Element(String name, Node positiveNode, Node negativeNode, Element ele, double k) {
		this.name = name;
		this.positiveNode = positiveNode;
		this.negativeNode = negativeNode;
		this.ele = ele;
		this.k = k;
		storedCurrents.add(0.0d);
		storedVoltages.add(0.0d);
	}

	// for voltage controlled sources (G)(X)
	Element(String name, Node positiveNode, Node negativeNode, Node nodeA, Node nodeB, double k) {
		this.name = name;
		this.positiveNode = positiveNode;
		this.negativeNode = negativeNode;
		this.nodeA = nodeA;
		this.nodeB = nodeB;
		this.k = k;
		storedCurrents.add(0.0d);
		storedVoltages.add(0.0d);
	}

	// for ac sources (H)(Y)
	Element(String name, Node positiveNode, Node negativeNode, double v, double u, double frequency, double phase) {
		this.name = name;
		this.positiveNode = positiveNode;
		this.negativeNode = negativeNode;
		this.v = v;
		this.u = u;
		this.frequency = frequency;
		this.phase = phase;
		storedCurrents.add(0.0d);
		storedVoltages.add(0.0d);
	}

	public double getVoltage(int cycle) { return storedVoltages.get(cycle); }

	public double getCurrent(int cycle, double dt) {
		switch (name.charAt(0)) {
			case 'R':	return getVoltage(cycle) / value;
			case 'I':	return -value;
			case 'F': 	return k * ele.getCurrent(cycle, dt);
			case 'G': 	return k * (nodeA.getVoltage(cycle) - nodeB.getVoltage(cycle));
			case 'H': 	return v + u * Math.sin(2 * Math.PI * frequency * cycle * dt + phase);
			case 'C': 	return value * (getVoltage(cycle) - getVoltage(cycle - 1)) / dt;
			case 'L':
				double i = 0;
				for(int j = 0; j < cycle; j++)
					i += getVoltage(j);
				return i / value;
			default: return 0;
		}
	}

	public String getName() { return name;}

	public Node getPositiveNode() { return positiveNode; }

	public Node getNegativeNode() { return negativeNode; }

	//public ArrayList<Double> getStoredVoltages() { return storedVoltages; }

	//public ArrayList<Double> getStoredCurrents() { return storedCurrents; }
}
