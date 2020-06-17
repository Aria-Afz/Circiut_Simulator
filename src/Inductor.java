
public class Inductor extends Element {
	
	Inductor(String name, String positiveNode, String negativeNode, double value) {
		super (name, positiveNode, negativeNode, value);
	}
	
	double getCurrent(int cycle) {
		double i = 0;
		for(int j = 0; j < cycle; j++)
			i += getVoltage(j);
		return i / value;
	}
}