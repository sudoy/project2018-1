package com.abc.asms.beans;

public class SearchAccountForm {
	private String name;
	private String kana;
	private String mail;
	private String saleAuthority;
	private String accountAuthority;
	public SearchAccountForm(String name, String kana, String mail, String saleAuthority, String accountAuthority) {
		super();
		this.name = name;
		this.kana = kana;
		this.mail = mail;
		this.saleAuthority = saleAuthority;
		this.accountAuthority = accountAuthority;
	}
	public SearchAccountForm(String name, String mail, String saleAuthority, String accountAuthority) {
		super();
		this.name = name;
		this.mail = mail;
		this.saleAuthority = saleAuthority;
		this.accountAuthority = accountAuthority;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKana() {
		return kana;
	}
	public void setKana(String kana) {
		this.kana = kana;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getSaleAuthority() {
		return saleAuthority;
	}
	public void setSaleAuthority(String saleAuthority) {
		this.saleAuthority = saleAuthority;
	}
	public String getAccountAuthority() {
		return accountAuthority;
	}
	public void setAccountAuthority(String accountAuthority) {
		this.accountAuthority = accountAuthority;
	};
}
