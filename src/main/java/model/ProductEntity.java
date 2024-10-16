package model;

public class ProductEntity {
	private String productName;
	private double price;
	private int quantity;
	private String categoryType;
	private String description;
	private int productId;
	private int categoryId;

	public ProductEntity(String productName, String categoryType, double price, int quantity, String description) {
		this.productName = productName;
		this.price = price;
		this.quantity = quantity;
		this.categoryType = categoryType;
		this.description = description;
	}

	public ProductEntity(int productId, String productName, String categoryType, double price, int categoryId,
			int quantity, String description) {
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.description = description;
		this.quantity = quantity;
		this.categoryType = categoryType;
		this.categoryId = categoryId;
	}

	public ProductEntity(int quantity) {
		this.quantity = quantity;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String toString() {
		return "Product { productId = " + productId + " , " + "productName = " + productName + " , categoryType = "
				+ categoryType + " , price = " + price + " , " + "quantity = " + quantity + " ,description = "
				+ description + " ,categoryId = " + categoryId + "}";
	}
}