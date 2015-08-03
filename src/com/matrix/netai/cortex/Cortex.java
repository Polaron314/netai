package com.matrix.netai.cortex;

public interface Cortex {
	
	public void train(String[] sIn, String[] sOut);
	
	public String calculate(String input);
	
	public CType getType();
}
