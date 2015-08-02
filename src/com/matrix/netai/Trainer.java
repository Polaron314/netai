package com.matrix.netai;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.matrix.netai.cortex.CType;
import com.matrix.netai.cortex.Cortex;

public class Trainer {

	File dict = new File("words.dict");
	File dataFile = new File("data.csv");
	List<String> words;
	List<double[]> inputData = new ArrayList<double[]>();
	List<double[]> outputData = new ArrayList<double[]>();
	List<CType> typeData = new ArrayList<CType>();

	public Trainer() {
	}
	 
	public void initDict() {
		words = new ArrayList<String>();
		try {
			Scanner s = new Scanner(dict);
			while (s.hasNext()) {
				words.add(s.nextLine().toLowerCase());
			}
			s.close();
			Start.log.info(String.format("Added %s words", words.size()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addLineToFile(File file, String line) {
		try {
			FileWriter fileWriter = new FileWriter(file, true);
			BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
			bufferWriter.newLine();
			bufferWriter.write(line);
			bufferWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadData() {
		initDict();
		try {
			Scanner s = new Scanner(dataFile);
			List<String> intm = new ArrayList<String>();
			while (s.hasNext()) {
				intm.add(s.nextLine().toLowerCase());
			}
			for (String string : intm) {
				String[] split = string.split(",");
				if(split.length == 3) {
				inputData.add(stringToDoub(split[0]));
				outputData.add(stringToDoub(split[1]));
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
		double[][] finIn = new double[inputData.size()][words.size()];
		double[][] finOut = new double[inputData.size()][3];
		for(int i = 0; i < inputData.size(); i++) {
			for(int j = 0; j < words.size(); j++) {
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
					inputs.add(this.dataToNeu(inputData.get(i)));
					outputs.add(this.dataToNeu(outputData.get(i)));
				}
			}
			c.train(inputs.toArray(new double[0][0]), outputs.toArray(new double[0][0]));
		}
	}

	public double[] stringToDoub(String s) {
		String[] wds = s.toLowerCase().split(" ");
		double[] ret = new double[wds.length];
		for(int i = 0; i < wds.length; i++) {
			if(words.contains(wds[i]))
				ret[i] = words.indexOf(wds[i]);
			else {
				this.addLineToFile(dict, wds[i]);
				this.initDict();
				ret[i] = words.indexOf(wds[i]);
			}
		}
		return ret;
	}
	
	public String doubToString(double[] d) {
		String[] wds = new String[d.length];
		for(int i = 0; i < d.length; i++) {
			wds[i] = words.get((int) d[i]);
		}
		String ret = "";
		for(String s : wds)
			ret += " " + s;
		return ret;
	}
	
	public double[] dataToNeu(double[] data) {
		double[] fin = new double[words.size()];
		for(int i = 0; i < fin.length; i++)
			fin[i] = 0;
		for(double d : data)
			fin[(int)d] = 1;
		return fin;
	}
	
	public double[] neuToData(double[] neu) {
		List<Double> intm = new ArrayList<Double>();
		for(int i = 0; i < neu.length; i++) {
			if(Math.round(neu[i]) == 1) 
				intm.add((double) i);
		}
		double[] target = new double[neu.length];
		for (int i = 0; i < intm.size(); i++) {
		    target[i] = intm.get(i);
		}
		return target;
	}
	
}
