package com.srdt.myguruji.enitity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity 
@Table(name = "AutoSeq")
@NamedQueries({
	@NamedQuery(name = "AutoSeq.SetTmplSeq", query = "update AutoSeq set TmplSeq=TmplSeq+10 where AutoSeqId=1"),
	@NamedQuery(name = "AutoSeq.SetAssessSeq", query = "update AutoSeq set AssessSeq=AssessSeq+10 where AutoSeqId=1"),
	@NamedQuery(name = "AutoSeq.findAutoSeqById",query = "select a from AutoSeq a where AutoSeqId=1")
})
public class AutoSeq {
	
	@Id
	private long AutoSeqId;
	@Column
	private long TmplSeq;
	@Column
	private long AssessSeq;
	public AutoSeq() {
		super();
	}
	
	public long getAssessSeq() {
		return AssessSeq;
	}

	public void setAssessSeq(long assessSeq) {
		AssessSeq = assessSeq;
	}

	public long getAutoSeqId() {
		return AutoSeqId;
	}
	public void setAutoSeqId(long autoSeqId) {
		AutoSeqId = autoSeqId;
	}
	public long getTmplSeq() {
		return TmplSeq;
	}
	public void setTmplSeq(long tmplSeq) {
		TmplSeq = tmplSeq;
	}    
}
