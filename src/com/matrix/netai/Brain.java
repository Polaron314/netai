package com.matrix.netai;

import java.util.Collection;
import java.util.HashMap;

import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.ml.data.MLData;
import org.encog.neural.data.NeuralDataSet;
import org.encog.neural.data.basic.BasicNeuralData;
import org.encog.neural.data.basic.BasicNeuralDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.Train;
import org.encog.neural.networks.training.propagation.back.Backpropagation;

import com.matrix.entity.*;
import com.matrix.netai.cortex.*;

public class Brain {

	private BasicNetwork network = new BasicNetwork();
	private HashMap<CType, Cortex> cortexes = new HashMap<CType, Cortex>();

	public Brain() {
		
	}
	
	public void typesHandled(CType[] types) {
		for(CType type : types) {
			if(type.equals(CType.squ)) {
				Entity[] entities = {new WeatherEntity()};
				cortexes.put(type, new QueryCortex(type, this, entities));
			} else
				cortexes.put(type, new BasicCortex(type, this));
		}
	}

	public void initNet(int[] layers) {
		for (int i : layers) {
			network.addLayer(new BasicLayer(new ActivationSigmoid(), true, i));
		}
		network.getStructure().finalizeStructure();
	}

	public void train(String[] s, CType[] types) {
		double[][] inputs = new double[s.length][Start.words.size()];
		double[][] outputs = new double[types.length][4];
		for(int i = 0; i < s.length; i++) {
			inputs[i] = Utils.stringToNeu(s[i]);
			double[] a = {types[i].getIndex()};
			outputs[i] = Utils.dataToNeu(a);
		}
		int[] layers = {inputs[0].length, inputs[0].length, outputs[0].length, outputs[0].length};
		initNet(layers);
		network.reset();
		NeuralDataSet trainingSet = new BasicNeuralDataSet(inputs, outputs);
		final Train train = new Backpropagation(network, trainingSet);
		int epoch = 1;

		do {

			train.iteration();
			Start.log.info("Brain: Epoch #" + epoch + " Error:" + train.getError());
			epoch++;

		} while (train.getError() > Start.errorMargin);
	}
	
	public Cortex[] getCortexes() {
		Collection<Cortex> cs = cortexes.values();
		return cs.toArray(new Cortex[cs.size()]);
	}
	
	public CType shallowCalculate(String input) {
		double[] inputs = Utils.stringToNeu(input);
		MLData in = new BasicNeuralData(inputs);
		return dataToType(network.compute(in).getData());
	}
	
	public String calculate(String input) {
		double[] inputs = Utils.stringToNeu(input);
		MLData in = new BasicNeuralData(inputs);
		CType type = dataToType(network.compute(in).getData());
		Cortex c = this.cortexes.get(type);
		return c.calculate(input);
	}
	
	public CType dataToType(double[] d) {
		int cint = 0;
		double max = 0;
		for(int i = 0; i < d.length; i++) {
			if(d[i] > max) {
				cint = i;
				max = d[i];
			}
		}
		return CType.getFromIndex(cint);
	}
	
	
	
}
