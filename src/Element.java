import java.util.ArrayList;

class Element {
	private final String name;
	private final Node positiveNode;
	private final Node negativeNode;
	private double value;
	private ArrayList<Double> storedVoltages = new ArrayList<>();
	private ArrayList<Double> storedCurrents = new ArrayList<>();

	// for controlled sources
	private Element ele;
	private double k;
	private Node a;
	private Node b;
	
	Element(String name, Node positiveNode, Node negativeNode, double value) {
		this.name = name;
		this.positiveNode = positiveNode;
		this.negativeNode = negativeNode;
		this.value = value;
	}

	Element(String name, Node positiveNode, Node negativeNode, Element ele, double k) {
		this.name = name;
		this.negativeNode = negativeNode;
		this.positiveNode = positiveNode;
		this.ele = ele;
		this.k = k;
	}

	Element(String name, Node positiveNode, Node negativeNode, Node a, Node b, double k) {
		this.name = name;
		this.positiveNode = positiveNode;
		this.negativeNode = negativeNode;
		this.a = a;
		this.b = b;
		this.k = k;
	}
	
	public double getVoltage(int cycle) { return storedVoltages.get(cycle); }

	public double getCurrent(int cycle, int dt) {
		switch (name.charAt(0)) {
			case 'R':
				return getVoltage(cycle) / value;
			case 'I':
				return value;
			case 'F':
				return k * ele.getCurrent(cycle,dt);
			case 'G':
				return k * (a.getVoltage() - b.getVoltage());
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

}
