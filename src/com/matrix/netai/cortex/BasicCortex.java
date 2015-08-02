package com.matrix.netai.cortex;

import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.MLData;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.basic.BasicNeuralData;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.Train;
import org.encog.neural.networks.training.propagation.back.Backpropagation;

public class BasicCortex implements Cortex{
	
	BasicNetwork network = new BasicNetwork();
	private final CType type;
	
	BasicCortex(CType type) {
		this.type = type;
	}
	
	private void initNet(int[] layers) {
		for (int i : layers) {
			network.addLayer(new BasicLayer(new ActivationSigmoid(), true, i));
		}
	}

	public void train(double[][] inputs, double[][] outputs, int[] layers) {
		this.initNet(layers);
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

	@Override
	public CType getType() {
		return type;
	}
	
}
