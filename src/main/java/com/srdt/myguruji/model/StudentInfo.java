package com.srdt.myguruji.model;

import java.util.List;

public class StudentInfo {
	
	private Student student;
	private List<Batches> batches;
	public StudentInfo() {
		super();
	}
	public StudentInfo(Student student, List<Batches> batches) {
		super();
		this.student = student;
		this.batches = batches;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public List<Batches> getBatches() {
		return batches;
	}
	public void setBatches(List<Batches> batches) {
		this.batches = batches;
	}
}
