import java.util.ArrayList;

class Element {
	final String name;
	final Node positiveNode;
	final Node negativeNode;
	private double value;
	String gValue = "N.C";
	ArrayList<Double> storedVoltages = new ArrayList<>();
	ArrayList<Double> storedCurrents = new ArrayList<>();
	// for controlled sources
	Element ele;
	double k;
	Node nodeA;
	Node nodeB;
	// for ac sources
	double v=0;
	double u = 0;
	double frequency;
	double phase = 0;
	boolean isAC = true;
	// for RLC, Diode
	Element(String name, Node positiveNode, Node negativeNode, double value) {
		this.name = name;
		this.positiveNode = positiveNode;
		this.negativeNode = negativeNode;
		this.value = value;
		storedCurrents.add(0.0d);
		storedVoltages.add(0.0d);
	}
	// for current controlled sources (F)(H)
	Element(String name, Node positiveNode, Node negativeNode, Element ele, double k) {
		this.name = name;
		this.positiveNode = positiveNode;
		this.negativeNode = negativeNode;
		this.ele = ele;
		this.k = k;
		storedCurrents.add(0.0d);
		storedVoltages.add(0.0d);
	}
	// for voltage controlled sources (G)(E)
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
	// for ac sources (I)(V)
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
		if (u == 0 || frequency == 0)
			this.isAC = false;
	}

	public double getVoltage(int cycle, double dt) {
		switch (name.charAt(0)) {
			case 'H':
				if (cycle == 0)
					return 0;
				else
					return k * ele.getCurrent(cycle - 1, dt);
			case 'E':
				if (cycle == 0)
					return 0;
				else
					return k * (nodeA.getVoltage(cycle - 1) - nodeB.getVoltage(cycle - 1));
			case 'V':	return v + u * Math.sin(2 * Math.PI * frequency * cycle * dt + phase);
			default:	return positiveNode.getVoltage(cycle) - negativeNode.getVoltage(cycle);
		}
	}

	public double getCurrent(int cycle, double dt) {
		switch (name.charAt(0)) {
			case 'R':	return getVoltage(cycle, dt) / value;
			case 'F': 	return -(k * ele.getCurrent(cycle, dt));
			case 'G': 	return -(k * (nodeA.getVoltage(cycle) - nodeB.getVoltage(cycle)));
			case 'I': 	return -(v + u * Math.sin(2 * Math.PI * frequency * cycle * dt + phase));
			case 'C': 	return value * (getVoltage(cycle, dt) - getVoltage(cycle - 1, dt)) / dt;
			case 'L':
				double i = 0;
				for(int j = 0; j < cycle; j++)
					i += getVoltage(j, dt) * dt;
				return i / value;
			default:
				double current = 0;
				for (Element ele : this.positiveNode.elementNeighbours)
					if (ele != this) {
						if (ele.positiveNode == this.positiveNode)
							current -= ele.getCurrent(cycle, dt);
						else
							current += ele.getCurrent(cycle, dt);
					}
				return current;
		}
	}

	public void update(int cycle, double dt) {
		storedVoltages.add(getVoltage(cycle, dt));
		storedCurrents.add(getCurrent(cycle, dt));
	}
}
