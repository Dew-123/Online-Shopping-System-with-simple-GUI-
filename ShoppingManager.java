package Main;

public interface ShoppingManager {

    void addProductToSystem(Product product);
    void deleteProductFromSystem(String productID);
    void printSystemProductList();
    void saveInFile();
    void loadFromFile();
}
