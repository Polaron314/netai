package com.matrix.grouping;

import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.MLData;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.basic.BasicNeuralData;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.Train;
import org.encog.neural.networks.training.propagation.back.Backpropagation;

public class Brain {

	BasicNetwork network = new BasicNetwork();

	public Brain() {

	}

	public void initNet(int[] layers) {
		for (int i : layers) {
			network.addLayer(new BasicLayer(new ActivationSigmoid(), true, i));
		}
	}

	public void train(double[][] inputs, double[][] outputs) {
		network.reset();
		NeuralDataSet trainingSet = new BasicNeuralDataSet(inputs, outputs);
		final Train train = new Backpropagation(network, trainingSet);
		int epoch = 1;

		do {

			train.iteration();
			System.out.println("Epoch #" + epoch + " Error:" + train.getError());
			epoch++;

		} while (train.getError() > 0.01);
	}
	
	public double[] calculate(double[] inputs) {
		MLData in = new BasicNeuralData(inputs);
		return network.compute(in).getData();
	}
}
