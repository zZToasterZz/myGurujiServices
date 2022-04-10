package com.srdt.myguruji.enitity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.srdt.myguruji.utility.Generation;

@Entity
@Table(name =  "WebSession")
public class WebSession implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(allocationSize = 1,initialValue = 1,name = "WebSession_Sqr",sequenceName = "WebSession_Sqr")
	@GeneratedValue(generator = "WebSession_Sqr", strategy = GenerationType.SEQUENCE)
	private long WebId;
	@Column
	private long SessionCount;
	@Column
	private long LoginCount;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date SyncDate;
	
	public WebSession() {
		super();
	}

	public WebSession(long sessionCount, long loginCount) {
		super();
		SessionCount = sessionCount;
		LoginCount = loginCount;
		SyncDate = Generation.getCurrentDate();
	}

	public long getWebId() {
		return WebId;
	}

	public void setWebId(long webId) {
		WebId = webId;
	}

	public long getSessionCount() {
		return SessionCount;
	}

	public void setSessionCount(long sessionCount) {
		SessionCount = sessionCount;
	}

	public long getLoginCount() {
		return LoginCount;
	}

	public void setLoginCount(long loginCount) {
		LoginCount = loginCount;
	}

	public Date getSyncDate() {
		return SyncDate;
	}

	public void setSyncDate(Date syncDate) {
		SyncDate = syncDate;
	}
}
