import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
	public static void main (String[] args) throws FileNotFoundException {
		Scanner sc = new Scanner(System.in);
		Circuit cir = new Circuit();
		if (readFile(new File("Circuit.txt"), cir) && cir.errorCheck()) {
			cir.run();
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
		Task:
			while(true) {
				String data = sc.nextLine();
				String[] arr = data.trim().split(" ");

				switch(data.charAt(0)) {
					case '*':	break;
					case 'd':
						switch(arr[0]) {
							case "dv":
								if (unitPrefix(arr[1]) == -1)
									break Task;
								else
									cir.setDv(unitPrefix(arr[1]));
								break;
							case "dt":
								if (unitPrefix(arr[1]) == -1)
									break Task;
								else
									cir.setDt(unitPrefix(arr[1]));
								break;
							case "di":
								if (unitPrefix(arr[1]) == -1)
									break Task;
								else
									cir.setDi(unitPrefix(arr[1]));
								break;
							default:	break Task;
						}
						break;
					case '.':
						if (arr[0].equals(".tran")) {
							if (unitPrefix(arr[1]) == -1)
								break Task;
							else {
								cir.setTime(unitPrefix(arr[1]));
								return true;
							}
						} else
							break Task;
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
						if (!addElement(data, cir))
							break Task;
						break;
					default:	break Task;
				}
				numLine++;
			}
		System.out.print(numLine);
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

	static boolean addElement(String input, Circuit cir) {
		String[] arr = input.trim().split(" +");
		Element e;
		Node a, b;
		try {
			a = cir.allNodes.get(Byte.parseByte(arr[1]));
		} catch (NumberFormatException ex) {
			return false;
		} catch (NullPointerException ex) {
			a = new Node(Byte.parseByte(arr[1]));
		}
		try {
			b = cir.allNodes.get(Byte.parseByte(arr[2]));
		} catch (NumberFormatException ex) {
			return false;
		} catch (NullPointerException ex) {
			b = new Node(Byte.parseByte(arr[2]));
		}
		if (arr.length == 4) {
			if (unitPrefix(arr[3]) != -1)
				e = new Element(arr[0], a, b, unitPrefix(arr[3]));
			else
				return false;
		} else if (arr.length == 5) {
			if (cir.allElements.containsKey(arr[3]) && unitPrefix(arr[4]) != -1)
				e = new Element(arr[0], a, b, cir.allElements.get(arr[3]), unitPrefix(arr[4]));
			else
				return false;
		}
		else if (arr.length == 6) {
			Node c, d;
			try {
				c = cir.allNodes.get(Byte.parseByte(arr[3]));
			} catch (NumberFormatException ex) {
				return false;
			} catch (NullPointerException ex) {
				c = new Node(Byte.parseByte(arr[3]));
			}
			try {
				d = cir.allNodes.get(Byte.parseByte(arr[1]));
			} catch (NumberFormatException ex) {
				return false;
			} catch (NullPointerException ex) {
				d = new Node(Byte.parseByte(arr[1]));
			}
			if (unitPrefix(arr[5]) != -1)
				e = new Element(arr[0], a, b, c, d, unitPrefix(arr[5]));
			else
				return false;
		} else if (arr.length == 7) {
			if (unitPrefix(arr[3]) != -1 && unitPrefix(arr[4]) != -1 && unitPrefix(arr[5]) != -1 && unitPrefix(arr[6]) != -1)
				e = new Element(arr[0], a, b, unitPrefix(arr[3]), unitPrefix(arr[4]), unitPrefix(arr[5]), unitPrefix(arr[6]));
			else
				return false;
		} else
			return false;
		cir.allElements.put(e.getName(), e);
		a.neighbours.add(e);
		b.neighbours.add(e);
		return true;
	}
}
