
public class CurrentSource extends Element {
	
	CurrentSource(String name, String positiveNode, String negativeNode, double value) {
		super (name, positiveNode, negativeNode, value);
	}
	
	public double getCurrent() { return value; }

}
