import java.util.ArrayList;
import java.util.HashMap;

class Element {
	private final String name;
	private final byte positiveNode;
	private final byte negativeNode;
	private double value;
	ArrayList<Double> storedVoltages = new ArrayList<>();
	ArrayList<Double> storedCurrents = new ArrayList<>();

	// for controlled sources
	private Element ele;
	private double k;
	private byte nodeA;
	private byte nodeB;

	// for ac sources
	private double v;
	private double u;
	private double frequency;
	private double phase;

	// for RLC, Diode and LTI sources
	Element(String name, String positiveNode, String negativeNode, double value) {
		this.name = name;
		this.positiveNode = Byte.parseByte(positiveNode);
		this.negativeNode = Byte.parseByte(negativeNode);
		this.value = value;
		storedCurrents.add((double) 0);
		storedVoltages.add((double) 0);
	}

	// for current controlled sources (F)
	Element(String name, String positiveNode, String negativeNode, Element ele, double k) {
		this.name = name;
		this.positiveNode = Byte.parseByte(positiveNode);
		this.negativeNode = Byte.parseByte(negativeNode);
		this.ele = ele;
		this.k = k;
		storedCurrents.add((double) 0);
		storedVoltages.add((double) 0);
	}

	// for voltage controlled sources (G)
	Element(String name, String positiveNode, String negativeNode, String nodeA, String nodeB, double k) {
		this.name = name;
		this.positiveNode = Byte.parseByte(positiveNode);
		this.negativeNode = Byte.parseByte(negativeNode);
		this.nodeA = Byte.parseByte(nodeA);
		this.nodeB = Byte.parseByte(nodeB);
		this.k = k;
		storedCurrents.add((double) 0);
		storedVoltages.add((double) 0);
	}

	// for ac sources (H)
	Element(String name, String positiveNode, String negativeNode, double v, double u, double frequency, double phase) {
		this.name = name;
		this.positiveNode = Byte.parseByte(positiveNode);
		this.negativeNode = Byte.parseByte(negativeNode);
		this.v = v;
		this.u = u;
		this.frequency = frequency;
		this.phase = phase;
		storedCurrents.add((double) 0);
		storedVoltages.add((double) 0);
	}

	public double getVoltage(int cycle) { return storedVoltages.get(cycle); }

	public double getCurrent(int cycle, double dt, HashMap<Byte, Node> n) {
		switch (name.charAt(0)) {
			case 'R':	return getVoltage(cycle) / value;
			case 'I':	return value;
			case 'F': 	return k * ele.getCurrent(cycle, dt, n);
			case 'G': 	return k * (n.get(nodeA).getVoltage(cycle) - n.get(nodeB).getVoltage(cycle));
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

	public Byte getPositiveNode() { return positiveNode; }

	public Byte getNegativeNode() { return negativeNode; }

	//public ArrayList<Double> getStoredVoltages() { return storedVoltages; }

	//public ArrayList<Double> getStoredCurrents() { return storedCurrents; }
}
