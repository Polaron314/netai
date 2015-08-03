package com.matrix.netai.cortex;

import java.util.ArrayList;
import java.util.List;

import org.encog.ml.data.MLData;
import org.encog.neural.data.basic.BasicNeuralData;

import com.matrix.entity.Entity;
import com.matrix.netai.Brain;
import com.matrix.netai.Utils;

public class QueryCortex extends BasicCortex implements Cortex{

	private List<Entity> entities = new ArrayList<Entity>();
	
	public QueryCortex(CType type, Brain brain, Entity[] es) {
		super(type, brain);
		for(Entity e : es) {
			entities.add(e);
		}
	}
	
	public String calculate(String input) {
		double[] inputs = Utils.stringToNeu(input);
		MLData in = new BasicNeuralData(inputs);
		return this.replaceEntities(setOrder(Utils.neuToString(network.compute(in).getData())));
	}
	
	public String replaceEntities(String s) {
		List<String> process = new ArrayList<String>();
		process.add(s);
		for(Entity e : entities) {
			process.add(e.processString(process.get(process.size() - 1)));
		}
		return process.get(process.size() - 1);
	}
	
}
