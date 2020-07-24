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
//		return aBoolean; // todo need to be completed
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
		for (int i = 1; i <= time/dt; i++)
			nodeUpdate(i);
	}

	void nodeUpdate(int cycle) {
		for(Map.Entry<Byte, Node> e : allNodes.entrySet()) {
			if (e.getKey() != 0) {
				double v1 = e.getValue().getVoltage(cycle - 1), v2 = v1 - dv, v3 = v1 + dv;
				double sum1 = 0, sum2 = 0, sum3 = 0;
				for (Map.Entry<String, String> n : e.getValue().getNeighbours().entrySet()) {
					Element ele = allElements.get(n.getValue());

				}
			}
		}
	}

	public void setTime(double time) { this.time = time; }

	public void setDt(double dt) { this.dt = dt; }

	public void setDv(double dv) { this.dv = dv; }

	public void setDi(double di) { this.di = di; }

}
