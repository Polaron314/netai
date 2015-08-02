package com.matrix.netai;

import java.util.Arrays;
import java.util.Scanner;

import com.matrix.netai.cortex.CType;

public class Start {
	
	public static Log log = new Log(System.out);

	public static void main(String[] args) {
		Trainer t = new Trainer();
		Brain b = new Brain();
		t.trainNet(b);
		while(true) {
			Scanner s = new Scanner(System.in);
			double[] output = b.calculate(t.dataToNeu(t.stringToDoub(s.nextLine())));
			System.out.println(Arrays.toString(output));
			int cint = 0;
			double max = 0;
			for(int i = 0; i < output.length; i++) {
				if(output[i] > max) {
					cint = i;
					max = output[i];
				}
			}
			Start.log.info(CType.getFromIndex(cint).name());
		}
	}

}
