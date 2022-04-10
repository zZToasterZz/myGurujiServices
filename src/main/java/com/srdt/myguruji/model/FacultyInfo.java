package com.srdt.myguruji.model;

import java.util.List;

public class FacultyInfo {
	
	private Faculty faculty;
	private List<Batches> batches;
	public FacultyInfo() {
		super();
	}
	public FacultyInfo(Faculty faculty, List<Batches> batches) {
		super();
		this.faculty = faculty;
		this.batches = batches;
	}
	public Faculty getFaculty() {
		return faculty;
	}
	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}
	public List<Batches> getBatches() {
		return batches;
	}
	public void setBatches(List<Batches> batches) {
		this.batches = batches;
	}
}
