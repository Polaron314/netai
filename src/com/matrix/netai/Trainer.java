package com.matrix.netai;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.matrix.netai.cortex.CType;
import com.matrix.netai.cortex.Cortex;

public class Trainer {

	File dataFile = new File("data.csv");
	List<double[]> inputData = new ArrayList<double[]>();
	List<double[]> outputData = new ArrayList<double[]>();
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
				inputData.add(Utils.stringToDoub(split[0]));
				outputData.add(Utils.stringToDoub(split[1]));
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
		double[][] finIn = new double[inputData.size()][Start.words.size()];
		double[][] finOut = new double[inputData.size()][3];
		for(int i = 0; i < inputData.size(); i++) {
			for(int j = 0; j < Start.words.size(); j++) {
				finIn[i][j] = 0;
			}
			for(int j = 0; j < 3; j++)
				finOut[i][j] = 0;
			for(int j = 0; j < inputData.get(i).length; j++) {
				finIn[i][(int) inputData.get(i)[j]] = 1;
			}
			finOut[i][typeData.get(i).getIndex()] = 1;
		}
		network.train(finIn, finOut);
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
			List<double[]> inputs = new ArrayList<double[]>();
			List<double[]> outputs = new ArrayList<double[]>();
			for(int i = 0; i < inputData.size(); i++) {
				if(typeData.get(i).equals(c.getType())) {
					inputs.add(Utils.dataToNeu(inputData.get(i)));
					outputs.add(Utils.dataToNeu(outputData.get(i)));
				}
			}
			c.train(inputs.toArray(new double[0][0]), outputs.toArray(new double[0][0]));
		}
	}

	
	
}
