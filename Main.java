package Ecommerce;

/*Title: Open ended assignment-Mini project

Problem Statement: Develop a console-based e-commerce application that allows users to view products, 

 * manage a shopping cart, and enable admins to manage product inventory. The application will include user and 

 * admin login, product management, and cart functionalities. 

 * It will use appropriate data structures like HashMap for user and product management, 

 * Binary Search Tree (BST) for product search, and LinkedList for the shopping cart.

*/


import java.util.*;

class User {

	String username;

	String password;

	boolean isPremium; // Tracks whether the user has a premium plan

	LinkedList<String> cart = new LinkedList<>();

	User(String username, String password, boolean isPremium) {

		this.username = username;

		this.password = password;

		this.isPremium = isPremium;

	}

}



class Product {

	int id;

	String name;

	double price;


	Product(int id, String name, double price) {

		this.id = id;

		this.name = name;

		this.price = price;

	}





	public String toString() {

		return "ID: " + id + " | Name: " + name + " | Price: Rs." + price;

	}

}







class BST {

	class Node {

		Product product;

		Node left, right;



		Node(Product product) {


			this.product = product;

			left = right = null;

		}

	}




	private Node root;



//Insert product into the BST based on name


	public void insert(Product product) {



		root = insertRec(root, product);


	}



	private Node insertRec(Node root, Product product) {

		if (root == null) {

			root = new Node(product);

			return root;

		}



		if (product.name.compareTo(root.product.name) < 0) {



			root.left = insertRec(root.left, product);



		} else if (product.name.compareTo(root.product.name) > 0) {



			root.right = insertRec(root.right, product);



		}







		return root;



	}







//Search for a product by name in the BST



	public Product search(String name) {



		return searchRec(root, name);



	}







	private Product searchRec(Node root, String name) {



		if (root == null) {



			return null;



		}







		if (name.equals(root.product.name)) {



			return root.product;



		}







		if (name.compareTo(root.product.name) < 0) {



			return searchRec(root.left, name);



		}







		return searchRec(root.right, name);



	}



}







public class Main {



	static Map<String, User> users = new HashMap<>();



	static Map<Integer, Product> products = new HashMap<>();



	static final String ADMIN_USERNAME = "admin";



	static final String ADMIN_PASSWORD = "admin123";



	static BST productBST = new BST();







	public static void main(String[] args) {



		Scanner scanner = new Scanner(System.in);



		String loggedInUser;







//Adding 15 products to the products list



		addInitialProducts();







		while (true) {



			System.out.println("\n1. User Login");



			System.out.println("2. User Register");



			System.out.println("3. Admin Login");



			System.out.println("4. Exit");



			System.out.print("Enter your choice: ");



			int choice = scanner.nextInt();



			scanner.nextLine(); // consume newline







			switch (choice) {



			case 1:



				loggedInUser = login(scanner);



				if (loggedInUser != null && !loggedInUser.equals(ADMIN_USERNAME)) {



					userMenu(scanner, loggedInUser);



				}



				break;



			case 2:



				register(scanner);



				break;



			case 3:



				loggedInUser = login(scanner);



				if (loggedInUser != null && loggedInUser.equals(ADMIN_USERNAME)) {



					adminMenu(scanner);



				}



				break;



			case 4:



				System.out.println("Exiting...");



				return;



			default:



				System.out.println("Invalid choice. Try again.");



			}



		}



	}







//Login method to authenticate users



	public static String login(Scanner scanner) {



		System.out.print("Enter username: ");



		String username = scanner.nextLine();



		System.out.print("Enter password: ");



		String password = scanner.nextLine();







//Check for admin login



		if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {



			System.out.println("Admin logged in.");



			return ADMIN_USERNAME;



		}







//Check for user login



		User user = users.get(username);



		if (user != null && user.password.equals(password)) {



			System.out.println("User logged in: " + username);



			return username;



		} else {



			System.out.println("Invalid username or password.");



			return null;



		}



	}







//Register method to create new users



	public static void register(Scanner scanner) {



		System.out.print("Enter username: ");



		String username = scanner.nextLine();



		System.out.print("Enter password: ");



		String password = scanner.nextLine();



		System.out.print("Do you want to opt for a premium plan? (yes/no): ");



		String premiumChoice = scanner.nextLine();







		boolean isPremium = premiumChoice.equalsIgnoreCase("yes");







		if (users.containsKey(username)) {



			System.out.println("Username already exists. Try another one.");



		} else {



			users.put(username, new User(username, password, isPremium));



			System.out.println("User registered successfully as " + (isPremium ? "Premium" : "Regular") + " member.");



		}



	}







//User Menu



	public static void userMenu(Scanner scanner, String username) {



		User user = users.get(username);



		while (true) {



			System.out.println("\n1. View all products");



			System.out.println("2. Search product by name");



			System.out.println("3. Sort products");



			System.out.println("4. Add to my cart");



			System.out.println("5. View cart");



			System.out.println("6. Logout");



			System.out.print("Enter your choice: ");



			int choice = scanner.nextInt();



			scanner.nextLine(); // consume newline







			switch (choice) {



			case 1:



				viewProductDetails();



				break;



			case 2:



				searchProductByName(scanner);



				break;



			case 3:



				sortProducts(scanner);



				break;



			case 4:



				addProductToCart(scanner, user);



				break;



			case 5:



				viewCart(user);



				break;



			case 6:



				System.out.println("Logging out...");



				return;



			default:



				System.out.println("Invalid choice. Try again.");



			}



		}



	}







//Admin Menu



	public static void adminMenu(Scanner scanner) {



		while (true) {



			System.out.println("\nAdmin Menu:");



			System.out.println("1. Add New Products");



			System.out.println("2. View All Products");



			System.out.println("3. View Everyone's Cart");



			System.out.println("4. Delete Product by ID");



			System.out.println("5. Logout");



			System.out.print("Enter your choice: ");



			int choice = scanner.nextInt();



			scanner.nextLine(); // consume newline







			switch (choice) {



			case 1:



				addNewProduct(scanner);



				break;



			case 2:



				viewProductDetails();



				break;



			case 3:



				viewAllCarts();



				break;



			case 4:



				deleteProduct(scanner);



				break;



			case 5:



				System.out.println("Admin logging out...");



				return;



			default:



				System.out.println("Invalid choice. Try again.");



			}



		}



	}





	//View all products in the store



		public static void viewProductDetails() {



			System.out.println("Product Details:");



			for (Product product : products.values()) {



				System.out.println(product);



			}



		}



//Add new product to the store



	public static void addNewProduct(Scanner scanner) {



		System.out.print("Enter product ID: ");



		int id = scanner.nextInt();



		scanner.nextLine(); // consume newline



		System.out.print("Enter product name: ");



		String name = scanner.nextLine();



		System.out.print("Enter product price: ");



		double price = scanner.nextDouble();



		scanner.nextLine(); // consume newline







		Product product = new Product(id, name, price);



		products.put(id, product);



		productBST.insert(product);



		System.out.println("Product added successfully.");



	}







//Delete a product by its ID



	public static void deleteProduct(Scanner scanner) {



		System.out.print("Enter product ID to delete: ");



		int productId = scanner.nextInt();



		scanner.nextLine(); // consume newline







		if (products.containsKey(productId)) {



			products.remove(productId);



			System.out.println("Product with ID " + productId + " has been deleted.");



		} else {



			System.out.println("Product with ID " + productId + " not found.");



		}



	}







	//View all users' carts



		public static void viewAllCarts() {



			System.out.println("User Carts:");



			for (Map.Entry<String, User> entry : users.entrySet()) {



				System.out.println("Username: " + entry.getKey() + " (" + (entry.getValue().isPremium ? "Premium" : "Regular") + ")");



				if (entry.getValue().cart.isEmpty()) {



					System.out.println(" Cart is empty.");



				} else {



					for (String item : entry.getValue().cart) {



						System.out.println(" - " + item);



					}



				}



			}



		}







//Search a product by its name



	public static void searchProductByName(Scanner scanner) {



		System.out.print("Enter the name of the product to search: ");



		String name = scanner.nextLine();







		Product product = productBST.search(name);



		if (product != null) {



			System.out.println("Product found: " + product);



		} else {



			System.out.println("Product not found.");



		}



	}







//Sort products based on various criteria



	public static void sortProducts(Scanner scanner) {



		System.out.println("\nSort Products By:");



		System.out.println("1. Alphabetically (A-Z)");



		System.out.println("2. Alphabetically (Z-A)");



		System.out.println("3. Price (Low to High)");



		System.out.println("4. Price (High to Low)");



		System.out.print("Enter your choice: ");



		int choice = scanner.nextInt();



		scanner.nextLine(); // consume newline







		List<Product> productList = new ArrayList<>(products.values());







		switch (choice) {



		case 1:



			bubbleSortAlphabetically(productList, true);



			break;



		case 2:



			bubbleSortAlphabetically(productList, false);



			break;



		case 3:



			bubbleSortPrice(productList, true);



			break;



		case 4:



			bubbleSortPrice(productList, false);



			break;



		default:



			System.out.println("Invalid choice.");



			return;



		}







		System.out.println("Sorted Products:");



		for (Product product : productList) {



			System.out.println(product);



		}



	}







//Bubble Sort for Alphabetical order (A-Z or Z-A)



	public static void bubbleSortAlphabetically(List<Product> productList, boolean ascending) {



		int n = productList.size();



		for (int i = 0; i < n - 1; i++) {



			for (int j = 0; j < n - i - 1; j++) {



				Product p1 = productList.get(j);



				Product p2 = productList.get(j + 1);







				int compareResult = p1.name.compareTo(p2.name);



				if ((ascending && compareResult > 0) || (!ascending && compareResult < 0)) {



//Swap the elements



					Collections.swap(productList, j, j + 1);



				}



			}



		}



	}







//Bubble Sort for Price (Low to High or High to Low)



	public static void bubbleSortPrice(List<Product> productList, boolean ascending) {



		int n = productList.size();



		for (int i = 0; i < n - 1; i++) {



			for (int j = 0; j < n - i - 1; j++) {



				Product p1 = productList.get(j);



				Product p2 = productList.get(j + 1);







				if ((ascending && p1.price > p2.price) || (!ascending && p1.price < p2.price)) {



//Swap the elements



					Collections.swap(productList, j, j + 1);



				}



			}



		}



	}













//Add a product to user's cart



	public static void addProductToCart(Scanner scanner, User user) {



		if (products.isEmpty()) {



			System.out.println("No products available.");



			return;



		}







		System.out.println("Available Products:");



		for (Product product : products.values()) {



			System.out.println(product);



		}







		System.out.print("Enter product ID to add to cart: ");



		int productId = scanner.nextInt();



		scanner.nextLine(); // consume newline







		Product product = products.get(productId);



		if (product != null) {



			user.cart.add(product.name);



			System.out.println("Product added to cart: " + product.name);



		} else {



			System.out.println("Invalid product ID.");



		}



	}







//View user's cart



	public static void viewCart(User user) {



		System.out.println("Your Cart:");



		if (user.cart.isEmpty()) {



			System.out.println("Cart is empty.");



		} else {



			for (String item : user.cart) {



				System.out.println("- " + item);



			}



		}



	}







//Add initial products (15 products) to the store



	public static void addInitialProducts() {



		products.put(1, new Product(1, "Laptop", 999.99));



		products.put(2, new Product(2, "Smartphone", 599.99));



		products.put(3, new Product(3, "Headphones", 199.99));



		products.put(4, new Product(4, "Smartwatch", 150.99));



		products.put(5, new Product(5, "Tablet", 349.99));



		products.put(6, new Product(6, "Monitor", 179.99));



		products.put(7, new Product(7, "Keyboard", 49.99));



		products.put(8, new Product(8, "Mouse", 29.99));



		products.put(9, new Product(9, "Charger", 19.99));



		products.put(10, new Product(10, "Camera", 899.99));



		products.put(11, new Product(11, "TV", 499.99));



		products.put(12, new Product(12, "Speakers", 129.99));



		products.put(13, new Product(13, "External Hard Drive", 59.99));



		products.put(14, new Product(14, "Printer", 129.99));



		products.put(15, new Product(15, "Flash Drive", 9.99));







		for (Product product : products.values()) {



			productBST.insert(product);



		}



	}



}





/*

OUTPUT

1. User Login
2. User Register
3. Admin Login
4. Exit
Enter your choice: 2
Enter username: a
Enter password: a
Do you want to opt for a premium plan? (yes/no): no
User registered successfully as Regular member.

1. User Login
2. User Register
3. Admin Login
4. Exit
Enter your choice: 2
Enter username: b
Enter password: b
Do you want to opt for a premium plan? (yes/no): yes
User registered successfully as Premium member.

1. User Login
2. User Register
3. Admin Login
4. Exit
Enter your choice: 2
Enter username: b
Enter password: b
Do you want to opt for a premium plan? (yes/no): no
Username already exists. Try another one.

1. User Login
2. User Register
3. Admin Login
4. Exit
Enter your choice: 1
Enter username: a
Enter password: a
User logged in: a

1. View all products
2. Search product by name
3. Sort products
4. Add to my cart
5. View cart
6. Logout
Enter your choice: 1
Product Details:
ID: 1 | Name: Laptop | Price: Rs.999.99
ID: 2 | Name: Smartphone | Price: Rs.599.99
ID: 3 | Name: Headphones | Price: Rs.199.99
ID: 4 | Name: Smartwatch | Price: Rs.150.99
ID: 5 | Name: Tablet | Price: Rs.349.99
ID: 6 | Name: Monitor | Price: Rs.179.99
ID: 7 | Name: Keyboard | Price: Rs.49.99
ID: 8 | Name: Mouse | Price: Rs.29.99
ID: 9 | Name: Charger | Price: Rs.19.99
ID: 10 | Name: Camera | Price: Rs.899.99
ID: 11 | Name: TV | Price: Rs.499.99
ID: 12 | Name: Speakers | Price: Rs.129.99
ID: 13 | Name: External Hard Drive | Price: Rs.59.99
ID: 14 | Name: Printer | Price: Rs.129.99
ID: 15 | Name: Flash Drive | Price: Rs.9.99

1. View all products
2. Search product by name
3. Sort products
4. Add to my cart
5. View cart
6. Logout
Enter your choice: 2
Enter the name of the product to search: Laptop
Product found: ID: 1 | Name: Laptop | Price: Rs.999.99

1. View all products
2. Search product by name
3. Sort products
4. Add to my cart
5. View cart
6. Logout
Enter your choice: 3

Sort Products By:
1. Alphabetically (A-Z)
2. Alphabetically (Z-A)
3. Price (Low to High)
4. Price (High to Low)
Enter your choice: 1
Sorted Products:
ID: 10 | Name: Camera | Price: Rs.899.99
ID: 9 | Name: Charger | Price: Rs.19.99
ID: 13 | Name: External Hard Drive | Price: Rs.59.99
ID: 15 | Name: Flash Drive | Price: Rs.9.99
ID: 3 | Name: Headphones | Price: Rs.199.99
ID: 7 | Name: Keyboard | Price: Rs.49.99
ID: 1 | Name: Laptop | Price: Rs.999.99
ID: 6 | Name: Monitor | Price: Rs.179.99
ID: 8 | Name: Mouse | Price: Rs.29.99
ID: 14 | Name: Printer | Price: Rs.129.99
ID: 2 | Name: Smartphone | Price: Rs.599.99
ID: 4 | Name: Smartwatch | Price: Rs.150.99
ID: 12 | Name: Speakers | Price: Rs.129.99
ID: 11 | Name: TV | Price: Rs.499.99
ID: 5 | Name: Tablet | Price: Rs.349.99

1. View all products
2. Search product by name
3. Sort products
4. Add to my cart
5. View cart
6. Logout
Enter your choice: 3

Sort Products By:
1. Alphabetically (A-Z)
2. Alphabetically (Z-A)
3. Price (Low to High)
4. Price (High to Low)
Enter your choice: 2
Sorted Products:
ID: 5 | Name: Tablet | Price: Rs.349.99
ID: 11 | Name: TV | Price: Rs.499.99
ID: 12 | Name: Speakers | Price: Rs.129.99
ID: 4 | Name: Smartwatch | Price: Rs.150.99
ID: 2 | Name: Smartphone | Price: Rs.599.99
ID: 14 | Name: Printer | Price: Rs.129.99
ID: 8 | Name: Mouse | Price: Rs.29.99
ID: 6 | Name: Monitor | Price: Rs.179.99
ID: 1 | Name: Laptop | Price: Rs.999.99
ID: 7 | Name: Keyboard | Price: Rs.49.99
ID: 3 | Name: Headphones | Price: Rs.199.99
ID: 15 | Name: Flash Drive | Price: Rs.9.99
ID: 13 | Name: External Hard Drive | Price: Rs.59.99
ID: 9 | Name: Charger | Price: Rs.19.99
ID: 10 | Name: Camera | Price: Rs.899.99

1. View all products
2. Search product by name
3. Sort products
4. Add to my cart
5. View cart
6. Logout
Enter your choice: 3

Sort Products By:
1. Alphabetically (A-Z)
2. Alphabetically (Z-A)
3. Price (Low to High)
4. Price (High to Low)
Enter your choice: 3
Sorted Products:
ID: 15 | Name: Flash Drive | Price: Rs.9.99
ID: 9 | Name: Charger | Price: Rs.19.99
ID: 8 | Name: Mouse | Price: Rs.29.99
ID: 7 | Name: Keyboard | Price: Rs.49.99
ID: 13 | Name: External Hard Drive | Price: Rs.59.99
ID: 12 | Name: Speakers | Price: Rs.129.99
ID: 14 | Name: Printer | Price: Rs.129.99
ID: 4 | Name: Smartwatch | Price: Rs.150.99
ID: 6 | Name: Monitor | Price: Rs.179.99
ID: 3 | Name: Headphones | Price: Rs.199.99
ID: 5 | Name: Tablet | Price: Rs.349.99
ID: 11 | Name: TV | Price: Rs.499.99
ID: 2 | Name: Smartphone | Price: Rs.599.99
ID: 10 | Name: Camera | Price: Rs.899.99
ID: 1 | Name: Laptop | Price: Rs.999.99

1. View all products
2. Search product by name
3. Sort products
4. Add to my cart
5. View cart
6. Logout
Enter your choice: 3

Sort Products By:
1. Alphabetically (A-Z)
2. Alphabetically (Z-A)
3. Price (Low to High)
4. Price (High to Low)
Enter your choice: 4
Sorted Products:
ID: 1 | Name: Laptop | Price: Rs.999.99
ID: 10 | Name: Camera | Price: Rs.899.99
ID: 2 | Name: Smartphone | Price: Rs.599.99
ID: 11 | Name: TV | Price: Rs.499.99
ID: 5 | Name: Tablet | Price: Rs.349.99
ID: 3 | Name: Headphones | Price: Rs.199.99
ID: 6 | Name: Monitor | Price: Rs.179.99
ID: 4 | Name: Smartwatch | Price: Rs.150.99
ID: 12 | Name: Speakers | Price: Rs.129.99
ID: 14 | Name: Printer | Price: Rs.129.99
ID: 13 | Name: External Hard Drive | Price: Rs.59.99
ID: 7 | Name: Keyboard | Price: Rs.49.99
ID: 8 | Name: Mouse | Price: Rs.29.99
ID: 9 | Name: Charger | Price: Rs.19.99
ID: 15 | Name: Flash Drive | Price: Rs.9.99

1. View all products
2. Search product by name
3. Sort products
4. Add to my cart
5. View cart
6. Logout
Enter your choice: 4
Available Products:
ID: 1 | Name: Laptop | Price: Rs.999.99
ID: 2 | Name: Smartphone | Price: Rs.599.99
ID: 3 | Name: Headphones | Price: Rs.199.99
ID: 4 | Name: Smartwatch | Price: Rs.150.99
ID: 5 | Name: Tablet | Price: Rs.349.99
ID: 6 | Name: Monitor | Price: Rs.179.99
ID: 7 | Name: Keyboard | Price: Rs.49.99
ID: 8 | Name: Mouse | Price: Rs.29.99
ID: 9 | Name: Charger | Price: Rs.19.99
ID: 10 | Name: Camera | Price: Rs.899.99
ID: 11 | Name: TV | Price: Rs.499.99
ID: 12 | Name: Speakers | Price: Rs.129.99
ID: 13 | Name: External Hard Drive | Price: Rs.59.99
ID: 14 | Name: Printer | Price: Rs.129.99
ID: 15 | Name: Flash Drive | Price: Rs.9.99
Enter product ID to add to cart: 1
Product added to cart: Laptop

1. View all products
2. Search product by name
3. Sort products
4. Add to my cart
5. View cart
6. Logout
Enter your choice: 4
Available Products:
ID: 1 | Name: Laptop | Price: Rs.999.99
ID: 2 | Name: Smartphone | Price: Rs.599.99
ID: 3 | Name: Headphones | Price: Rs.199.99
ID: 4 | Name: Smartwatch | Price: Rs.150.99
ID: 5 | Name: Tablet | Price: Rs.349.99
ID: 6 | Name: Monitor | Price: Rs.179.99
ID: 7 | Name: Keyboard | Price: Rs.49.99
ID: 8 | Name: Mouse | Price: Rs.29.99
ID: 9 | Name: Charger | Price: Rs.19.99
ID: 10 | Name: Camera | Price: Rs.899.99
ID: 11 | Name: TV | Price: Rs.499.99
ID: 12 | Name: Speakers | Price: Rs.129.99
ID: 13 | Name: External Hard Drive | Price: Rs.59.99
ID: 14 | Name: Printer | Price: Rs.129.99
ID: 15 | Name: Flash Drive | Price: Rs.9.99
Enter product ID to add to cart: 8
Product added to cart: Mouse

1. View all products
2. Search product by name
3. Sort products
4. Add to my cart
5. View cart
6. Logout
Enter your choice: 4   5
Your Cart:
- Laptop
- Mouse

1. View all products
2. Search product by name
3. Sort products
4. Add to my cart
5. View cart
6. Logout
Enter your choice: 6
Logging out...

1. User Login
2. User Register
3. Admin Login
4. Exit
Enter your choice: 1
Enter username: b
Enter password: b
User logged in: b

1. View all products
2. Search product by name
3. Sort products
4. Add to my cart
5. View cart
6. Logout
Enter your choice: 4
Available Products:
ID: 1 | Name: Laptop | Price: Rs.999.99
ID: 2 | Name: Smartphone | Price: Rs.599.99
ID: 3 | Name: Headphones | Price: Rs.199.99
ID: 4 | Name: Smartwatch | Price: Rs.150.99
ID: 5 | Name: Tablet | Price: Rs.349.99
ID: 6 | Name: Monitor | Price: Rs.179.99
ID: 7 | Name: Keyboard | Price: Rs.49.99
ID: 8 | Name: Mouse | Price: Rs.29.99
ID: 9 | Name: Charger | Price: Rs.19.99
ID: 10 | Name: Camera | Price: Rs.899.99
ID: 11 | Name: TV | Price: Rs.499.99
ID: 12 | Name: Speakers | Price: Rs.129.99
ID: 13 | Name: External Hard Drive | Price: Rs.59.99
ID: 14 | Name: Printer | Price: Rs.129.99
ID: 15 | Name: Flash Drive | Price: Rs.9.99
Enter product ID to add to cart: 2
Product added to cart: Smartphone

1. View all products
2. Search product by name
3. Sort products
4. Add to my cart
5. View cart
6. Logout
Enter your choice: 6
Logging out...


1. User Login
2. User Register
3. Admin Login
4. Exit
Enter your choice: 3
Enter username: admin
Enter password: pass
Invalid username or password.

1. User Login
2. User Register
3. Admin Login
4. Exit
Enter your choice: 3
Enter username: admin
Enter password: admin123
Admin logged in.

Admin Menu:
1. Add New Products
2. View All Products
3. View Everyone's Cart
4. Delete Product by ID
5. Logout
Enter your choice: 2   1
Enter product ID: 16
Enter product name: Lipstick
Enter product price: 100
Product added successfully.

Admin Menu:
1. Add New Products
2. View All Products
3. View Everyone's Cart
4. Delete Product by ID
5. Logout
Enter your choice: 2
Product Details:
ID: 1 | Name: Laptop | Price: Rs.999.99
ID: 2 | Name: Smartphone | Price: Rs.599.99
ID: 3 | Name: Headphones | Price: Rs.199.99
ID: 4 | Name: Smartwatch | Price: Rs.150.99
ID: 5 | Name: Tablet | Price: Rs.349.99
ID: 6 | Name: Monitor | Price: Rs.179.99
ID: 7 | Name: Keyboard | Price: Rs.49.99
ID: 8 | Name: Mouse | Price: Rs.29.99
ID: 9 | Name: Charger | Price: Rs.19.99
ID: 10 | Name: Camera | Price: Rs.899.99
ID: 11 | Name: TV | Price: Rs.499.99
ID: 12 | Name: Speakers | Price: Rs.129.99
ID: 13 | Name: External Hard Drive | Price: Rs.59.99
ID: 14 | Name: Printer | Price: Rs.129.99
ID: 15 | Name: Flash Drive | Price: Rs.9.99
ID: 16 | Name: Lipstick | Price: Rs.100.0

Admin Menu:
1. Add New Products
2. View All Products
3. View Everyone's Cart
4. Delete Product by ID
5. Logout
Enter your choice: 4
Enter product ID to delete: 16
Product with ID 16 has been deleted.

Admin Menu:
1. Add New Products
2. View All Products
3. View Everyone's Cart
4. Delete Product by ID
5. Logout
Enter your choice: 2
Product Details:
ID: 1 | Name: Laptop | Price: Rs.999.99
ID: 2 | Name: Smartphone | Price: Rs.599.99
ID: 3 | Name: Headphones | Price: Rs.199.99
ID: 4 | Name: Smartwatch | Price: Rs.150.99
ID: 5 | Name: Tablet | Price: Rs.349.99
ID: 6 | Name: Monitor | Price: Rs.179.99
ID: 7 | Name: Keyboard | Price: Rs.49.99
ID: 8 | Name: Mouse | Price: Rs.29.99
ID: 9 | Name: Charger | Price: Rs.19.99
ID: 10 | Name: Camera | Price: Rs.899.99
ID: 11 | Name: TV | Price: Rs.499.99
ID: 12 | Name: Speakers | Price: Rs.129.99
ID: 13 | Name: External Hard Drive | Price: Rs.59.99
ID: 14 | Name: Printer | Price: Rs.129.99
ID: 15 | Name: Flash Drive | Price: Rs.9.99

Admin Menu:
1. Add New Products
2. View All Products
3. View Everyone's Cart
4. Delete Product by ID
5. Logout
Enter your choice: 3
User Carts:
Username: a (Regular)
 - Laptop
 - Mouse
Username: b (Premium)
 - Smartphone

Admin Menu:
1. Add New Products
2. View All Products
3. View Everyone's Cart
4. Delete Product by ID
5. Logout
Enter your choice: 5
Admin logging out...


1. User Login
2. User Register
3. Admin Login
4. Exit
Enter your choice: 4
Exiting...
*/
