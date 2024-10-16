package service;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import controller.ConnectionJdbc;
import model.ProductEntity;

public class InventoryService {
	private static final ConnectionJdbc connection = new ConnectionJdbc();
	protected PreparedStatement preparedstatement = null;
	protected Connection connect = null;
	protected ResultSet resultSet = null;

	public InventoryService() {

		try {
			String product = "create table IF NOT EXISTS Product (" + "productId INT AUTO_INCREMENT PRIMARY KEY, "
					+ "productName VARCHAR(100), " + "categoryId int, " + "price Double(10,2), " + "quantity int, "
					+ "description VARCHAR(100))";

			String category = "create table if not exists Category(categoryId int Auto_increment primary key,"
					+ "categoryType varchar(100)," + "brand varchar(100))" + "AUTO_INCREMENT = 101;";

			connect = connection.connect();
			Statement statement = connect.createStatement();
			statement.executeUpdate(product);
			statement.executeUpdate(category);

		} catch (Exception e) {
			System.out.println("Connection error.");
		}
	}

	public void addProduct(ProductEntity product) throws SQLException {

		String categoryInsert = "insert into Category(categoryType,brand) values(?,?)";
		PreparedStatement categoryId = connect.prepareStatement(categoryInsert);
		categoryId.setString(1, product.getCategoryType());
		categoryId.setString(2, product.getDescription());
		categoryId.executeUpdate();

		String productStatement = "INSERT INTO Product (productName, categoryId, price, quantity,description) "
				+ "VALUES (?, (SELECT Max(categoryId) FROM Category WHERE categoryType = ?), ?, ?, ?)";
		preparedstatement = connect.prepareStatement(productStatement);

		preparedstatement.setString(1, product.getProductName());
		preparedstatement.setString(2, product.getCategoryType());
		preparedstatement.setDouble(3, product.getPrice() * product.getQuantity());
		preparedstatement.setInt(4, product.getQuantity());
		preparedstatement.setString(5, product.getDescription());
		preparedstatement.executeUpdate();
		System.out.println("Product added successfully");
	}

	public void updateStock(int productId, ProductEntity product) throws SQLException {

		int addValue = 0;
		int addPrice = 0;
		String getQuantity = "SELECT quantity,price FROM Product WHERE productId = ? ";
		preparedstatement = connect.prepareStatement(getQuantity);
		preparedstatement.setInt(1, productId);
		resultSet = preparedstatement.executeQuery();
		if (resultSet.next()) {
			addValue = resultSet.getInt("quantity");
			addPrice = resultSet.getInt("price");

		}
		if ((addValue + product.getQuantity()) > 0) {
			String update = "UPDATE Product SET quantity = ? ,price= ? WHERE productId = ?";
			PreparedStatement pst = connect.prepareStatement(update);
			pst.setInt(1, (product.getQuantity() + addValue));
			pst.setDouble(2, (addPrice / addValue) * (product.getQuantity() + addValue));
			pst.setInt(3, productId);

			pst.executeUpdate();
			System.out.println("Stock updated successfully.");
		} else {
			System.out.println("Your old quantity value is: " + (addValue) + ", so give greater than " + addValue);
		}

	}

	public void allProduct() throws SQLException {
		String select = "SELECT Product.productId, Product.productName, " + "Category.categoryType, Product.price, "
				+ "Category.categoryId," + "Product.quantity, Product.description " + "FROM Product JOIN Category ON "
				+ "Product.categoryId = Category.categoryId";
		preparedstatement = connect.prepareStatement(select);
		resultSet = preparedstatement.executeQuery();
		InventoryService view = new InventoryService();
		view.output(resultSet);
	}

	public void getProductsByCategory(String categoryType) throws SQLException {
		String select = "SELECT Product.productId, Product.productName, " + "Category.categoryType, Product.price, "
				+ "Category.categoryId, " + "Product.quantity, Product.description " + "FROM Product JOIN Category ON "
				+ "Product.categoryId = Category.categoryId " + "where CategoryType= ? ";
		preparedstatement = connect.prepareStatement(select);
		preparedstatement.setString(1, categoryType);
		resultSet = preparedstatement.executeQuery();
		InventoryService view = new InventoryService();
		view.output(resultSet);

	}

	public void getSortedProductsByPrice() throws SQLException {
		String select = "SELECT Product.productId, Product.productName, " + "Category.categoryType, Product.price, "
				+ "Category.categoryId," + "Product.quantity, Product.description " + "FROM Product JOIN Category ON "
				+ "Product.categoryId = Category.categoryId order by price";
		preparedstatement = connect.prepareStatement(select);
		resultSet = preparedstatement.executeQuery();
		InventoryService view = new InventoryService();
		view.output(resultSet);
	}

	

	public void getProductByProductId(int productId) throws SQLException {
		String select = "SELECT Product.productId, Product.productName, " + "Category.categoryType, Product.price, "
				+ "Category.categoryId," + "Product.quantity, Product.description " + "FROM Product JOIN Category ON "
				+ "Product.categoryId = Category.categoryId " + "where productId= ? ";
		preparedstatement = connect.prepareStatement(select);
		preparedstatement.setInt(1, productId);
		resultSet = preparedstatement.executeQuery();
		InventoryService view = new InventoryService();
		view.output(resultSet);
	}

	public void deleteProductByCategoryId(int categoryId) throws SQLException {
		String deleteProduct = "delete from Product where categoryId=?";
		preparedstatement = connect.prepareStatement(deleteProduct);
		preparedstatement.setInt(1, categoryId);
		preparedstatement.executeUpdate();
		String deleteCategory = "delete from Category where categoryId=?";
		preparedstatement = connect.prepareStatement(deleteCategory);
		preparedstatement.setInt(1, categoryId);
		preparedstatement.executeUpdate();
		System.out.println("Delete CategoryId successfully");
	}
	
	public void getCategoryList() throws SQLException
	{
		String getCategoryList="select distinct categoryType from Category";
		preparedstatement = connect.prepareStatement(getCategoryList);
		resultSet = preparedstatement.executeQuery();
		while(resultSet.next())
		{
			String categoryList=resultSet.getString("categoryType");
			System.out.print(categoryList+" ");
		}
		System.out.println();
	}
	
	public void output(ResultSet resultSet) throws SQLException {
		while (resultSet.next()) {
			int productId = resultSet.getInt("productId");
			String productName = resultSet.getString("productName");
			String categoryType = resultSet.getString("categoryType");
			double price = resultSet.getDouble("price");
			int categoryId = resultSet.getInt("categoryId");
			int quantity = resultSet.getInt("quantity");
			String description = resultSet.getString("description");

			ProductEntity method = new ProductEntity(productId, productName, categoryType, price, categoryId, quantity,
					description);
			System.out.println(method);
		}
	}

}