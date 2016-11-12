package com.prima.pricer.model;

public class PriceBookRecord {
    private String supplierId;
    private String articul;
    private String name;
    private String price;
    private String quantity;
    private boolean hasRetailPrice;
    private boolean isAvailable;
    // is newly created during last successful run of app. Update on scnd time of existance of this record to false.
    private boolean isNew;
    private Double retailPriceMultiplierPercent;
    private int rowNumber;

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getArticul() {
        return articul;
    }

    public void setArticul(String articul) {
        this.articul = articul;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public boolean hasRetailPrice() {
        return hasRetailPrice;
    }

    public void setRetailPrice(boolean hasRetailPrice) {
        this.hasRetailPrice = hasRetailPrice;
    }

    public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public Double getRetailPriceMultiplierPercent() {
        return retailPriceMultiplierPercent;
    }

    public void setRetailPriceMultiplierPercent(Double retailPriceMultiplierPercent) {
        this.retailPriceMultiplierPercent = retailPriceMultiplierPercent;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    @Override
    public String toString() {
        return "PriceBookRecord{" +
                "rowNumber='" + rowNumber + '\'' +
                ", articul='" + articul + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", quantity='" + quantity + '\'' +
                ", hasRetailPrice=" + hasRetailPrice +
                ", isAvailable=" + isAvailable +
                ", isNew=" + isNew +
                '}';
    }
}