package Main;

import java.io.*;
import java.util.*;

public class WestminsterShoppingManager implements ShoppingManager, Serializable {

    private List<Product> systemProductList;
    private List<Product> sortedList;
    private List<User> regUserList;

    public WestminsterShoppingManager() {
        this.systemProductList = new ArrayList<>();
        this.regUserList = new ArrayList<>();
        loadFromFile();
    }

    public List<Product> getSortedList() {
        sortedList = new ArrayList<>(systemProductList);
        Collections.sort(sortedList, Comparator.comparing(Product::getProductID));
        return sortedList;
    }

    //Add a product to the system
    @Override
    public void addProductToSystem(Product product) {
        if (systemProductList.size() < 50) {
            systemProductList.add(product);
        } else {
            System.out.println("Cannot add the product. Max limit reached");
        }
    }

    //Delete a product from the system based on its ID
    public void deleteProductFromSystem(String productID) {
        Iterator<Product> iterator = systemProductList.iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.getProductID().equals(productID)) {
                iterator.remove();
                String productType = (product instanceof Electronics) ? "Electronics" : "Clothing";
                System.out.println("ProductID: " + productID + " in " + productType + " is deleted");
                System.out.println("The total number of products left in the system: " + systemProductList.size());
                return;
            }
        }
    }

    //Print the sorted list of products to console
    @Override
    public void printSystemProductList() {
        System.out.println("***** Product List *****");
        sortedList = new ArrayList<>(systemProductList);
        Collections.sort(sortedList, Comparator.comparing(Product::getProductID));
        for (Product product : sortedList) {
            System.out.println(product.printInfo());
            String productType = (product instanceof Electronics) ? "Electronics" : "Clothing";
            System.out.println("Product is a " + productType);
        }
    }

    //Save the system product list to a file.
    @Override
    public void saveInFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("productsList.txt"))) {
            for (Product product : systemProductList) {
                writer.write(product.printInfo());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Load product data from a file into the system
    @Override
    public void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("productsList.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] readList = line.split(",");
                String productID = readList[0];
                String productName = readList[1];
                int numOfAvailableItems = Integer.parseInt(readList[2]);
                double price = Double.parseDouble(readList[3]);

                if (readList[6].startsWith("E")) {
                    int warrantyPeriod = Integer.parseInt(readList[4]);
                    String brand = readList[5];
                    systemProductList.add(new Electronics(productID, productName, numOfAvailableItems, price, brand, warrantyPeriod));
                } else if (readList[6].startsWith("C")) {
                    String size = readList[4];
                    String colour = readList[5];
                    systemProductList.add(new Clothing(productID, productName, numOfAvailableItems, price, size, colour));
                }

            }
        } catch (FileNotFoundException e) {
            System.err.println("Creating new list...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Display the main menu options
    public static void displayMenu() {
        System.out.println("***** Shopping Manager *****");
        System.out.println("1. Add a new product");
        System.out.println("2. Delete a product");
        System.out.println("3. Print the list of the products");
        System.out.println("4. Save in a file");
        System.out.println("5. Open GUI");
        System.out.println("6. Exit");
        System.out.println("Select an option: ");
    }

    //Register a new user with a username and password
    public void registerUser(String username, String password) {
        User newUser = new User(username, password);
        regUserList.add(newUser);
    }


    public boolean userAuthentication(String username, String password) {
        for (User user : regUserList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    //Save registered users to a file
    public void saveUsersToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("registeredUsers.dat", false))) {
            oos.writeObject(regUserList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadRegUserList() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("registeredUsers.dat"))) {
            regUserList = (List<User>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.err.println("Creating new file...");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public User getUserByUsername(String username) {
        for (User user : regUserList) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

}
