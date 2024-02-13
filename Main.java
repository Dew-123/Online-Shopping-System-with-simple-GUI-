package Main;

import java.util.Scanner;
import javax.swing.*;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();

        while (true) {
            displayMainMenu();

            if (scanner.hasNextInt()) {
                int option = scanner.nextInt();
                handleMainMenuOption(shoppingManager, option);
            } else {
                System.out.println("Please enter a number");
                scanner.next(); // consume invalid input
            }
        }
    }

    private static void displayMainMenu() {
        WestminsterShoppingManager.displayMenu();
    }

    private static void handleMainMenuOption(WestminsterShoppingManager shoppingManager, int option) {
        switch (option) {
            case 1:
                addProduct(shoppingManager);
                break;
            case 2:
                deleteProduct(shoppingManager);
                break;
            case 3:
                shoppingManager.printSystemProductList();
                break;
            case 4:
                shoppingManager.saveInFile();
                System.out.println("Successful");
                break;
            case 5:
                handleUserAuthentication(shoppingManager);
                break;
            case 6:
                System.out.println("Exiting...");
                System.exit(0);
            default:
                System.out.println("Invalid selection. Try again !!!");
        }
    }

    private static void addProduct(WestminsterShoppingManager shoppingManager) {
        System.out.println("Enter the product ID: ");
        String productID = scanner.next();

        System.out.println("Enter product name: ");
        String productName = scanner.next();
        scanner.nextLine();

        System.out.println("Enter product type (Electronics/Clothing): ");
        String productType = scanner.next();
        scanner.nextLine();

        System.out.println("Enter number of available items: ");
        int numOfAvailableItems = getValidIntInput();

        System.out.println("Enter price: ");
        double price = getValidDoubleInput();

        if (productType.equalsIgnoreCase("Electronics")) {
            addElectronicsProduct(shoppingManager, productID, productName, numOfAvailableItems, price);
        } else if (productType.equalsIgnoreCase("Clothing")) {
            addClothingProduct(shoppingManager, productID, productName, numOfAvailableItems, price);
        } else {
            System.out.println("Invalid product type");
        }
    }

    private static void addElectronicsProduct(WestminsterShoppingManager shoppingManager,
                                              String productID, String productName, int numOfAvailableItems, double price) {
        System.out.println("Enter brand: ");
        String brand = scanner.next();

        System.out.println("Enter warranty period: ");
        int warrantyPeriod = getValidIntInput();

        Product product = new Electronics(productID, productName, numOfAvailableItems, price, brand, warrantyPeriod);
        shoppingManager.addProductToSystem(product);
    }

    private static void addClothingProduct(WestminsterShoppingManager shoppingManager,
                                           String productID, String productName, int numOfAvailableItems, double price) {
        System.out.println("Enter size: ");
        String size = scanner.next();

        System.out.println("Enter colour: ");
        String colour = scanner.next();

        Product product = new Clothing(productID, productName, numOfAvailableItems, price, size, colour);
        shoppingManager.addProductToSystem(product);
    }

    private static void deleteProduct(WestminsterShoppingManager shoppingManager) {
        System.out.println("Enter product ID: ");
        String productIDDelete = scanner.next();
        shoppingManager.deleteProductFromSystem(productIDDelete);
    }

    private static void handleUserAuthentication(WestminsterShoppingManager shoppingManager) {
        shoppingManager.loadRegUserList();
        System.out.println("Opening GUI");

        System.out.println("1. Log in");
        System.out.println("2. Register");
        System.out.println("Enter your choice: ");
        int loginOrRegister = scanner.nextInt();

        switch (loginOrRegister) {
            case 1:
                handleUserLogin(shoppingManager);
                break;
            case 2:
                handleUserRegistration(shoppingManager);
                break;
            default:
                System.out.println("Invalid choice. Please enter 1 or 2.");
                break;
        }
    }

    private static void handleUserLogin(WestminsterShoppingManager shoppingManager) {
        System.out.println("Enter your username: ");
        String username = scanner.next();
        System.out.println("Enter your password: ");
        String password = scanner.next();

        if (shoppingManager.userAuthentication(username, password)) {
            System.out.println("Login successful");
            User loggedInUser = shoppingManager.getUserByUsername(username);
            openGUI(shoppingManager, loggedInUser);
        } else {
            System.out.println("Login failed. Invalid username or password.");
        }
    }

    private static void handleUserRegistration(WestminsterShoppingManager shoppingManager) {
        System.out.println("Enter a new username: ");
        String newUsername = scanner.next();

        System.out.println("Enter a password: ");
        String newPassword = scanner.next();

        shoppingManager.registerUser(newUsername, newPassword);
        shoppingManager.saveUsersToFile();

        System.out.println("User registered successfully. You can now log in.");
    }

    private static void openGUI(WestminsterShoppingManager manager, User loggedInUser) {
        SwingUtilities.invokeLater(() -> new ShoppingGUI(manager, loggedInUser).setVisible(true));
    }

    private static int getValidIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid integer.");
            scanner.next(); // consume invalid input
        }
        return scanner.nextInt();
    }

    private static double getValidDoubleInput() {
        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a valid double.");
            scanner.next(); // consume invalid input
        }
        return scanner.nextDouble();
    }
}
