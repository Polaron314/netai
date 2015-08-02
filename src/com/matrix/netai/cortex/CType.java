package com.matrix.netai.cortex;

public enum CType {
	sgr(0), sim(1), sit(2);
	
	private int index;
	
	CType(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
	
	public String getType() {
		return name().substring(0, 1);
	}
	
	public String getSubType() {
		return name().substring(1);
	}
	
	public static CType getFromIndex(int index) {
		switch(index) {
		case 0:
			return CType.sgr;
		case 1:
			return CType.sim;
		case 2:
			return CType.sit;
		default:
			return null;
		}
	}
}
