package com.matrix.netai;

import java.util.List;
import java.util.Scanner;

public class Start {
	
	public static Log log = new Log(System.out);
	public static double errorMargin = 0.005;
	public static List<String> words;

	public static void main(String[] args) {
		Trainer t = new Trainer();
		Brain b = new Brain();
		t.trainNet(b);
		Scanner s = new Scanner(System.in);
		while(true) {
			Start.log.info(b.calculate(s.nextLine()));
		}
	}

}
