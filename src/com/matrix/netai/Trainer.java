package com.matrix.netai;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.matrix.netai.cortex.CType;
import com.matrix.netai.cortex.Cortex;

public class Trainer {

	File dataFile = new File("data.csv");
	List<String> inputData = new ArrayList<String>();
	List<String> outputData = new ArrayList<String>();
	List<CType> typeData = new ArrayList<CType>();

	public Trainer() {
	}
	

	
	public void loadData() {
		Utils.initDict();
		try {
			Scanner s = new Scanner(dataFile);
			List<String> intm = new ArrayList<String>();
			while (s.hasNext()) {
				intm.add(s.nextLine().toLowerCase());
			}
			for (String string : intm) {
				String[] split = string.split(",");
				if(split.length == 3) {
				inputData.add(split[0]);
				outputData.add(split[1]);
				typeData.add(Enum.valueOf(CType.class, split[2]));
				}
			}
			s.close();
			Start.log.info(String.format("Added %s defintions", inputData.size()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void trainNet(Brain network) {
		loadData();
		network.train(inputData.toArray(new String[inputData.size()]), typeData.toArray(new CType[typeData.size()]));
		this.trainCortexes(network);
	}
	
	public void trainCortexes(Brain network) {
		List<CType> types = new ArrayList<CType>();
		for(CType dtype : typeData) {
			if(!types.contains(dtype))
				types.add(dtype);
		}
		network.typesHandled(types.toArray(new CType[types.size()]));
		Cortex[] cortexes = network.getCortexes();
		for(Cortex c : cortexes) {
			List<String> inputs = new ArrayList<String>();
			List<String> outputs = new ArrayList<String>();
			for(int i = 0; i < inputData.size(); i++) {
				if(typeData.get(i).equals(c.getType())) {
					inputs.add(inputData.get(i));
					outputs.add(outputData.get(i));
				}
			}
			c.train(inputs.toArray(new String[0]), outputs.toArray(new String[0]));
		}
	}

	
	
}
