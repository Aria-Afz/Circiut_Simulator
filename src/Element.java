import java.util.ArrayList;
import java.util.HashMap;

class Element {
	private final String name;
	private final String positiveNode;
	private final String negativeNode;
	private double value;
	private ArrayList<Double> storedVoltages = new ArrayList<>();
	private ArrayList<Double> storedCurrents = new ArrayList<>();

	// for controlled sources
	private Element ele;
	private double k;
	private String nodeA;
	private String nodeB;

	// for ac sources
	private double v;
	private double u;
	private double frequency;
	private double phase;

	// for RLC, Diode and LTI sources
	Element(String name, String positiveNode, String negativeNode, double value) {
		this.name = name;
		this.positiveNode = positiveNode;
		this.negativeNode = negativeNode;
		this.value = value;
	}

	// for current controlled sources (F)
	Element(String name, String positiveNode, String negativeNode, Element ele, double k) {
		this.name = name;
		this.negativeNode = negativeNode;
		this.positiveNode = positiveNode;
		this.ele = ele;
		this.k = k;
	}

	// for voltage controlled sources (G)
	Element(String name, String positiveNode, String negativeNode, String nodeA, String nodeB, double k) { // todo need better way using String instead of Node
		this.name = name;
		this.positiveNode = positiveNode;
		this.negativeNode = negativeNode;
		this.nodeA = nodeA;
		this.nodeB = nodeB;
		this.k = k;
	}

	// for ac sources (H)
	Element(String name, String positiveNode, String negativeNode, double v, double u, double frequency, double phase) {
		this.name = name;
		this.positiveNode = positiveNode;
		this.negativeNode = negativeNode;
		this.v = v;
		this.u = u;
		this.frequency = frequency;
		this.phase = phase;
	}
	public double getVoltage(int cycle) { return storedVoltages.get(cycle); }

	public double getCurrent(int cycle, int dt, HashMap<String, Node> n) {
		switch (name.charAt(0)) {
			case 'R':	return getVoltage(cycle) / value;
			case 'I':	return value;
			case 'F': 	return k * ele.getCurrent(cycle, dt, n);
			case 'G': 	return k * (n.get(nodeA).getVoltage(cycle) - n.get(nodeB).getVoltage(cycle));
			case 'H': 	return v + u * Math.sin(2 * Math.PI * frequency * cycle * dt + phase);
			case 'L':
				double i = 0;
				for(int j = 0; j < cycle; j++)
					i += getVoltage(j);
				return i / value;
			case 'C':
				if (cycle == 0)
					return 0;
				return value * (getVoltage(cycle) - getVoltage(cycle - 1)) / dt;
		}
		return 0;
	}

	public String getName() { return name;}
}
