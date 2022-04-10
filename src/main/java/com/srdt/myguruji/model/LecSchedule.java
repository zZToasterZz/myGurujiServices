package com.srdt.myguruji.model;

public class LecSchedule
{
	private long lecschid;
	private String starturl;
	private String joinurl;
	private long duration;
	private String descr;
	private String title;
	private String starttime;
	private String uuid;
	private String hostid;
	private String meetingid;
	private String types;
	private String createdby;
	private long courseid;
	private long unitid;
	private String unittitle;
	private String coursetitle;
	
	public LecSchedule()
	{
		super();
	}
	public LecSchedule(long lecschid, String starturl, String joinurl, long duration, String descr, String title,
			String starttime, String uuid, String hostid, String meetingid, String types, String createdby, long courseid,
			long unitid, String unittitle, String coursetitle)
	{
		super();
		this.lecschid = lecschid;
		this.starturl = starturl;
		this.joinurl = joinurl;
		this.duration = duration;
		this.descr = descr;
		this.title = title;
		this.starttime = starttime;
		this.uuid = uuid;
		this.hostid = hostid;
		this.meetingid = meetingid;
		this.types = types;
		this.createdby = createdby;
		this.courseid = courseid;
		this.unitid = unitid;
		this.unittitle = unittitle;
		this.coursetitle = coursetitle;
	}
	public long getCourseid() {
		return courseid;
	}
	public void setCourseid(long courseid) {
		this.courseid = courseid;
	}
	public long getUnitid() {
		return unitid;
	}
	public void setUnitid(long unitid) {
		this.unitid = unitid;
	}
	public String getUnittitle() {
		return unittitle;
	}
	public void setUnittitle(String unittitle) {
		this.unittitle = unittitle;
	}
	public String getCoursetitle() {
		return coursetitle;
	}
	public void setCoursetitle(String coursetitle) {
		this.coursetitle = coursetitle;
	}
	public long getLecschid() {
		return lecschid;
	}
	public void setLecschid(long lecschid) {
		this.lecschid = lecschid;
	}
	public String getStarturl() {
		return starturl;
	}
	public void setStarturl(String starturl) {
		this.starturl = starturl;
	}
	public String getJoinurl() {
		return joinurl;
	}
	public void setJoinurl(String joinurl) {
		this.joinurl = joinurl;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getHostid() {
		return hostid;
	}
	public void setHostid(String hostid) {
		this.hostid = hostid;
	}
	public String getMeetingid() {
		return meetingid;
	}
	public void setMeetingid(String meetingid) {
		this.meetingid = meetingid;
	}
	public String getTypes() {
		return types;
	}
	public void setTypes(String types) {
		this.types = types;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}	
}
