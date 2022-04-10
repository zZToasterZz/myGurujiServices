package com.srdt.myguruji.enitity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="QuestionType")
@NamedQueries(
		@NamedQuery(name="QuestionType.getactivetype",query="select a from QuestionType a where a.IsActive='Y' order by a.TypeId")
)
public class QuestionType extends SharedField implements Serializable
{
	private static final long serialVersionUID = 1L;
	@Id
	@Column
	private long TypeId;
	@Column(length=50,unique=true,nullable=false)
	private String Type;
	@Column(nullable=true)
	private String Descr;
	
	@OneToMany(mappedBy="questionType",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	List<QuestionDetails> questionDetails = new ArrayList<>();
	
	public QuestionType() {
		super();
	}
	public QuestionType(String type, String descr,String createdBy) {
		super();
		Type = type;
		Descr = descr;
		setCreatedBy(createdBy);
		setModifiedBy(createdBy);
	}
	public long getTypeId() {
		return TypeId;
	}
	public void setTypeId(long typeId) {
		TypeId = typeId;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getDescr() {
		return Descr;
	}
	public void setDescr(String descr) {
		Descr = descr;
	}	
}
