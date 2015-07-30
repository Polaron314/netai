package com.matrix.grouping;

import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.training.Train;
import org.encog.neural.networks.training.propagation.back.Backpropagation;

public class Brain {
	
	BasicNetwork network = new BasicNetwork();
	
	public Brain() {
		
	}
	
	public void train(double[][] inputs, double[][]outputs) {
		network.reset();
		NeuralDataSet trainingSet = new BasicNeuralDataSet(inputs, outputs);
		final Train train = new Backpropagation(network, trainingSet);
		int epoch = 1;

		do {

		  train.iteration();
		  System.out.println("Epoch #" + epoch + 
		                     " Error:" + train.getError());
		  epoch++;

		} while(train.getError() > 0.01);
	}
}
