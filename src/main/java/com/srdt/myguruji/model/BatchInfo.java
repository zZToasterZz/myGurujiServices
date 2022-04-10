package com.srdt.myguruji.model;

import java.util.List;

public class BatchInfo {
	
	private Batches batch;
	private List<Faculty> faculty;
	private List<Student> students;
	public BatchInfo() {
		super();
	}
	public BatchInfo(Batches batch, List<Faculty> faculty, List<Student> students) {
		super();
		this.batch = batch;
		this.faculty = faculty;
		this.students = students;
	}
	public Batches getBatch() {
		return batch;
	}
	public void setBatch(Batches batch) {
		this.batch = batch;
	}
	public List<Faculty> getFaculty() {
		return faculty;
	}
	public void setFaculty(List<Faculty> faculty) {
		this.faculty = faculty;
	}
	public List<Student> getStudents() {
		return students;
	}
	public void setStudents(List<Student> students) {
		this.students = students;
	}
}
