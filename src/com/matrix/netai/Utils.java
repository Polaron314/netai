package com.matrix.netai;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utils {

	public static File dict = new File("words.dict");
	
	public static double[] stringToDoub(String s) {
		String[] wds = s.toLowerCase().split(" ");
		double[] ret = new double[wds.length];
		for(int i = 0; i < wds.length; i++) {
			if(Start.words.contains(wds[i]))
				ret[i] = Start.words.indexOf(wds[i]);
			else {
				addLineToFile(dict, wds[i]);
				initDict();
				ret[i] = Start.words.indexOf(wds[i]);
			}
		}
		return ret;
	}
	
	public static void initDict() {
			Start.words = new ArrayList<String>();
			try {
				Scanner s = new Scanner(dict);
				while (s.hasNext()) {
					Start.words.add(s.nextLine().toLowerCase());
				}
				s.close();
				Start.log.info(String.format("Added %s words", Start.words.size()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	public static String doubToString(double[] d) {
		String[] wds = new String[d.length];
		for(int i = 0; i < d.length; i++) {
			wds[i] = Start.words.get((int) d[i]);
		}
		String ret = "";
		for(String s : wds)
			ret += " " + s;
		return ret.substring(1);
	}
	
	public static double[] dataToNeu(double[] data) {
		double[] fin = new double[Start.words.size()];
		for(int i = 0; i < fin.length; i++)
			fin[i] = 0;
		for(double d : data)
			fin[(int)d] = 1;
		return fin;
	}
	
	public static double[] neuToData(double[] neu) {
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
	
	public static String neuToString(double[] neu) {
		double[] data = Utils.neuToData(neu);
		return Utils.doubToString(data);
	}
	
	public static double[] stringToNeu(String s) {
		double[] data = Utils.stringToDoub(s);
		double[] neu = Utils.dataToNeu(data);
		return neu;
	}
	
	public static void addLineToFile(File file, String line) {
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
}
