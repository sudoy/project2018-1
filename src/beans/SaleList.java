package beans;

public class SaleList {
	private int saleDate;
	private String staffName;
	private String categoryName;
	private String tradeName;
	private int unitPrice;
	private String saleNumber;
	private int total;
	private String note;

	public SaleList(int saleDate, String staffName, String categoryName, String tradeName, int unitPrice,
			String saleNumber, int total, String note) {
		super();
		this.saleDate = saleDate;
		this.staffName = staffName;
		this.categoryName = categoryName;
		this.tradeName = tradeName;
		this.unitPrice = unitPrice;
		this.saleNumber = saleNumber;
		this.total = total;
		this.note = note;
	}

	public int getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(int saleDate) {
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

	public String getSaleNumber() {
		return saleNumber;
	}

	public void setSaleNumber(String saleNumber) {
		this.saleNumber = saleNumber;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}



}
