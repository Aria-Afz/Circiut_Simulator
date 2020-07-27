import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
	public static void main (String[] args) throws FileNotFoundException {
		Scanner sc = new Scanner(System.in);
		Circuit cir = new Circuit();
		if (readFile(new File("Circuit.txt"), cir)) {
			String input = sc.nextLine();
			while (!input.equals("END")) {
				String[] arr = input.split(" +");
				Node node1, node2;
				double t;
				try {
					node1 = cir.allNodes.get(Byte.parseByte(arr[0]));
					node2 = cir.allNodes.get(Byte.parseByte(arr[1]));
					t = Double.parseDouble(arr[2]);
				} finally {
					System.out.print("ERROR");
				}
				System.out.println(node1.getVoltage(cir.getCycle(t)) - node2.getVoltage(cir.getCycle(t)));
				input = sc.nextLine();
			}
		}
	}

	static boolean readFile(File circuit, Circuit cir) throws FileNotFoundException {
		Scanner sc = new Scanner(circuit);
		byte numLine = 1;
		while(true) {
			String data = sc.nextLine();
			String[] arr = data.trim().split(" ");
			if (arr[0].equals(".tran")) {
				if (unitPrefix(arr[1]) == -1)
					break;
				else {
					cir.setTime(unitPrefix(arr[1]));
					cir.run();
					return true;
				}
			} else if (arr[0].equals("dv")) {
				if (unitPrefix(arr[1]) == -1)
					break;
				else
					cir.setDv(unitPrefix(arr[1]));
			} else if (arr[0].equals("dt")) {
				if (unitPrefix(arr[1]) == -1)
					break;
				else
					cir.setDt(unitPrefix(arr[1]));
			} else if (arr[0].equals("di")) {
				if (unitPrefix(arr[1]) == -1)
					break;
				else
					cir.setDi(unitPrefix(arr[1]));
			} else if (arr[0].charAt(0) == '*')
				continue;
			else {
				addElement(data, cir);
			}
			numLine++;
		}
		System.out.println(numLine);
		return false;
	}

	static double unitPrefix(String a) {
		if (a.charAt(0) == '-')
			return -1;
		double x;
		if (!(a.charAt(a.length() - 1) > 47 && a.charAt(a.length() - 1) < 58)) {
			try {
				x = Double.parseDouble(a.substring(0, a.length() - 1));
			} catch (NumberFormatException e) {
				return -1;
			}
			switch (a.charAt(a.length() - 1)) {
				case 'p': 	return x * Math.pow(10, -12);
				case 'n': 	return x * Math.pow(10, -9);
				case 'u': 	return x * Math.pow(10, -6);
				case 'm': 	return x * Math.pow(10, -3);
				case 'k': 	return x * Math.pow(10, 3);
				case 'M': 	return x * Math.pow(10, 6);
				case 'G': 	return x * Math.pow(10, 9);
				default:	return -1;
			}
		} else {
			try {
				x = Double.parseDouble(a);
			} catch (NumberFormatException ex) {
				return -1;
			}
			return x;
		}
	}

	static void addElement(String input, Circuit cir) {
		String[] arr = input.trim().split(" +");
		Element e;
		byte A, B;
		try {
			A = Byte.parseByte(arr[1]);
			B = Byte.parseByte(arr[2]);
		} catch (NumberFormatException ex) {
			return;
		}
		Node a = new Node((byte) 0), b = new Node((byte) 1);
		if (arr.length == 4)
			e = new Element(arr[0], a, b, unitPrefix(arr[3]));
		else if (arr.length == 5)
			e = new Element(arr[0], a, b, cir.allElements.get(arr[3]), unitPrefix(arr[4]));
		else if (arr.length == 6) {
			byte C, D;
			Node c, d;
			try {
				C = Byte.parseByte(arr[3]);
				D = Byte.parseByte(arr[4]);
			} catch (NumberFormatException ex) {
				return;
			}
			if (!cir.allNodes.containsKey(C)) {
				c = new Node(C);
				cir.allNodes.put(C, c);
			} else
				c = cir.allNodes.get(C);
			if (!cir.allNodes.containsKey(D)) {
				d = new Node(D);
				cir.allNodes.put(D, d);
			} else
				d = cir.allNodes.get(D);
			e = new Element(arr[0], a, b, c, d, unitPrefix(arr[5]));
		} else
			e = new Element(arr[0], a, b, unitPrefix(arr[3]), unitPrefix(arr[4]), unitPrefix(arr[5]), unitPrefix(arr[6]));
		cir.allElements.put(e.getName(), e);
	}
}
