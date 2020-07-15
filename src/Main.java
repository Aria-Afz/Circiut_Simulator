import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
	
	public static void main (String[] args) throws FileNotFoundException {
		Circuit cir = new Circuit();
		File inputFile = new File("Circuit.txt");
		int k = readFile(inputFile, cir);
		if (k == 0 /*&& cir.errorCheck()*/)
			cir.run();
		else
			System.out.println("Invalid Input in line " + k);
	}

	static int readFile(File circuit, Circuit cir) throws FileNotFoundException {
		Scanner sc = new Scanner(circuit);
		int numLine = 1;
		while(true) {
			String data = sc.nextLine();
			String[] arr = data.trim().split(" ");
			if (arr[0].equals(".tran")) {
				if (unitPrefix(arr[1]) == -1)
					return numLine;
				else {
					cir.setTime(unitPrefix(arr[1]));
					return 0;
				}
			} else if (arr[0].equals("dv")) {
				if (unitPrefix(arr[1]) == -1)
					return numLine;
				else
					cir.setDv(unitPrefix(arr[1]));
			} else if (arr[0].equals("dt")) {
				if (unitPrefix(arr[1]) == -1)
					return numLine;
				else
					cir.setDt(unitPrefix(arr[1]));
			} else if (arr[0].equals("di")) {
				if (unitPrefix(arr[1]) == -1)
					return numLine;
				else
					cir.setDi(unitPrefix(arr[1]));
			} else if (arr[0].charAt(0) != '*')
				return numLine;
			else
				addElement(data, cir);
			numLine++;
		}
	}

	static double unitPrefix(String a) {
		if (a.charAt(0) == '-')
			return -1;
		if (a.contains("[^\\d][^.]")) { //todo check prefix
			double x = Double.parseDouble(a.substring(0, a.length() - 1));
			switch (a.charAt(a.length() - 1)) {
				case 'p': 	return x * Math.pow(10, -12);
				case 'n': 	return x * Math.pow(10, -9);
				case 'u': 	return x * Math.pow(10, -6);
				case 'm': 	return x * Math.pow(10, -3);
				case 'k': 	return x * Math.pow(10, 3);
				case 'M': 	return x * Math.pow(10, 6);
				case 'G': 	return x * Math.pow(10, 9);
			}
		} else
			return Double.parseDouble(a);
		return 0;
	}

	static void addElement(String input, Circuit cir) {
		String[] arr = input.trim().split(" ");
		Element e;
		if (arr.length == 4)
			e = new Element(arr[0], arr[1], arr[2], unitPrefix(arr[3]));
		else if (arr.length == 5)
			e = new Element(arr[0], arr[1], arr[2], cir.allElements.get(arr[3]), unitPrefix(arr[4]));
		else if (arr.length == 6)
			e = new Element(arr[0], arr[1], arr[2], arr[3], arr[4], unitPrefix(arr[5]));
		else
			e = new Element(arr[0], arr[1], arr[2], unitPrefix(arr[3]), unitPrefix(arr[4]), unitPrefix(arr[5]), unitPrefix(arr[6]));
		cir.allElements.put(arr[0], e);
		if (cir.allNodes.containsKey(arr[1])) {
			HashMap<String, String> neighbours = cir.allNodes.get(arr[1]).getNeighbours();
			neighbours.put(arr[2], e.getName());
			cir.allNodes.get(arr[1]).setNeighbours(neighbours);
		} else
			cir.allNodes.put(arr[1], new Node(arr[1]));
		if (cir.allNodes.containsKey(arr[2])) {
			HashMap<String, String> neighbours = cir.allNodes.get(arr[2]).getNeighbours();
			neighbours.put(arr[1], e.getName());
			cir.allNodes.get(arr[2]).setNeighbours(neighbours);
		} else
			cir.allNodes.put(arr[2], new Node(arr[2]));
	}

}
