package com.srdt.myguruji.model;

public class Books {
	
	private long bookid;
	private String booktitle;
	private String bookdescr;
	private String bookauthor;
	private String booktype;
    private long courseplanid;
	public Books() {
		super();
	}
	public Books(String booktitle, String bookdescr, String bookauthor, String booktype, long courseplanid) {
		super();
		this.booktitle = booktitle;
		this.bookdescr = bookdescr;
		this.bookauthor = bookauthor;
		this.booktype = booktype;
		this.courseplanid = courseplanid;
	}
	
	public Books(long bookid, String booktitle, String bookdescr, String bookauthor, String booktype,
			long courseplanid) {
		super();
		this.bookid = bookid;
		this.booktitle = booktitle;
		this.bookdescr = bookdescr;
		this.bookauthor = bookauthor;
		this.booktype = booktype;
		this.courseplanid = courseplanid;
	}
	
	public long getBookid() {
		return bookid;
	}
	public void setBookid(long bookid) {
		this.bookid = bookid;
	}
	public String getBooktitle() {
		return booktitle;
	}
	public void setBooktitle(String booktitle) {
		this.booktitle = booktitle;
	}
	public String getBookdescr() {
		return bookdescr;
	}
	public void setBookdescr(String bookdescr) {
		this.bookdescr = bookdescr;
	}
	public String getBookauthor() {
		return bookauthor;
	}
	public void setBookauthor(String bookauthor) {
		this.bookauthor = bookauthor;
	}
	public String getBooktype() {
		return booktype;
	}
	public void setBooktype(String booktype) {
		this.booktype = booktype;
	}
	public long getCourseplanid() {
		return courseplanid;
	}
	public void setCourseplanid(long courseplanid) {
		this.courseplanid = courseplanid;
	}    
}
