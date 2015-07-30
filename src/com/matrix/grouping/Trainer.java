package com.matrix.grouping;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Trainer {

	File dict = new File("words.dict");
	String[] words;
	Map<double[], double[]> data = new HashMap<double[], double[]>();

	public Trainer() {
	}

	/* not needed
	 
	public void initDict() {
		try {
			Scanner s = new Scanner(dict);
			List<String> intm = new ArrayList<String>();
			while (s.hasNext()) {
				intm.add(s.next());
			}
			words = new String[intm.size()];
			for (int i = 0; i < words.length; i++) {
				words[i] = intm.get(i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addLineToFile(File file, String line) {
		try {
			FileWriter fileWriter = new FileWriter(file, true);
			BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
			bufferWriter.newLine();
			bufferWriter.write(line);
			bufferWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/

	public void trainNet(InputStream input, PrintStream out) {
		Scanner in = new Scanner(input);
		out.println("Training Phase");
		boolean exit = false;
		while (!exit) {
			out.println("Enter command, a=add, s=submit");
			String in1 = in.nextLine();
			if (in1.equals("a")) {
				out.println("Enter input");
				double[] in2 = this.stringToDoub(in.nextLine());
				out.println("Enter output");
				double[] out2 = this.stringToDoub(in.nextLine());
				data.put(in2, out2);
			} else if (in1.equals("s")) {
				exit = true;
			} else {
				out.println("Error: command not found");
			}
		}
		in.close();
		for(double[] key : data.keySet()) {
			System.out.println("Input: " + Arrays.toString(key));
			System.out.println("Output: " + Arrays.toString(data.get(key)));
		}
	}

	public double[] stringToDoub(String s) {
		char[] chars = s.toCharArray();
		double[] nums = new double[chars.length];
		for (int i = 0; i < chars.length; i++) {
			if(chars[i] == ' ') {
				nums[i] = 0;
			} else {
			Character c = new Character(chars[i]);
			nums[i] = (double)((int)c.charValue() - 94)/30;
			}
		}
		return nums;
	}

}
