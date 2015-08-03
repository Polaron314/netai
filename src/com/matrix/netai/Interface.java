package com.matrix.netai;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Interface {
	
	public Interface() {
		this.start(System.in, System.out);
	}
	
	public void start(InputStream in, PrintStream out) {
		out.println("----- INIT TRAIN -----");
		Trainer t = new Trainer();
		Brain b = new Brain();
		t.trainNet(b);
		boolean exit = false;
		out.println("----- WELCOME -----");
		out.println("a: add + retrain");
		out.println("e: exit");
		out.println("or enter command");
		Scanner s = new Scanner(in);
		while(!exit) {
			String line = s.nextLine();
			switch(line) {
			case "a":
				out.println("Enter Input");
				String aIn = s.nextLine();
				out.println("Enter Output");
				String aOut = s.nextLine();
				out.println("Enter Type");
				String aType = s.nextLine();
				this.addData(aIn, aOut, aType);
				t.trainNet(b);
			case "e":
				exit = true;
			default:
				out.println(b.calculate(line));
			}
		}
		out.println("----- GOODBYE -----");
		System.exit(0);
	}
	
	private void addData(String input, String output, String type) {
		Utils.addLineToFile(new File("data.csv"), input + "," + output + "," + type);
	}
}
