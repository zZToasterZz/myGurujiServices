package com.srdt.myguruji.model;

public enum BookType {

	TextBook("txt","Text Book"), ReferenceBook("refer","Reference Book");
	 
    private String code,descr;
 
    private BookType(String code,String descr) {
        this.code = code;
        this.descr = descr;
    }
 
    public String getCode() {
        return code;
    }
    
    public String getDescr()
    {
    	return descr;
    }
}
