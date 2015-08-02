package com.matrix.netai;

import java.util.Scanner;

public class Start {
	
	public static Log log = new Log(System.out);
	public static double errorMargin = 0.01;

	public static void main(String[] args) {
		Trainer t = new Trainer();
		Brain b = new Brain();
		t.trainNet(b);
		while(true) {
			Scanner s = new Scanner(System.in);
			double[] output = b.calculate(t.dataToNeu(t.stringToDoub(s.nextLine())));
			Start.log.info(t.doubToString(t.neuToData(output)));
		}
	}

}
