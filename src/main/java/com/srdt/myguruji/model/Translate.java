package com.srdt.myguruji.model;

public class Translate {
	private long id;
	private String title;
	private String code;
	private String Descr;
	public Translate() {
		super();
	}
	
	public Translate(long id, String title) {
		super();
		this.id = id;
		this.title = title;
	}
	public Translate(String code, String descr) {
		super();
		this.code = code;
		this.Descr = descr;
	}
	public Translate(long id, String title, String descr) {
		super();
		this.id = id;
		this.title = title;
		Descr = descr;
	}
	public Translate(long id, String title, String code, String descr) {
		super();
		this.id = id;
		this.title = title;
		this.code = code;
		Descr = descr;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescr() {
		return Descr;
	}

	public void setDescr(String descr) {
		Descr = descr;
	}

	public long getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setTitle(String title) {
		this.title = title;
	}    
}
