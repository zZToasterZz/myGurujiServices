package com.srdt.myguruji.model.reporting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuesBankData {
	
	private String[] cols;
	private List<String[]> data=new ArrayList<String[]>();
	
	public QuesBankData() {
		super();
	}

	public QuesBankData(String[] cols, List<String[]> data) {
		super();
		this.cols = cols;
		this.data = data;
	}

	public String[] getCols() {
		return cols;
	}

	public void setCols(String[] cols) {
		this.cols = cols;
	}

	public List<String[]> getData() {
		return data;
	}

	public void setData(List<String[]> data) {
		this.data = data;
	}

}
