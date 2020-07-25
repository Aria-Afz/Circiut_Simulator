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
			for (Map.Entry<Byte, Node> e : allNodes.entrySet())
				e.getValue().storedVoltages.add(e.getValue().storedVoltages.get(i - 1));
			for (Map.Entry<Byte, Node> e : allNodes.entrySet())
				if (e.getKey() != 0)
					sumCalculate(e.getValue(), i);
			for (Map.Entry<String, Element> ele : allElements.entrySet()) {
				ele.getValue().storedVoltages.add(
						allNodes.get(ele.getValue().getPositiveNode()).getVoltage(i) -
						allNodes.get(ele.getValue().getNegativeNode()).getVoltage(i));
				ele.getValue().storedCurrents.add(ele.getValue().getCurrent(i, dt, allNodes));
			}
		}
	}

	void sumCalculate(Node e, int cycle) {
		double sum1 = 0, sum2 = 0, sum3 = 0, v = e.getVoltage(cycle - 1);
		for (Map.Entry<Byte, String> n : e.getNeighbours().entrySet()) {
			Element ele = allElements.get(n.getValue());
			e.storedVoltages.remove(cycle);
			e.storedVoltages.add(v - dv);
			if (ele.getPositiveNode() == e.getName())
				sum1 -= ele.getCurrent(cycle, dt, allNodes);
			else
				sum1 += ele.getCurrent(cycle, dt, allNodes);
			e.storedVoltages.remove(cycle);
			e.storedVoltages.add(v + dv);
			if (ele.getPositiveNode() == e.getName())
				sum2 -= ele.getCurrent(cycle, dt, allNodes);
			else
				sum2 += ele.getCurrent(cycle, dt, allNodes);
			e.storedVoltages.remove(cycle);
			e.storedVoltages.add(v);
			if (ele.getPositiveNode() == e.getName())
				sum3 -= ele.getCurrent(cycle, dt, allNodes);
			else
				sum3 += ele.getCurrent(cycle, dt, allNodes);
		}
		e.storedVoltages.remove(cycle);
		if (Math.abs(sum1) > Math.abs(sum2))
			if (Math.abs(sum2) > Math.abs(sum3))
				e.storedVoltages.add(v);
			else
				e.storedVoltages.add(v + dv);
		else if (Math.abs(sum1) > Math.abs(sum3))
			e.storedVoltages.add(v);
		else
			e.storedVoltages.add(v - dv);
	}

	void printResult() {
		System.out.println("Node's Voltages :");
		for (Map.Entry<Byte, Node> e : allNodes.entrySet()) {
			System.out.print(e.getKey() + " : ");
			e.getValue().storedVoltages.forEach(x -> System.out.print(x + " "));
			System.out.println();
		}
		System.out.println("Element's Voltages :");
		for (Map.Entry<String, Element> ele : allElements.entrySet()) {
			System.out.print(ele.getKey() + " : ");
			ele.getValue().storedVoltages.forEach(x -> System.out.print(x + " "));
			System.out.println();
		}
		System.out.println("Element's Currents :");
		for (Map.Entry<String, Element> ele : allElements.entrySet()) {
			System.out.print(ele.getKey() + " : ");
			ele.getValue().storedCurrents.forEach(x -> System.out.print(x + " "));
			System.out.println();
		}
	}

	public void setTime(double time) { this.time = time; }

	public void setDt(double dt) { this.dt = dt; }

	public void setDv(double dv) { this.dv = dv; }

	public void setDi(double di) { this.di = di; }

}
