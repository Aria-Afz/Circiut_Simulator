
public class Resistor extends Element {

	Resistor(String name, String positiveNode, String negativeNode, double value) {
		super(name, positiveNode, negativeNode, value);
	}
	
	public double getCurrent(int cycle) { return getVoltage(cycle) / value; }
	
}
