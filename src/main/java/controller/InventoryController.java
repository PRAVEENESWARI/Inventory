package controller;

import java.sql.SQLException;
import java.util.Scanner;

import model.ProductEntity;
import service.InventoryService;

public class InventoryController {
	InventoryService inventory = new InventoryService();
	Scanner sc = new Scanner(System.in);

	public void addProduct() throws SQLException {
		System.out.println("Enter ProductName :");
		String productName = sc.nextLine();

		System.out.println("Enter categoryName :");
		String categoryType = sc.nextLine();

		System.out.println("Enter price :");
		double price = sc.nextDouble();

		System.out.println("Enter quantity :");
		int quantity = sc.nextInt();

		sc.nextLine();

		System.out.println("Enter Description :");
		String description = sc.nextLine();

		ProductEntity product = new ProductEntity(productName, categoryType, price, quantity, description);

		inventory.addProduct(product);
	}

	public void updateStock() throws SQLException {
		System.out.println("Enter product ID to update stock: ");
		int productId = sc.nextInt();
		System.out.println("Enter quantity to add ");
		int quantityAdd = sc.nextInt();
		ProductEntity product = new ProductEntity(quantityAdd);
		inventory.updateStock(productId, product);
	}

	public void allProduct() throws SQLException {
		inventory.allProduct();
	}

	public void getProductsByCategory() throws SQLException {
		InventoryService view = new InventoryService();
		view.getCategoryList();
		System.out.println("Enter category to filter by : ");
		String categoryType = sc.nextLine();
		System.out.println("Products in category : " + categoryType);
		inventory.getProductsByCategory(categoryType);
	}

	public void getSortedProductsByPrice() throws SQLException {
		inventory.getSortedProductsByPrice();
	}

	public void getProductByProductId() throws SQLException {
		System.out.println("Enter the ProductId to view : ");
		int productId = sc.nextInt();
		inventory.getProductByProductId(productId);
	}

	public void deleteProductByCategoryId() throws SQLException {
		System.out.println("Enter CategoryId to delete : ");
		int categoryId = sc.nextInt();
		inventory.deleteProductByCategoryId(categoryId);
	}

}