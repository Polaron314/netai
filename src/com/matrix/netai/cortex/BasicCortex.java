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

import com.matrix.netai.Brain;
import com.matrix.netai.Start;

public class BasicCortex implements Cortex{
	
	BasicNetwork network = new BasicNetwork();
	private final CType type;
	private final Brain brain;
	
	public BasicCortex(CType type, Brain brain) {
		this.type = type;
		this.brain = brain;
	}
	
	private void initNet(int[] layers) {
		for (int i : layers) {
			network.addLayer(new BasicLayer(new ActivationSigmoid(), true, i));
		}
		network.getStructure().finalizeStructure();
	}

	public void train(double[][] inputs, double[][] outputs) {
		int[] layers = {inputs[0].length, inputs[0].length, outputs[0].length, outputs[0].length};
		initNet(layers);
		network.reset();
		NeuralDataSet trainingSet = new BasicNeuralDataSet(inputs, outputs);
		final Train train = new Backpropagation(network, trainingSet);
		int epoch = 1;

		do {

			train.iteration();
			Start.log.info("Cortex " + this.type.name() + ": Epoch #" + epoch + " Error:" + train.getError());
			epoch++;

		} while (train.getError() > Start.errorMargin);
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
