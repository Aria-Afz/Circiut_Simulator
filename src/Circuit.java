import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.HashSet;

public class Circuit {
	private double time;
	private double dt = -1;
	private double dv = -1;
	private double di = -1;
	LinkedHashMap<String, Element> allElements = new LinkedHashMap<>();
	HashMap<Byte, Node> allNodes = new HashMap<>();
	HashMap<Byte, Union> allUnions = new HashMap<>();

	void errorCheck() {
		if (dt == -1 || dv == -1 || di == -1)
			exit(-6);
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
			HashSet<Node> connectedBackup = new HashSet<>(connected);
			for (Node i : connectedBackup)
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
		allNodes.get((byte) 0).visited = true;
		while (visited.size() != allNodes.size()) {
			HashSet<Node> visitedBackup = new HashSet<>(visited);
			for (Node i : visitedBackup) {
				for (Node j : i.nodeNeighbours) {
					j.visited = true;
					visited.add(j);
				}
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
		}
		for (Node e : allNodes.values()) {
			if (allUnions.containsKey(e.union))
				allUnions.get(e.union).nodes.add(e);
			else
				allUnions.put(e.union, new Union(e, e.union));
		}
		for (Union e : allUnions.values()) {
			e.storedVoltages.add(0, 0.0d);
			e.elementCheck();
			e.update(0, dt);
		}
	}

	void run() {
		unionCheck();
		for (int i = 1; i <= time / dt; i++) {
			for (Union e : allUnions.values()) {
				e.storedVoltages.add(i, e.storedVoltages.get(i - 1));
				e.update(i, dt);
			} for (Union e : allUnions.values())
				if (!e.nodes.contains(allNodes.get((byte) 0)))
					solve(e, i);
			for (Element ele : allElements.values())
				ele.update(i, dt);
		}
		printResult();
	}

	void solve(Union e, int cycle) {
		double v = e.storedVoltages.get(cycle);
		double kcl1 = kclCalculate(e, cycle);
		e.storedVoltages.add(cycle, v + dv);
		e.update(cycle, dt);
		double kcl2 = kclCalculate(e, cycle);
		e.storedVoltages.add(cycle, v + (Math.abs(kcl1) - Math.abs(kcl2)) * dv / di);
		e.update(cycle, dt);
	}

	double kclCalculate(Union e, int cycle) {
		double sum = 0;
		for (Node a : e.nodes)
			for (Element ele : a.elementNeighbours)
				if (!e.elements.contains(ele)) {
					if (ele.getPositiveNode() == a)
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
				System.out.print("(" + ele.getVoltage(i, dt) + ", " + ele.getCurrent(i, dt) + ", "
						+ ele.getVoltage(i, dt) * ele.getCurrent(i, dt) + ") ");
			System.out.println();
		}
	}

	static void exit(int n) {
		System.out.print(n);
		System.exit(0);
	}

	public void setTime(double time) { this.time = time; }

	public double getTime() { return time; }

	public void setDt(double dt) {
		this.dt = dt;
	}

	public void setDv(double dv) {
		this.dv = dv;
	}

	public void setDi(double di) {
		this.di = di;
	}

	public int getCycle(double t) {
		return (int) Math.round(t/dt);
	}
}
