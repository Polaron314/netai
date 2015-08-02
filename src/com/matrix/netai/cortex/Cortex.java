package com.matrix.netai.cortex;

public interface Cortex {
	
	public void train(double[][] in, double[][] out);
	
	public double[] calculate(double[] in);
	
	public CType getType();
}
