import java.util.ArrayList;

public class Circuit {
	ArrayList<Element> allElements = new ArrayList<>();
	ArrayList<Node> allNodes = new ArrayList<>();
	double time;
	double dt;
	double dv;
	double di;

	static boolean aBoolean = true;
	boolean errorCheck() {
		allNodes.forEach(this::groundCheck);  // very very nice
		allNodes.forEach(this::neighbourCheck); // again very very nice
		return aBoolean; // todo need to be completed
	}

	void neighbourCheck(Node a) {
		if(a.neighbours.size() == 0) {
			aBoolean = false;
			System.out.println("-5");
		}
	}

	void groundCheck(Node a) {
		if(a.name.equals("0")) {
			aBoolean = false;
			System.out.println("-4");
		}
	}

	void run() {}
}
