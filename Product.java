package Main;

public abstract class Product {

    protected String productID;
    protected String productName;
    protected int numOfAvailableItems;
    protected double price;

    public Product(String productID, String productName, int numOfAvailableItems, double price) {
        this.productID = productID;
        this.productName = productName;
        this.numOfAvailableItems = numOfAvailableItems;
        this.price = price;
    }

    public String getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public int getNumOfAvailableItems() {
        return numOfAvailableItems;
    }

    public double getPrice() {
        return price;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setNumOfAvailableItems(int numOfAvailableItems) {
        this.numOfAvailableItems = numOfAvailableItems;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public abstract String printInfo();
    public abstract String productType();
    public abstract String info();
    public abstract String infoGUI();
}
