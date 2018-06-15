package com.abc.asms.beans;

public class SearchSaleForm {
	private String start;
	private String end;
	private String account;
	private String[] category;
	private String tradeName;
	private String note;
	public SearchSaleForm(String start, String end, String account, String[] category, String tradeName, String note) {
		super();
		this.start = start;
		this.end = end;
		this.account = account;
		this.category = category;
		this.tradeName = tradeName;
		this.note = note;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String[] getCategory() {
		return category;
	}
	public void setCategory(String[] category) {
		this.category = category;
	}
	public String getTradeName() {
		return tradeName;
	}
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}

}
