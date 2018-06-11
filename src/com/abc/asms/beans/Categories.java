package com.abc.asms.beans;

public class Categories {

	private int categoryId;
	private String categoryName;
	private int activeFlg;

	public Categories(int categoryId, String categoryName, int activeFlg) {
		super();
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.activeFlg = activeFlg;
	}

	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public int getActiveFlg() {
		return activeFlg;
	}
	public void setActiveFlg(int activeFlg) {
		this.activeFlg = activeFlg;
	}
}
