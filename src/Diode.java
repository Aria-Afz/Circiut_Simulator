
public class Diode extends Element {
	
	Diode(String name, String positiveNode, String negativeNode, double value) {
		super (name, positiveNode, negativeNode, value);
	}
	boolean isOn(int cycle) { return getVoltage(cycle) >= 0; }
	
}
