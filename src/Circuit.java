import java.util.*;

public class Circuit {
	private double time;
	private double dt;
	private double dv;
	private double di;
	LinkedHashMap<String, Element> allElements = new LinkedHashMap<>();
	HashMap<Byte, Node> allNodes = new HashMap<>();
	LinkedList<Union> allUnions = new LinkedList<>();

	boolean errorCheck() {
		if (!allNodes.containsKey((byte) 0)) {
			System.out.print("-4");
			return false;
		}
		for (Map.Entry<Byte, Node> e : allNodes.entrySet()) {
			if (e.getValue().nodeNeighbours.size() == 1)
				System.out.print("-5");
				return false;
			}
		HashSet<Node> connected = new HashSet<>();
		connected.add(allNodes.get((byte) 0));
		allNodes.get((byte) 0).visited = true;
		byte k = 0;
		while (connected.size() - k > 0) {
			k = (byte) connected.size();
			for (Node i : connected)
				for (Node e : i.nodeNeighbours)
					if (!e.visited) {
						connected.add(e);
						e.visited = true;
					}
		}
		if (connected.size() != allNodes.size()) {
			System.out.print("-4");
			return false;
		} else
			return true;
	}

	void unionCheck() {
		
	}

	void run() {
		unionCheck();
		for (int i = 1; i <= time / dt; i++) {
			for (Map.Entry<Byte, Node> e : allNodes.entrySet())
				e.getValue().storedVoltages.add(e.getValue().storedVoltages.get(i - 1));
			for (Map.Entry<Byte, Node> e : allNodes.entrySet())
				if (e.getKey() != 0)
					KclCalculate(e.getValue(), i);
			for (Map.Entry<String, Element> ele : allElements.entrySet()) {
				ele.getValue().storedVoltages.add(
						ele.getValue().getPositiveNode().getVoltage(i) -
						ele.getValue().getNegativeNode().getVoltage(i));
				ele.getValue().storedCurrents.add(ele.getValue().getCurrent(i, dt));
			}
		}
		printResult();
	}

	void KclCalculate(Node e, int cycle) {
		double kcl1 = 0, kcl2 = 0, kcl3 = 0, v = e.getVoltage(cycle - 1);
		for (Element ele : e.elementNeighbours) {
			e.storedVoltages.remove(cycle);
			e.storedVoltages.add(v - dv);
			if (ele.getPositiveNode() == e)
				kcl1 -= ele.getCurrent(cycle, dt);
			else
				kcl1 += ele.getCurrent(cycle, dt);
			e.storedVoltages.remove(cycle);
			e.storedVoltages.add(v + dv);
			if (ele.getPositiveNode() == e)
				kcl2 -= ele.getCurrent(cycle, dt);
			else
				kcl2 += ele.getCurrent(cycle, dt);
			e.storedVoltages.remove(cycle);
			e.storedVoltages.add(v);
			if (ele.getPositiveNode() == e)
				kcl3 -= ele.getCurrent(cycle, dt);
			else
				kcl3 += ele.getCurrent(cycle, dt);
		}
		e.storedVoltages.remove(cycle);
		if (Math.abs(kcl1) > Math.abs(kcl2))
			if (Math.abs(kcl2) > Math.abs(kcl3))
				e.storedVoltages.add(v);
			else
				e.storedVoltages.add(v + dv);
		else if (Math.abs(kcl1) > Math.abs(kcl3))
			e.storedVoltages.add(v);
		else
			e.storedVoltages.add(v - dv);
	}

	void printResult() {
		System.out.println("Node's Voltages :");
		for (Map.Entry<Byte, Node> e : allNodes.entrySet()) {
			if (e.getValue().getName() != 0) {
				System.out.print(e.getKey() + " : ");
				e.getValue().storedVoltages.forEach(x -> System.out.print(x + " "));
				System.out.println();
			}
		}
		System.out.println("Element's (Voltages Currents Powers) :");
		for (Map.Entry<String, Element> ele : allElements.entrySet()) {
			System.out.print(ele.getKey() + " : ");
			Element a = ele.getValue();
			for (int i = 1; i <= time/dt; i++)
				System.out.print("(" + a.getVoltage(i) + " " + a.getCurrent(i, dt) + " "
						+ a.getVoltage(i) * a.getCurrent(i, dt) + ") ");
			System.out.println();
		}
	}

	public void setTime(double time) { this.time = time; }

	public void setDt(double dt) { this.dt = dt; }

	public void setDv(double dv) { this.dv = dv; }

	public void setDi(double di) { this.di = di; }

	public int getCycle(double t) { return (int) Math.round(t/dt); }
}
