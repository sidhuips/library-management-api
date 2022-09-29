package com.library.management.model;

import java.io.Serializable;

public class SearchVO implements Serializable{

	private static final long serialVersionUID = -2026666038736759214L;
	private String searchString;

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	
}
