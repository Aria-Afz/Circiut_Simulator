import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.HashSet;

public class Circuit {
	private double time;
	private double dt;
	private double dv;
	private double di;
	LinkedHashMap<String, Element> allElements = new LinkedHashMap<>();
	HashMap<Byte, Node> allNodes = new HashMap<>();
	HashMap<Byte, Union> allUnions = new HashMap<>();

	void errorCheck() {
		if (!allNodes.containsKey((byte) 0))
			exit(-4);
		for (Node e : allNodes.values())
			if (e.elementNeighbours.size() == 1)
				exit(-5);
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
		if (connected.size() != allNodes.size())
			exit(-4);
	}

	void unionCheck() {
		for (Node e : allNodes.values())
			e.visited = false;
		HashSet<Node> visited = new HashSet<>();
		visited.add(allNodes.get((byte) 0));
		while (visited.size() != allNodes.size())
			for (Node i : visited) {
				for (Node j : i.nodeNeighbours)
					j.visited = true;
				for (Element ele : i.elementNeighbours) {
					char k = ele.getName().charAt(0);
					if (k == 'V' || k == 'E' || k == 'H') {
						if (ele.getPositiveNode() == i)
							ele.getNegativeNode().union = i.union;
						else
							ele.getPositiveNode().union = i.union;
					}
				}
			}
		for (Node e : allNodes.values()) {
			if (allUnions.containsKey(e.union))
				allUnions.get(e.union).nodes.add(e);
			else
				allUnions.put(e.union, new Union(e));
		}
	}

	void run() {
		unionCheck();
		for (int i = 1; i <= time / dt; i++) {
			for (Node e : allNodes.values())
				e.storedVoltages.add(e.storedVoltages.get(i - 1));
			for (Node e : allNodes.values())
				if (e.getName() != 0)
					solve(e, i);
			for (Element ele : allElements.values()) {
				ele.storedVoltages.add(ele.getVoltage(i));
				ele.storedCurrents.add(ele.getCurrent(i, dt));
			}
		}
		printResult();
	}

	void solve(Node e, int cycle) {
		double v = e.storedVoltages.get(cycle);
		double kcl1 = kclCalculate(e, cycle);
		e.storedVoltages.remove(cycle);
		e.storedVoltages.add(v + dv);
		double kcl2 = kclCalculate(e, cycle);
		System.out.println(kcl1 + " " + kcl2);
		e.storedVoltages.remove(cycle);
		e.storedVoltages.add(v + (Math.abs(kcl1) - Math.abs(kcl2)) * dv / di);
	}

	double kclCalculate(Node e, int cycle) {
		double sum = 0;
		for (Element ele : e.elementNeighbours) {
			if (ele.getPositiveNode() == e)
				sum -= ele.getCurrent(cycle, dt);
			else
				sum += ele.getCurrent(cycle, dt);
		}
		return sum;
	}

	void printResult() {
		System.out.println("Node's Voltages :");
		for (Node e : allNodes.values())
			if (e.getName() != 0) {
				System.out.print(e.getName() + " : ");
				e.storedVoltages.forEach(x -> System.out.print(x + " "));
				System.out.println();
			}
		System.out.println("Element's (Voltages Currents Powers) :");
		for (Element ele : allElements.values()) {
			System.out.print(ele.getName() + " : ");
			for (int i = 1; i <= time/dt; i++)
				System.out.print("(" + ele.getVoltage(i) + " " + ele.getCurrent(i, dt) + " "
						+ ele.getVoltage(i) * ele.getCurrent(i, dt) + ") ");
			System.out.println();
		}
	}

	static void exit(int n) {
		System.out.print(n);
		System.exit(0);
	}

	public void setTime(double time) { this.time = time; }

	public double getTime() { return time; }

	public void setDt(double dt, int n) {
		if (dt == 0)
			exit(n);
		else
			this.dt = dt;
	}

	public void setDv(double dv, int n) {
		if (dv == 0)
			exit(n);
		else
			this.dv = dv;
	}

	public void setDi(double di, int n) {
		if (di == 0)
			exit(n);
		else
			this.di = di;
	}

	public int getCycle(double t) { return (int) Math.round(t/dt); }
}
