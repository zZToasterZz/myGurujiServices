package com.srdt.myguruji.enitity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="DifficultyLevel")
public class DifficultyLevel extends SharedField implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id	
	@Column
	private long DifficultyId;
	@Column(unique=true,nullable=false,length=50)
	private String DiffLevel;
	
	@OneToMany(mappedBy="difficultyLevel",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<QuestionDetails> questionDetails = new ArrayList<>();
	
	public DifficultyLevel() {
		super();
	}
	
	public DifficultyLevel(String diffLevel,String createdBy) {
		super();
		DiffLevel = diffLevel;
		setCreatedBy(createdBy);
		setModifiedBy(createdBy);
	}

	public List<QuestionDetails> getQuestionDetails() {
		return questionDetails;
	}

	public void setQuestionDetails(List<QuestionDetails> questionDetails) {
		this.questionDetails = questionDetails;
	}

	public long getDifficultyId() {
		return DifficultyId;
	}
	public void setDifficultyId(long difficultyId) {
		DifficultyId = difficultyId;
	}
	public String getDiffLevel() {
		return DiffLevel;
	}
	public void setDiffLevel(String diffLevel) {
		DiffLevel = diffLevel;
	}    
}
