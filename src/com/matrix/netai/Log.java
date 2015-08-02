package com.matrix.netai;

import java.io.PrintStream;

public class Log {
	
	private final PrintStream out;
	
	public Log(PrintStream out) {
		this.out = out;
	}
	
	public void info(String info) {
		out.println("[INFO]" + info);
	}
	
	public void error(String error) {
		out.println("[ERROR]" + error);
	}
	
}
