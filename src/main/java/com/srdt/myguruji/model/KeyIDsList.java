package com.srdt.myguruji.model;

import java.util.List;

public class KeyIDsList
{
	long[] ids;
	
	public KeyIDsList(long[] ids) {
		super();
		this.ids = ids;
	}
	public KeyIDsList() {
		super();
	}
	public long[] getIds() {
		return ids;
	}
	public void setIds(long[] ids) {
		this.ids = ids;
	}
}