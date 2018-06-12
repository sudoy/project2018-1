package com.abc.asms;

import java.sql.Date;

public class SaleList {
	private Date saleDate;
	private String staffName;
	private String categoryName;
	private String tradeName;
	private int unitPrice;
	private int saleNumber;
	private String note;

	public SaleList(Date saleDate, String staffName, String categoryName, String tradeName, int unitPrice,
			int saleNumber, String note) {
		super();
		this.saleDate = saleDate;
		this.staffName = staffName;
		this.categoryName = categoryName;
		this.tradeName = tradeName;
		this.unitPrice = unitPrice;
		this.saleNumber = saleNumber;
		this.note = note;
	}

	public Date getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public int getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}

	public int getSaleNumber() {
		return saleNumber;
	}

	public void setSaleNumber(int saleNumber) {
		this.saleNumber = saleNumber;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}




}
