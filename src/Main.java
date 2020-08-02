import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	static Circuit cir = new Circuit();

	public static void main (String[] args) throws FileNotFoundException {
		readFile(new File("Circuit.txt"));
		//cir.errorCheck();
		cir.run();
		new DrawCircuit(new ArrayList<>(cir.allElements.values())).main();
		consoleInput();
	}

	static void readFile(File circuit) throws FileNotFoundException {
		Scanner sc = new Scanner(circuit);
		byte numLine = 1;
		while(true) {
			String data = sc.nextLine();
			String[] arr = data.trim().split(" ");
			switch(data.charAt(0)) {
				case '*':	break;
				case 'd':
					switch(arr[0]) {
						case "dv":
							cir.setDv(nonZero(arr[1], numLine));
							break;
						case "dt":
							cir.setDt(nonZero(arr[1], numLine));
							break;
						case "di":
							cir.setDi(nonZero(arr[1], numLine));
							break;
						default:
							exit(numLine);
					}
					break;
				case '.':
					if (arr[0].equals(".tran")) {
							cir.setTime(unitPrefix(arr[1], numLine));
							return;
					} else
						exit(numLine);
				case 'R':
				case 'L':
				case 'C':
				case 'F':
				case 'G':
				case 'I':
				case 'H':
				case 'E':
				case 'V':
				case 'D':
					addElement(arr, numLine);
					break;
				default:
					exit(numLine);
			}
			numLine++;
		}
	}

	static double unitPrefix(String a, int b) {
		if (a.charAt(0) == '-')
			exit(b);
		double x = 0;
		if (!(a.charAt(a.length() - 1) > 47 && a.charAt(a.length() - 1) < 58)) {
			try {
				x = Double.parseDouble(a.substring(0, a.length() - 1));
			} catch (NumberFormatException e) {
				exit(b);
			}
			switch (a.charAt(a.length() - 1)) {
				case 'p': 	return x * Math.pow(10, -12);
				case 'n': 	return x * Math.pow(10, -9);
				case 'u': 	return x * Math.pow(10, -6);
				case 'm': 	return x * Math.pow(10, -3);
				case 'k': 	return x * Math.pow(10, 3);
				case 'M': 	return x * Math.pow(10, 6);
				case 'G': 	return x * Math.pow(10, 9);
				default:	exit(b);  return 0;
			}
		} else {
			try {
				x = Double.parseDouble(a);
			} catch (NumberFormatException ex) {
				exit(b);
			}
			return x;
		}
	}

	static double nonZero(String a, int b) {
		double x = unitPrefix(a, b);
		if (x == 0) {
			exit(b);
			return 0;
		} else
			return x;
	}

	static void addElement(String[] arr, int numLine) {
		if (cir.allElements.containsKey(arr[0]))
			exit(numLine);
		Element e = null;
		byte A = 0, B = 0; Node a, b;
		try {
			A = Byte.parseByte(arr[1]);
			B = Byte.parseByte(arr[2]);
		} catch (NumberFormatException ey) {
			exit(numLine);
		}
		if (A < 0 || B < 0 || A == B)
			exit(numLine);
		a = initializeNode(A);
		cir.allNodes.put(A, a);
		b = initializeNode(B);
		cir.allNodes.put(B, b);
		byte len = (byte) arr.length;
		char k = arr[0].charAt(0);
		if (len == 4 && (k == 'R' || k == 'L' || k == 'C' || k == 'D')) {
			e = new Element(arr[0], a, b, unitPrefix(arr[3], numLine));
			e.gValue = arr[3];
		} else if (len == 5 && (k == 'F' || k == 'H')) {
			e = new Element(arr[0], a, b, cir.allElements.get(arr[3]), unitPrefix(arr[4], numLine));
		}  else if (len == 6 && (k == 'G' || k == 'E')) {
			byte C = 0, D = 0; Node c, d;
			try {
				C = Byte.parseByte(arr[3]);
				D = Byte.parseByte(arr[4]);
			} catch (NumberFormatException ex) {
				exit(numLine);
			}
			if (C < 0 || D < 0 || C == D)
				exit(numLine);
			c = initializeNode(C);
			cir.allNodes.put(C, c);
			d = initializeNode(D);
			cir.allNodes.put(D, d);
			e = new Element(arr[0], a, b, c, d, unitPrefix(arr[5], numLine));
		} else if (len == 7 && (k == 'I' || k == 'V')) {
			e = new Element(arr[0], a, b, unitPrefix(arr[3], numLine), unitPrefix(arr[4], numLine),
					unitPrefix(arr[5], numLine), unitPrefix(arr[6], numLine));
		} else
			exit(numLine);
		if (e != null)
			cir.allElements.put(e.getName(), e);
		a.nodeNeighbours.add(b);
		a.elementNeighbours.add(e);
		b.nodeNeighbours.add(a);
		b.elementNeighbours.add(e);
	}

	static Node initializeNode(byte a) {
		if (cir.allNodes.containsKey(a))
			return cir.allNodes.get(a);
		else
			return new Node(a);
	}

	static void exit(int n) {
		if (n != 0) {
			System.out.print(n);
			System.exit(0);
		} else
			System.out.println("ERROR");
	}

	static void consoleInput() {
		Scanner sc = new Scanner(System.in);
		Node node1, node2; byte e1, e2;
		double t;
		String input = sc.nextLine();
		while (!input.equals("END")) {
			String[] arr = input.split(" +");
			try {
				e1 = Byte.parseByte(arr[0]);
				e2 = Byte.parseByte(arr[1]);
				t = unitPrefix(arr[2], 0);
			} catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
				System.out.println("ERROR");
				input = sc.nextLine();
				continue;
			}
			if (cir.allNodes.containsKey(e1) && cir.allNodes.containsKey(e2)
					&& cir.getCycle(t) <= cir.getCycle(cir.getTime())) {
				node1 = cir.allNodes.get(e1);
				node2 = cir.allNodes.get(e2);
			} else {
				System.out.println("ERROR");
				input = sc.nextLine();
				continue;
			}
			System.out.println(node1.getVoltage(cir.getCycle(t)) - node2.getVoltage(cir.getCycle(t)));
			input = sc.nextLine();
		}
	}
}
