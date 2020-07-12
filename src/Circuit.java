import java.util.ArrayList;

public class Circuit {
	ArrayList<Element> allElements = new ArrayList<>();
	ArrayList<Node> allNodes = new ArrayList<>();
	private double time;
	private double dt;
	private double dv;
	private double di;

	static boolean aBoolean = true;
	boolean errorCheck() {
		allNodes.forEach(this::groundCheck);  // very very nice
		allNodes.forEach(this::neighbourCheck); // again very very nice
		return aBoolean; // todo need to be completed
	}

	void neighbourCheck(Node a) {
		if(a.getNeighbours().size() == 0) {
			aBoolean = false;
			System.out.println("-5");
		}
	}

	void groundCheck(Node a) {
		if(a.getName().equals("0")) {
			aBoolean = false;
			System.out.println("-4");
		}
	}

	void run() {
		double cycle = time/dt;
		for (int i = 0; i < cycle; i++) {
			
		}
	}

	public void setTime(double time) { this.time = time; }

	public void setDt(double dt) { this.dt = dt; }

	public void setDv(double dv) { this.dv = dv; }

	public void setDi(double di) { this.di = di; }
}
