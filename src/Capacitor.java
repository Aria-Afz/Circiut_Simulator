
public class Capacitor extends Element {
	
	Capacitor(String name, String positiveNode, String negativeNode, double value) {
		super (name, positiveNode, negativeNode, value);
	}
	
	public double getCurrent(int cycle, int dt) {
		if (cycle == 0)
			return 0;
		return (getVoltage(cycle) - getVoltage(cycle - 1)) / dt;
	}
}
