package com.srdt.myguruji.enitity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CourseBooks")
@NamedQueries({
	@NamedQuery(name="CourseBooks.deleteCourseBooksByBookId",query="delete from CourseBooks where BookId in(:BookId)"),
	@NamedQuery(name="CourseBooks.findCourseBookByCoursePlanId",query="select b from CourseBooks b where b.coursePlan.CoursePlanId=:CoursePlanId"),
	@NamedQuery(name="CourseBooks.findAddCourseBooks",query="select b from CourseBooks b")
})
public class CourseBooks extends SharedField implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(allocationSize=1,initialValue=1,name="CourseBooks_Sqr",sequenceName="CourseBooks_Sqr")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="CourseBooks_Sqr")
	@Column
	private long BookId;
	@Column(length=150,nullable=false)
	private String BookTitle;
	@Column(length=500,nullable=true)
	private String BookDescr;
	@Column(length=150,nullable=false)
	private String BookAuthor;
	@Column(length=10,nullable=false)
	private String bookType;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CoursePlanId",referencedColumnName="CoursePlanId",insertable=true,updatable=true)
	private CoursePlan coursePlan = new CoursePlan();
	
	public CourseBooks() {
		super();
	}
	public CourseBooks(String bookTitle, String bookDescr, String bookAuthor,String createdBy,String bookType) {
		super();
		BookTitle = bookTitle;
		BookDescr = bookDescr;
		BookAuthor = bookAuthor;
		setCreatedBy(createdBy);
		setModifiedBy(createdBy);
		this.bookType = bookType;
	}
	public CourseBooks(String bookTitle, String bookDescr, String bookAuthor,String createdBy,String bookType,CoursePlan coursePlan) {
		super();
		BookTitle = bookTitle;
		BookDescr = bookDescr;
		BookAuthor = bookAuthor;
		setCreatedBy(createdBy);
		setModifiedBy(createdBy);
		this.bookType = bookType;
		this.coursePlan = coursePlan;
	}
	public CourseBooks(String bookTitle, String bookDescr, String bookAuthor,String createdBy,String bookType,CoursePlan coursePlan,long bookId) {
		super();
		BookTitle = bookTitle;
		BookDescr = bookDescr;
		BookAuthor = bookAuthor;
		setCreatedBy(createdBy);
		setModifiedBy(createdBy);
		this.bookType = bookType;
		this.coursePlan = coursePlan;
		BookId = bookId;
	}
	public String getBookType() {
		return bookType;
	}
	public void setBookType(String bookType) {
		this.bookType = bookType;
	}
	public CoursePlan getCoursePlan() {
		return coursePlan;
	}
	public void setCoursePlan(CoursePlan coursePlan) {
		this.coursePlan = coursePlan;
	}
	public long getBookId() {
		return BookId;
	}
	public void setBookId(long bookId) {
		BookId = bookId;
	}
	public String getBookTitle() {
		return BookTitle;
	}
	public void setBookTitle(String bookTitle) {
		BookTitle = bookTitle;
	}
	public String getBookDescr() {
		return BookDescr;
	}
	public void setBookDescr(String bookDescr) {
		BookDescr = bookDescr;
	}
	public String getBookAuthor() {
		return BookAuthor;
	}
	public void setBookAuthor(String bookAuthor) {
		BookAuthor = bookAuthor;
	}	
}
