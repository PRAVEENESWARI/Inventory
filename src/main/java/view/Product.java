package view;

import java.sql.SQLException;
import java.util.*;
import controller.InventoryController;

class Product {
	public static void main(String[] args) throws SQLException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Inventory Management System !");
		while (true) {
			System.out.println("1. Add Product ");
			System.out.println("2. Update Stock ");
			System.out.println("3. View All Products ");
			System.out.println("4. View Products by Category");
			System.out.println("5. View Products Sorted by Price");
			System.out.println("6. Get product by CategoryId");
			System.out.println("7. Delete Product and Category by CategoryId");
			System.out.println("8. Exit");

			InventoryController controller = new InventoryController();
			int check = sc.nextInt();

			switch (check) {
			case 1:
				controller.addProduct();
				break;
			case 2:
				controller.updateStock();
				break;
			case 3:
				controller.allProduct();
				break;
			case 4:
				controller.getProductsByCategory();
				break;
			case 5:
				controller.getSortedProductsByPrice();
				break;
			case 6:
				controller.getProductByProductId();
				break;
			case 7:
				controller.deleteProductByCategoryId();
				break;
			case 8:
				System.out.print("Thank you...");
				sc.close();
				return;
			default:
				System.out.println("Please give a number from 1 to 8");
				break;
			}
		}
	}
}
