import java.util.HashMap;
import java.util.Map;

public class Circuit {
	HashMap<String, Element> allElements = new HashMap<>();
	HashMap<Byte, Node> allNodes = new HashMap<>();
	private double time;
	private double dt;
	private double dv;
	private double di;

//	static boolean aBoolean = true;
//	boolean errorCheck() {
//		allNodes.forEach(this::groundCheck);  // very very nice
//		allNodes.forEach(this::neighbourCheck); // again very very nice
//		return aBoolean;
//	}
//
//	void neighbourCheck(Node a) {
//		if(a.getNeighbours().size() == 0) {
//			aBoolean = false;
//			System.out.println("-5");
//		}
//	}
//
//	void groundCheck(Node a) {
//		if(a.getName().equals("0")) {
//			aBoolean = false;
//			System.out.println("-4");
//		}
//	}

	void run() {
		for (int i = 1; i <= time / dt; i++) {
			nodeUpdate(i);
			for (Map.Entry<String, Element> ele : allElements.entrySet()) {
				ele.getValue().getStoredVoltages().add(
						allNodes.get(ele.getValue().getPositiveNode()).getVoltage(i) -
						allNodes.get(ele.getValue().getNegativeNode()).getVoltage(i));
				ele.getValue().getStoredCurrents().add(ele.getValue().getCurrent(i, dt, allNodes));
			}
		}
	}

	void nodeUpdate(int cycle) {
		for (Map.Entry<Byte, Node> e : allNodes.entrySet()) {
			if (e.getKey() != 0)
				for (Map.Entry<String, String> n : e.getValue().getNeighbours().entrySet())
					sumCalculate(allElements.get(n.getValue()), cycle);
		}
	}

	void sumCalculate(Element ele, int cycle) {
		double sum1 = 0, sum2 = 0, sum3 = 0, v = ele.getVoltage(cycle - 1);
	}

	void printResult() {
		System.out.println("Node's Voltages :");
		for (Map.Entry<Byte, Node> e : allNodes.entrySet()) {
			System.out.print(e.getKey() + " : ");
			e.getValue().getStoredVoltage().forEach(x -> System.out.print(x + " "));
			System.out.println();
		}
		System.out.println("Element's Voltages :");
		for (Map.Entry<String, Element> ele : allElements.entrySet()) {
			System.out.print(ele.getKey() + " : ");
			ele.getValue().getStoredVoltages().forEach(x -> System.out.print(x + " "));
			System.out.println();
		}
		System.out.println("Element's Currents :");
		for (Map.Entry<String, Element> ele : allElements.entrySet()) {
			System.out.print(ele.getKey() + " : ");
			ele.getValue().getStoredCurrents().forEach(x -> System.out.print(x + " "));
			System.out.println();
		}
	}

	public void setTime(double time) { this.time = time; }

	public void setDt(double dt) { this.dt = dt; }

	public void setDv(double dv) { this.dv = dv; }

	public void setDi(double di) { this.di = di; }

}
