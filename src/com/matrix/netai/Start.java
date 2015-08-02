package com.matrix.netai;

import java.util.List;
import java.util.Scanner;

public class Start {
	
	public static Log log = new Log(System.out);
	public static double errorMargin = 0.01;
	public static List<String> words;

	public static void main(String[] args) {
		Trainer t = new Trainer();
		Brain b = new Brain();
		t.trainNet(b);
		while(true) {
			Scanner s = new Scanner(System.in);
			double[] output = b.calculate(Utils.dataToNeu(Utils.stringToDoub(s.nextLine())));
			Start.log.info(Utils.doubToString(Utils.neuToData(output)));
		}
	}

}
