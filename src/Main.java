import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main (String[] args) throws FileNotFoundException {
		Scanner sc = new Scanner(System.in);
		Circuit cir = new Circuit();
		readFile(new File("Circuit.txt"), cir);
		cir.errorCheck();
		//cir.run();
		///////////////////////////////Drawing the circuit/////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////////////
		ArrayList<Element> elementsForGraphics = new ArrayList<Element>(cir.allElements.values());
		DrawCircuit drawCircuit = new DrawCircuit(elementsForGraphics);
		drawCircuit.main();
		///////////////////////////////////////////////////////////////////////////////////////////
		/////////////////////////////End of drawing the circuit////////////////////////////////////
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

	static void readFile(File circuit, Circuit cir) throws FileNotFoundException {
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
							if (unitPrefix(arr[1]) == -1 || unitPrefix(arr[1]) == 0) {
								System.out.print(numLine);
								System.exit(0);
							} else
								cir.setDv(unitPrefix(arr[1]));
							break;
						case "dt":
							if (unitPrefix(arr[1]) == -1 || unitPrefix(arr[1]) == 0) {
								System.out.print(numLine);
								System.exit(0);
							} else
								cir.setDt(unitPrefix(arr[1]));
							break;
						case "di":
							if (unitPrefix(arr[1]) == -1 || unitPrefix(arr[1]) == 0) {
								System.out.print(numLine);
								System.exit(0);
							} else
								cir.setDi(unitPrefix(arr[1]));
							break;
						default:
							System.out.print(numLine);
							System.exit(0);
					}
					break;
				case '.':
					if (arr[0].equals(".tran")) {
						if (unitPrefix(arr[1]) == -1) {
							System.out.print(numLine);
							System.exit(0);
						} else {
							cir.setTime(unitPrefix(arr[1]));
							return;
						}
					} else {
						System.out.print(numLine);
						System.exit(0);
					}
				case 'R':
				case 'L':
				case 'C':
				case 'I':
				case 'F':
				case 'G':
				case 'H':
				case 'V':
				case 'W':
				case 'X':
				case 'Y':
					addElement(data, cir, numLine);
					break;
				default:
					System.out.print(numLine);
					return;
			}
			numLine++;
		}
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

	static void addElement(String input, Circuit cir, int numLine) {
		String[] arr = input.trim().split(" +");
		if (cir.allElements.containsKey(arr[0])) {
			System.out.print(numLine);
			System.exit(0);
		}
		Element e = null;
		byte A = 0, B = 0; Node a, b;
		try {
			A = Byte.parseByte(arr[1]);
			B = Byte.parseByte(arr[2]);
		} catch (NumberFormatException ex) {
			System.out.print(numLine);
			System.exit(0);
		}
		if (A < 0 || B < 0 || A == B) {
			System.out.print(numLine);
			System.exit(0);
		}
		if (cir.allNodes.containsKey(A))
			a = cir.allNodes.get(A);
		else {
			a = new Node(A);
			cir.allNodes.put(A, a);
		} if (cir.allNodes.containsKey(B))
			b = cir.allNodes.get(B);
		else {
			b = new Node(B);
			cir.allNodes.put(B, b);
		}
		byte len = (byte) arr.length;
		char k = arr[0].charAt(0);
		if (len == 4 && (k == 'R' || k == 'L' || k == 'C' || k == 'D')) {
			if (unitPrefix(arr[3]) != -1)
				e = new Element(arr[0], a, b, unitPrefix(arr[3]));
			else {
				System.out.print(numLine);
				System.exit(0);
			}
		} else if (len == 5 && (k == 'F' || k == 'H')) {
			if (cir.allElements.containsKey(arr[3]) && unitPrefix(arr[4]) != -1)
				e = new Element(arr[0], a, b, cir.allElements.get(arr[3]), unitPrefix(arr[4]));
			else {
				System.out.print(numLine);
				System.exit(0);
			}
		}
		else if (len == 6 && (k == 'G' || k == 'E')) {
			byte C = 0, D = 0; Node c, d;
			try {
				C = Byte.parseByte(arr[3]);
				D = Byte.parseByte(arr[4]);
			} catch (NumberFormatException ex) {
				System.out.print(numLine);
				System.exit(0);
			}
			if (C < 0 || D < 0 || C == D) {
				System.out.print(numLine);
				System.exit(0);
			}
			if (cir.allNodes.containsKey(C))
				c = cir.allNodes.get(C);
			else {
				c = new Node(C);
				cir.allNodes.put(C, c);
			}
			if (cir.allNodes.containsKey(D))
				d = cir.allNodes.get(D);
			else {
				d = new Node(D);
				cir.allNodes.put(D, d);
			}
			if (unitPrefix(arr[5]) != -1)
				e = new Element(arr[0], a, b, c, d, unitPrefix(arr[5]));
			else {
				System.out.print(numLine);
				System.exit(0);
			}
		} else if (len == 7 && (k == 'I' || k == 'V')) {
			if (unitPrefix(arr[3]) != -1 && unitPrefix(arr[4]) != -1 && unitPrefix(arr[5]) != -1 && unitPrefix(arr[6]) != -1)
				e = new Element(arr[0], a, b, unitPrefix(arr[3]), unitPrefix(arr[4]), unitPrefix(arr[5]), unitPrefix(arr[6]));
			else {
				System.out.print(numLine);
				System.exit(0);
			}
		} else {
			System.out.print(numLine);
			System.exit(0);
		}
		cir.allElements.put(e.getName(), e);
		a.nodeNeighbours.add(b);
		a.elementNeighbours.add(e);
		b.nodeNeighbours.add(a);
		b.elementNeighbours.add(e);
	}
}
