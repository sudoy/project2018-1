package com.abc.asms.beans;

import java.time.LocalDate;

public class Sales {

	private int saleId;
	private LocalDate saleDate;
	private String account;
	private String category;
	private String tradeName;
	private int unitPrice;
	private int saleNumber;
	private String note;
	private int version;

	public Sales(int saleId, LocalDate saleDate, String category, String tradeName, int unitPrice, int saleNumber) {
		super();
		this.saleId = saleId;
		this.saleDate = saleDate;
		this.category = category;
		this.tradeName = tradeName;
		this.unitPrice = unitPrice;
		this.saleNumber = saleNumber;
	}

	public Sales(int saleId, LocalDate saleDate, String account, String category, String tradeName, int unitPrice, int saleNumber,
			String note) {
		super();
		this.saleId = saleId;
		this.saleDate = saleDate;
		this.account = account;
		this.category = category;
		this.tradeName = tradeName;
		this.unitPrice = unitPrice;
		this.saleNumber = saleNumber;
		this.note = note;
	}

	public Sales(int saleId, LocalDate saleDate, String account, String category, String tradeName, int unitPrice, int saleNumber,
			String note, int version) {
		super();
		this.saleId = saleId;
		this.saleDate = saleDate;
		this.account = account;
		this.category = category;
		this.tradeName = tradeName;
		this.unitPrice = unitPrice;
		this.saleNumber = saleNumber;
		this.note = note;
		this.version = version;
	}

	public int getSaleId() {
		return saleId;
	}
	public void setSaleId(int saleId) {
		this.saleId = saleId;
	}
	public LocalDate getSaleDate() {
		return saleDate;
	}
	public void setSaleDate(LocalDate saleDate) {
		this.saleDate = saleDate;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
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
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}

}
